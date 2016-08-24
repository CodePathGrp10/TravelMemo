package com.grp10.codepath.travelmemo.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
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
import com.grp10.codepath.travelmemo.firebase.User;
import com.grp10.codepath.travelmemo.fragments.ViewTripInfoFragment;
import com.grp10.codepath.travelmemo.fragments.ViewTripPhotoFragment;
import com.grp10.codepath.travelmemo.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class ViewTripActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG;
    private static final int SELECT_PICTURE = 1;

    private String tabTitle[] = {"Info", "Photos"};
    ViewTripPagerAdapter viewTripPagerAdapter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.backdrop) ImageView ivBackdrop;
    @BindView(R.id.fabAddPhoto) FabSpeedDial fabSpeedDial;
    @BindView(R.id.view_trip_viewpager) ViewPager vpPager;
    @BindView(R.id.view_trip_tabstrip) PagerSlidingTabStrip tabStrip;
    @BindView(R.id.progressbar) SmoothProgressBar mProgressBar;

    private boolean isNewTrip;
    private String tripName;
    private String tripId;
    private String userId = "";
    private StorageReference userRef;
    private DatabaseReference mFirebaseDatabaseReference;
    Trip tripDetails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        if(getIntent() != null){
            tripName = getIntent().getStringExtra(Constants.TRIP_NAME);
            tripId = getIntent().getStringExtra(Constants.TRIP_ID);
            getSupportActionBar().setTitle(tripName);
            collapsingToolbar.setTitle(tripName);
            Glide.with(this).load(R.mipmap.goldengate).centerCrop().into(ivBackdrop);
        }
        Log.d(Constants.TAG,"user == " + FirebaseUtil.getCurrentUserId() + ", " + FirebaseUtil.getCurrentUserName());

        viewTripPagerAdapter = new ViewTripPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(viewTripPagerAdapter);
        vpPager.setCurrentItem(1);
        tabStrip.setViewPager(vpPager);

        userId = FirebaseUtil.getCurrentUserId();       /// TODO : update this to real user name
        if(getIntent() != null){
            isNewTrip = getIntent().getBooleanExtra(Constants.NEW_TRIP,false);
        }
        updateFirebaseStorage(isNewTrip,tripId);
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTripDetails();
    }

    private void getTripDetails() {

//        if(isNewTrip) {     /// TODO : Fix this as this is just a hack
            mFirebaseDatabaseReference.child("trips").child(tripId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Trip trip = dataSnapshot.getValue(Trip.class);
                            Log.d(TAG, "Trip name == " + trip.getName());
                            Log.d(TAG, "Trip id == " + trip.getId());
                            Log.d(TAG, "Trip owner == " + trip.getOwner().toString());

                            Map map = (HashMap<String,String>) dataSnapshot.getValue();

                            User owner = new User();
                            HashMap<String, String> mapOwners = (HashMap<String, String>) map.get("owner");
                            owner.setName(mapOwners.get("name"));
                            owner.setUid(mapOwners.get("uid"));
                            trip.setOwner(owner);

                            List<User> travellers = new ArrayList<User>();
                            List<HashMap<String, String>> listTravellers = (List<HashMap<String, String>>) map.get("Travellers");
                            for (HashMap<String, String> members : listTravellers) {
                                User member = new User();
                                member.setName(members.get("name"));
                                member.setUid(members.get("uid"));
                                travellers.add(member);
                            }
                            trip.setTravellers(travellers);

                            List<Memo> memoList = new ArrayList<Memo>();
                            List<HashMap<String, String>> listMemos = (List<HashMap<String, String>>) map.get("Memos");
                            if(listMemos != null) {
                                for (HashMap<String, String> allMemos : listMemos) {
                                    Memo memo = new Memo();
                                    memo.setMediaUrl(allMemos.get("mediaUrl"));
                                    memo.setText(allMemos.get("text"));
                                    memo.setType(allMemos.get("type"));
                                    Log.d(TAG, "Memo for trip == " + memo.toString());
                                    memoList.add(memo);
                                }
                                trip.setMemoList(memoList);
                            }
                            tripDetails = trip;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
//        }

    }

    private void updateFirebaseStorage(boolean isNewTrip, String tripId) {
        StorageReference reference = MemoApplication.getFBStorageReference();

        StorageReference tripRef = reference.child(tripId);
        userRef = tripRef.child(userId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.IMAGE_CAPTURE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String path = data.getStringExtra("FilePath");
                Log.d(TAG,"ViewTrip : File store here : " + path);
                Bitmap takenImage = BitmapFactory.decodeFile(path);
                storeMemoToFirebase(takenImage);
                File file = new File(path);
                if(file.exists())
                    file.delete();
            }
        }else if(requestCode == SELECT_PICTURE){
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    storeMemoToFirebase(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void storeMemoToFirebase(Bitmap bm) {
        mProgressBar.progressiveStart();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // shrink it down otherwise we will use stupid amounts of memory
        Bitmap bitmap = bm;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
//        String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

//        Memo memo = new Memo(new User("travis", "", ""), base64Image, "Dummy Text",Memo.TYPE_PHOTO);
//        mFirebaseDatabaseReference.child("trips").child(tripId).child("Memos").push().setValue(memo.toMap());

        uploadFile(true,bytes,userRef);
    }

    private void uploadFile(boolean isNewTrip, byte[] data, StorageReference userRef) {
        if(isNewTrip){

        }
        /*ArrayList<TripPhoto> photos = TripPhoto.createDemoTripPhotoList();
        int random = new Random().nextInt(photos.size());

        final int resId = photos.get(random).getPhotoUrl();
//        imageView.setDrawingCacheEnabled(true);
//        imageView.buildDrawingCache();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();*/
        String dateFormat = "ddMMyyyyHHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String fileName = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        StorageReference imageRef = userRef.child(fileName + ".jpg");
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                mProgressBar.progressiveStop();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d(Constants.TAG,"Download URL == " + downloadUrl.toString());

                Memo memo = new Memo(new User(FirebaseUtil.getCurrentUserName(), "", userId), downloadUrl.toString(), "Dummy Text",Memo.TYPE_PHOTO);
                HashMap<String, Object> result = new HashMap<>();

                List<Memo> memoList = new ArrayList<Memo>();
                if(tripDetails != null){
                    memoList = tripDetails.getMemoList();
                    if(memoList == null)
                        memoList = new ArrayList<Memo>();
                }
                memoList.add(memo);
//                result.put("Memos",memoList);
                mFirebaseDatabaseReference.child("trips").child(tripId).child("Memos").setValue(memoList);
                tripDetails.setMemoList(memoList);
                mProgressBar.progressiveStop();
            }
        });
        //Make sure to stop progressbar
        mProgressBar.progressiveStop();
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
        mProgressBar.setSmoothProgressDrawableCallbacks(new SmoothProgressDrawable.Callbacks() {
            @Override
            public void onStop() {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onStart() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        mProgressBar.progressiveStop();

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                // TODO: Do something with your menu items, or return false if you don't want to show them
                return true;
            }
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                switch (id){
                    case R.id.action_camera :
                        ToastText("Camera button clicked");
                        intent = new Intent(ViewTripActivity.this, AddCaptureActivity.class);
                        intent.putExtra("tripId", tripId);
                        startActivityForResult(intent,Constants.IMAGE_CAPTURE_REQUEST_CODE);
                        break;
                    case R.id.action_album :
                        ToastText("Album button clicked....Uploading a photo");
                        intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

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
//                collapsingToolbar.setTitle(tabTitle[position]);
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
                fragment =  new ViewTripInfoFragment().newInstance(tripId);
                hm.put(0, fragment);
            }else if(position == 1){
                fragment =  new ViewTripPhotoFragment().newInstance(tripId);
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
