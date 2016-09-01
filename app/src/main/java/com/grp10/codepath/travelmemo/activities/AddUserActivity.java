package com.grp10.codepath.travelmemo.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.fragments.AddUserFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUserActivity extends AppCompatActivity implements AddUserFragment.OnListFragmentInteractionListener {

    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String mTripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTripId = getIntent().getStringExtra("tripId");
        String tripName = getIntent().getStringExtra("tripName");
        getSupportActionBar().setTitle(tripName);

        if (savedInstanceState == null) {
            Fragment addUserFragment = (Fragment) AddUserFragment.newInstance(mTripId);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.flContainer, addUserFragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction() {
        Toast.makeText(this, "added user", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
