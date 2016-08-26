package com.grp10.codepath.travelmemo.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.adapters.ImageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class ViewPhotoActivity extends AppCompatActivity {
    @BindView(R.id.vpPhotos)
    ViewPager viewPager;

    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;

    ImageAdapter pagerAdapter;
    boolean isImmersive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo2);
        ButterKnife.bind(this);

        setupCarousal();
        updateCarousalView();

//        pagerContainer.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                    if (isImmersive)
//                        showSystemUI();
//                    else {
//                        goFullImmersive();
//                    }
//                }
//                return false;
//            }
//        });
    }

    private void setupCarousal() {

        viewPager = pagerContainer.getViewPager();

        //Set this to have a carousal effect
//        new CoverFlow.Builder().with(viewPager)
//                .scale(0.3f)
//                .pagerMargin(getResources().getDimensionPixelSize(R.dimen.overlap_pager_margin))
//                .spaceSize(0f)
//                .build();

        // Enable this for a linear page view
        viewPager.setPageMargin(30);

        viewPager.setClipChildren(false);

    }
    private void updateCarousalView() {

        if(pagerAdapter == null) {
            pagerAdapter = new ImageAdapter(getSupportFragmentManager());
        }
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.setAdapter(pagerAdapter);

        if(pagerAdapter.getCount() > 0) {
            //Manually setting the first View to be elevated
            viewPager.post(new Runnable() {
                @Override
                public void run() {
                    Fragment fragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
                    ViewCompat.setElevation(fragment.getView(), 8.0f);
                }
            });
        }

    }

    /*

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            goFullImmersive();
        }
    }

    private void goFullImmersive() {
        View decorView = getWindow().getDecorView();
        int visibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(
                visibility);
        isImmersive = true;
//        closeImg.setVisibility(View.GONE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        isImmersive = false;
//        closeImg.setVisibility(View.VISIBLE);
    }
    */
}