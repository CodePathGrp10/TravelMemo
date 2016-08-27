package com.grp10.codepath.travelmemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.grp10.codepath.travelmemo.firebase.Memo;
import com.grp10.codepath.travelmemo.fragments.PhotoDetailFragment;

import java.util.ArrayList;

/**
 * Created by traviswkim on 8/20/16.
 */

public class ImageAdapter extends FragmentPagerAdapter {

    private ArrayList<Memo> memoList;

    public ImageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Memo memo = memoList.get(position);
        return PhotoDetailFragment.newInstance(memo.getMediaUrl(), memo.getText());
    }
    @Override
    public int getCount() {
        return memoList.size();
    }

    public void setMemoList(ArrayList<Memo> memoList) {
        this.memoList = memoList;
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