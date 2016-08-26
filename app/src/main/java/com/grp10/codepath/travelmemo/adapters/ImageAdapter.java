package com.grp10.codepath.travelmemo.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.firebase.Trip;
import com.grp10.codepath.travelmemo.fragments.PhotoDetailFragment;

/**
 * Created by traviswkim on 8/20/16.
 */

public class ImageAdapter extends FragmentPagerAdapter {
    Context context;
    private int[] GalImages = new int[] {
            R.mipmap.goldengate,
            R.mipmap.ny,
            R.mipmap.grand_canyon,
            R.mipmap.shanghai,
            R.mipmap.yellowstone,
            R.mipmap.istanbu,
            R.mipmap.tokyo
    };

    public ImageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Trip trip = new Trip();
        trip.setName("Dummy trip");
        trip.setDescription("Dummy desc");
        trip.setId("RandomId");
        return PhotoDetailFragment.newInstance(GalImages[position % 6], trip.getName(), trip.getDescription(), trip.getId());
    }
    @Override
    public int getCount() {
        return GalImages.length;
    }

    /*
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(GalImages[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }*/
}