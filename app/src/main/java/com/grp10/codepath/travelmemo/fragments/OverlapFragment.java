package com.grp10.codepath.travelmemo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.activities.TripActivity;
import com.grp10.codepath.travelmemo.asynctasks.ComputeDominantColorTask;
import com.grp10.codepath.travelmemo.interfaces.DominantColor;
import com.grp10.codepath.travelmemo.interfaces.FragmentLifecycle;
import com.grp10.codepath.travelmemo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverlapFragment extends Fragment implements DominantColor,FragmentLifecycle{


    @BindView(R.id.image_cover)
    ImageView coverImageView;

    @BindView(R.id.content_layout)
    RelativeLayout layout;

    @BindView(R.id.cardview)
    CardView cardView;

    Integer color;

    public OverlapFragment() {
        // Required empty public constructor
    }

    int resourceId;
    static final String ARG_RES_ID = "ARG_RES_ID";

    public static OverlapFragment newInstance(int resourceId) {
        OverlapFragment overlapFragment = new OverlapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_RES_ID, resourceId);
        overlapFragment.setArguments(bundle);
        return overlapFragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resourceId = getArguments().getInt(ARG_RES_ID);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trip_images, container, false);
        ButterKnife.bind(this,rootView);

        Glide.with(getActivity()).load(resourceId).into(coverImageView);
        SharedPreferences prefs = getActivity().getSharedPreferences("Colors", Context.MODE_PRIVATE);
        if(!prefs.contains(resourceId+"")) {
            Log.d(Constants.TAG,"we dont have Dominant color ==" + color);

            ComputeDominantColorTask computeDominantColorTask = new ComputeDominantColorTask(getActivity(), this);
            computeDominantColorTask.execute(resourceId);
        }else{
            color = prefs.getInt(resourceId + "",-1);
            cardView.setBackgroundColor(color);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDominantColorComputed(Integer color) {

        Log.d(Constants.TAG,"afterExec : Dominant color ==" + color);
//        layout.setBackgroundColor(color);
        cardView.setBackgroundColor(color);
        SharedPreferences prefs = getActivity().getSharedPreferences("Colors", Context.MODE_PRIVATE);
        prefs.edit().putInt(resourceId + "",color).apply();

    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment(AppCompatActivity activity) {
        int color =  activity.getSharedPreferences("Colors", Context.MODE_PRIVATE).getInt(resourceId+"",-1);
        Log.d(Constants.TAG,"+++Resume fragment= " + color);
        ((TripActivity)activity).setToolbar(color);
    }
}
