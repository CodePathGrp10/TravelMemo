package com.grp10.codepath.travelmemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grp10.codepath.travelmemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailFragment extends Fragment {


    @BindView(R.id.imgPhoto)
    ImageView coverImageView;

    @BindView(R.id.tvTripText)
    TextView txtTripName;


    @BindView(R.id.closeView)
    ImageView closeImg;


    public PhotoDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_photo_detail, container, false);
        ButterKnife.bind(this,rootView);
        int resourceId = getArguments().getInt("resId");
        String tripName = getArguments().getString("name");

        Glide.with(getActivity()).load(resourceId).into(coverImageView);
        txtTripName.setText(tripName);


        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return rootView;
    }

    public static Fragment newInstance(int resId, String name, String description, String id) {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("resId",resId);
        bundle.putString("name",name);
        bundle.putString("desc",description);
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }


}
