package com.grp10.codepath.travelmemo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.data.DemoImages;
import com.grp10.codepath.travelmemo.firebase.FirebaseUtil;
import com.grp10.codepath.travelmemo.firebase.Trip;
import com.grp10.codepath.travelmemo.firebase.User;
import com.grp10.codepath.travelmemo.fragments.OverlapFragment;
import com.grp10.codepath.travelmemo.interfaces.FragmentLifecycle;
import com.grp10.codepath.travelmemo.utils.Constants;
import com.grp10.codepath.travelmemo.view.CustomToolbar;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class TripActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG;

    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;

//    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Drawer result;
    private MyFragmentPagerAdapter pagerAdapter;
    private DatabaseReference mFirebaseDatabaseReference;

    private String mUsername;
    private String mUserId;
    String username = "akshat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getMaterialDrawerMenu();
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        setupCarousal();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTripDialog();
            }
        });
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        // Initialize Current User
        mUsername = FirebaseUtil.getCurrentUserName();
        mUserId = FirebaseUtil.getCurrentUserId();
//        Constants.colorizeToolbar(toolbar,R.color.colorPrimary,this);
    }

    public void getMaterialDrawerMenu(){
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.HOME).withIcon(GoogleMaterial.Icon.gmd_home),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.SETTINGS).withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return true;
                    }
                })
                .build();

    }
    private void setupCarousal() {

        viewPager = pagerContainer.getViewPager();

        new CoverFlow.Builder().with(viewPager)
                .scale(0.3f)
                .pagerMargin(getResources().getDimensionPixelSize(R.dimen.overlap_pager_margin))
                .spaceSize(0f)
                .build();

        pagerContainer.setOverlapEnabled(true);

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        //Manually setting the first View to be elevated
        viewPager.post(new Runnable() {
            @Override public void run() {
                Fragment fragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
                ViewCompat.setElevation(fragment.getView(), 8.0f);
            }
        });

    }

    private void createTripDialog() {
        View messageView = LayoutInflater.from(TripActivity.this).
                inflate(R.layout.create_trip_dialog, null);
        // Create alert dialog builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set create_trip_dialog.xml to AlertDialog builder
        alertDialogBuilder.setView(messageView).setCancelable(false).setTitle("Create a new trip");
        alertDialogBuilder.setPositiveButton("Ok",null).setNegativeButton("Cancel",null);
        // Create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button btnPostive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button btnCancel = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                btnPostive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tripName = ((TextInputLayout) alertDialog.findViewById(R.id.tripName)).getEditText().
                                getText().toString();
                        String description = ((TextInputLayout) alertDialog.findViewById(R.id.description)).getEditText().
                                getText().toString();
                        if("".equals(tripName)){
                            TextView txtErr = (TextView) alertDialog.findViewById(R.id.errorMsg);
                            txtErr.setText("Enter a trip name...");
                            return;
                        }else {
                            createNewTrip(tripName,description);
                            dialogInterface.dismiss();
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogInterface.dismiss();
                    }
                });
            }
        });

        alertDialog.show();

    }

    private void createNewTrip(final String tripName, final String description) {

        User owner = new User(mUsername, null, mUserId);
        final String tripKey = mFirebaseDatabaseReference.child("trips").push().getKey();

        Log.d(Constants.TAG,"trip id == " + tripKey);
//            DatabaseReference newPostRef = postRef.push();
        Trip trip = new Trip(tripKey,owner, tripName, description);
//        String userId = getSharedPreferences("UserKey",MODE_PRIVATE).getString("userId",null);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/trips/" + tripKey, trip.toMap());
        childUpdates.put("/user-trips/" + mUserId + "/" + tripKey, trip.toMap());

        // Create a new trip
        mFirebaseDatabaseReference.child("trips").child(tripKey).setValue(trip.toMap());
        // Now create a new trip associated with the user.

        mFirebaseDatabaseReference.child("user-trips").child(mUserId).child("trips").child(tripKey).setValue(trip.toMap(), new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null || databaseError.getCode() >= 0) {
                    Log.d(Constants.TAG,"created a new trip id == ");

                    Intent viewTripIntent = new Intent(TripActivity.this, ViewTripActivity.class);
                    viewTripIntent.putExtra(Constants.TRIP_ID, tripKey);
                    viewTripIntent.putExtra(Constants.TRIP_NAME, tripName);
                    viewTripIntent.putExtra(Constants.DESCRIPTION, description);
                    viewTripIntent.putExtra(Constants.NEW_TRIP, true);
                    startActivity(viewTripIntent);
                }else{
                    Log.d(Constants.TAG,"error on creating a new trip id == " + databaseError.getMessage());

                }
            }
        });
    }

    public void setToolbar(Integer color) {
        toolbar.setItemColor(color);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return OverlapFragment.newInstance(DemoImages.covers[position]);
        }

        @Override
        public int getCount() {
            return DemoImages.covers.length;
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        int currentPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            FragmentLifecycle fragmentToShow = (FragmentLifecycle)pagerAdapter.getItem(position);
            fragmentToShow.onResumeFragment(TripActivity.this);

            FragmentLifecycle fragmentToHide = (FragmentLifecycle)pagerAdapter.getItem(currentPosition);
            fragmentToHide.onPauseFragment();

            currentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
