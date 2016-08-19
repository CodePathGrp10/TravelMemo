package com.grp10.codepath.travelmemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.adapters.TripPhotosAdapter;
import com.grp10.codepath.travelmemo.models.TripPhoto;
import com.grp10.codepath.travelmemo.utils.SpacesItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by traviswkim on 8/18/16.
 */
public class TripPhotoFragment extends Fragment {
    ArrayList<TripPhoto> tripPhotos;
    @BindView(R.id.rvTripPhotos) RecyclerView rvTripPhotos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_photos, parent, false);
        ButterKnife.bind(this, v);

        tripPhotos = TripPhoto.createDemoTripPhotoList();
        TripPhotosAdapter adapter = new TripPhotosAdapter(getContext(), tripPhotos);
        rvTripPhotos.setAdapter(adapter);
        adapter.notifyDataSetChanged();// First param is number of columns and second param is orientation i.e Vertical or Horizontal
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        // Attach the layout manager to the recycler view
        rvTripPhotos.setLayoutManager(gridLayoutManager);

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        rvTripPhotos.addItemDecoration(decoration);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
