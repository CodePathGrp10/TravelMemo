package com.grp10.codepath.travelmemo.activities;

import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.fragments.ViewTripInfoFragment;
import com.grp10.codepath.travelmemo.fragments.ViewTripPhotoFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class ViewTripActivity extends AppCompatActivity {
    private String tabTitle[] = {"INFO", "PHOTO"};
    ViewTripPagerAdapter viewTripPagerAdapter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fabAddPhoto) FabSpeedDial fabSpeedDial;
    @BindView(R.id.view_trip_viewpager) ViewPager vpPager;
    @BindView(R.id.view_trip_tabstrip) PagerSlidingTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewTripPagerAdapter = new ViewTripPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(viewTripPagerAdapter);
        vpPager.setCurrentItem(1);
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
                        ToastText("Album button clicked");
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
