package com.grp10.codepath.travelmemo.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.fragments.AddUserFragment;

public class AddUserActivity extends AppCompatActivity implements AddUserFragment.OnListFragmentInteractionListener {
    private String mTripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTripId = getIntent().getStringExtra("tripId");

        startAddUserFragment();
    }

    private void startAddUserFragment() {
        FragmentManager fm = getSupportFragmentManager();
        AddUserFragment addUserFragment = AddUserFragment.newInstance(mTripId);
        addUserFragment.show(fm, "fragment_add_user");
    }

//    @Override
//    public void onFinishAddCapture(String path) {
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("FilePath",path);
//        setResult(RESULT_OK,returnIntent);
//        finish();
//    }

    @Override
    public void onListFragmentInteraction() {
        Toast.makeText(this, "added user", Toast.LENGTH_SHORT).show();
    }
}
