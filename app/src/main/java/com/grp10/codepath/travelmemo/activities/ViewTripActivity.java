package com.grp10.codepath.travelmemo.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.fragments.ViewTripInfoFragment;
import com.grp10.codepath.travelmemo.fragments.ViewTripPhotoFragment;
import com.grp10.codepath.travelmemo.utils.Constants;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewTripActivity extends AppCompatActivity {
    private String tabTitle[] = {"Info", "Photos"};
    ViewTripPagerAdapter viewTripPagerAdapter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fabAddPhoto) FloatingActionButton fabAddPhoto;
    @BindView(R.id.view_trip_viewpager) ViewPager vpPager;
    @BindView(R.id.view_trip_tabstrip) PagerSlidingTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent() != null){
            String tripName = getIntent().getStringExtra(Constants.TRIP_NAME);
            getSupportActionBar().setTitle(tripName);
        }
        viewTripPagerAdapter = new ViewTripPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(viewTripPagerAdapter);
        tabStrip.setViewPager(vpPager);
        setListener();
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
        fabAddPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Fab Button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0){
                    fabAddPhoto.show();
                }else{
                    fabAddPhoto.hide();
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
                fragment =  new ViewTripPhotoFragment();
                hm.put(0, fragment);
            }else if(position == 1){
                fragment =  new ViewTripInfoFragment();
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
}
