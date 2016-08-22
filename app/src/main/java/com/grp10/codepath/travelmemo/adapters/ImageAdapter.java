package com.grp10.codepath.travelmemo.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grp10.codepath.travelmemo.R;

/**
 * Created by traviswkim on 8/20/16.
 */

public class ImageAdapter extends PagerAdapter {
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

    public ImageAdapter(Context context)
    {
        this.context=context;
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }

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
    }
}