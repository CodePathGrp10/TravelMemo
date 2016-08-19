package com.grp10.codepath.travelmemo.interfaces;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by akshatjain on 8/11/16.
 */
public interface FragmentLifecycle {
    public void onPauseFragment();
    public void onResumeFragment(AppCompatActivity activity);
}
