package com.grp10.codepath.travelmemo.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.app.MemoApplication;
import com.grp10.codepath.travelmemo.firebase.FirebaseUtil;
import com.grp10.codepath.travelmemo.firebase.Memo;
import com.grp10.codepath.travelmemo.firebase.Trip;
import com.grp10.codepath.travelmemo.fragments.ViewTripInfoFragment;
import com.grp10.codepath.travelmemo.fragments.ViewTripPhotoFragment;
import com.grp10.codepath.travelmemo.models.TripPhoto;
import com.grp10.codepath.travelmemo.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class ViewTripActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG;

    private String tabTitle[] = {"Info", "Photos"};
    ViewTripPagerAdapter viewTripPagerAdapter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fabAddPhoto) FabSpeedDial fabSpeedDial;
    @BindView(R.id.view_trip_viewpager) ViewPager vpPager;
    @BindView(R.id.view_trip_tabstrip) PagerSlidingTabStrip tabStrip;

    private boolean isNewTrip;
    private String tripName;
    private String tripId;
    private String userId = "";
    private StorageReference userRef;
    private DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent() != null){
            tripName = getIntent().getStringExtra(Constants.TRIP_NAME);
            getSupportActionBar().setTitle(tripName);
            tripId = getIntent().getStringExtra(Constants.TRIP_ID);
        }
        Log.d(Constants.TAG,"user == " + FirebaseUtil.getCurrentUserId() + ", " + FirebaseUtil.getCurrentUserName());

        userId = FirebaseUtil.getCurrentUserId();       /// TODO : update this to real user name
        if(getIntent() != null){
            isNewTrip = getIntent().getBooleanExtra(Constants.NEW_TRIP,false);
        }
        viewTripPagerAdapter = new ViewTripPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(viewTripPagerAdapter);
        vpPager.setCurrentItem(1);
        tabStrip.setViewPager(vpPager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        updateFirebaseStorage(isNewTrip,tripName);
        setListener();

    }


    @Override
    protected void onResume() {
        super.onResume();
        getTripDetails();
    }

    Trip tripDetails = null;

    private void getTripDetails() {

        mFirebaseDatabaseReference.child("trips").child(tripId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        Log.d(TAG,"Trip name == " + trip.getName());
                        Log.d(TAG,"Trip id == " + trip.getId());
                        Log.d(TAG,"Trip owner == " + trip.getOwner().toString());
                        tripDetails =trip;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void updateFirebaseStorage(boolean isNewTrip, String tripName) {
        StorageReference reference = MemoApplication.getFBStorageReference();

        StorageReference tripRef = reference.child(tripName);
        userRef = tripRef.child(userId);
    }

    private void uploadFile(boolean isNewTrip, StorageReference userRef) {
        if(isNewTrip){

        }
        ArrayList<TripPhoto> photos = TripPhoto.createDemoTripPhotoList();
        int random = new Random().nextInt(photos.size());

        final int resId = photos.get(random).getPhotoUrl();
//        imageView.setDrawingCacheEnabled(true);
//        imageView.buildDrawingCache();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        String dateFormat = "ddMMyyyyHHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String fileName = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        StorageReference imageRef = userRef.child(fileName + ".jpg");
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d(Constants.TAG,"Download URK == " + downloadUrl.toString());

                Memo memo = new Memo(tripDetails.getOwner(),downloadUrl.toString(),"Dummy Text",Memo.TYPE_PHOTO);
                HashMap<String, Object> result = new HashMap<>();
                List<Memo> memoList = tripDetails.getMemoList();
                if(memoList == null){
                    memoList = new ArrayList<Memo>();
                }
                memoList.add(memo);
//                result.put("Memos",memoList);
                mFirebaseDatabaseReference.child("trips").child(tripId).child("Memos").setValue(memoList);
                tripDetails.setMemoList(memoList);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setListener() {
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                // TODO: Do something with your menu items, or return false if you don't want to show them
                return true;
            }
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.action_camera :
                        ToastText("Camera button clicked");
                        break;
                    case R.id.action_album :
                        ToastText("Album button clicked....Uploading a photo");
                        uploadFile(isNewTrip, userRef);
                        break;
                }
                //TODO: Start some activity
                return false;
            }
        });

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                getSupportActionBar().setTitle(tabTitle[position]);
                if(position == 1){
                    fabSpeedDial.show();
                }else{
                    fabSpeedDial.hide();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class ViewTripPagerAdapter extends FragmentPagerAdapter {
        Fragment fragment = null;
        HashMap<Integer, Fragment> hm = new HashMap<>();
        public ViewTripPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                fragment =  new ViewTripInfoFragment();
                hm.put(0, fragment);
            }else if(position == 1){
                fragment =  new ViewTripPhotoFragment();
                hm.put(1, fragment);
            }else{
                return null;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }

        @Override
        public int getItemPosition (Object object) {
            return POSITION_NONE;
        }

    }

    public void ToastText(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
