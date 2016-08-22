package com.grp10.codepath.travelmemo.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.adapters.ImageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPhotoActivity extends AppCompatActivity {
    @BindView(R.id.vpPhotos) ViewPager vpPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo2);
        ButterKnife.bind(this);

        ImageAdapter adapter = new ImageAdapter(this);
        vpPhotos.setAdapter(adapter);
    }
}