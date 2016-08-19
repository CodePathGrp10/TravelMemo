package com.grp10.codepath.travelmemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grp10.codepath.travelmemo.R;

/**
 * Created by traviswkim on 8/16/16.
 */
public class ViewTripInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_trip_detail_photo, container, false);
    }
}
