package com.grp10.codepath.travelmemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.grp10.codepath.travelmemo.R;

public class AddCaptureActivity extends AppCompatActivity implements AddCaptureActivityFragment.AddCaptureListener {
    private String mTripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_capture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTripId = getIntent().getStringExtra("tripId");

        startAddCaptureFragment();
    }

    private void startAddCaptureFragment() {
        FragmentManager fm = getSupportFragmentManager();
        AddCaptureActivityFragment addCaptureActivityFragment = AddCaptureActivityFragment.newInstance(mTripId);
        addCaptureActivityFragment.show(fm, "fragment_add_capture");
    }

    @Override
    public void onFinishAddCapture(String path) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("FilePath",path);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
