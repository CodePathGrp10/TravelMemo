package com.grp10.codepath.travelmemo.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.firebase.Memo;
import com.grp10.codepath.travelmemo.models.TripPhoto;
import com.grp10.codepath.travelmemo.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by traviswkim on 8/18/16.
 */
public class TripPhotoFragment extends Fragment {
    static final String ARG_TRIP_ID = "ARG_TRIP_ID";
    private DatabaseReference mFbDBReference;
    private String tripId;
    ArrayList<TripPhoto> tripPhotos;
    private LinearLayoutManager layoutManager;
//    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

    @BindView(R.id.rvTripPhotos) RecyclerView rvTripPhotos;

    public static TripPhotoFragment newInstance(int tripId) {
        TripPhotoFragment tripPhotoFragment = new TripPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TRIP_ID, tripId);
        tripPhotoFragment.setArguments(bundle);
        return tripPhotoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFbDBReference = FirebaseDatabase.getInstance().getReference();
        //tripId = getArguments().getString(ARG_TRIP_ID);
        tripId = "-KPinKfmgOsZFl-55mNN";
        mFbDBReference = mFbDBReference.child("trips").child(tripId).child("Memos");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_photos, parent, false);
        ButterKnife.bind(this, v);

//        tripPhotos = TripPhoto.createDemoTripPhotoList();
//        TripPhotosAdapter adapter = new TripPhotosAdapter(getContext(), tripPhotos);
//        rvTripPhotos.setAdapter(adapter);
//        adapter.notifyDataSetChanged();// First param is number of columns and second param is orientation i.e Vertical or Horizontal
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        // Attach the layout manager to the recycler view
//        rvTripPhotos.setLayoutManager(layoutManager);
//        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
//        rvTripPhotos.addItemDecoration(decoration);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(false);

        rvTripPhotos.setHasFixedSize(false);
        rvTripPhotos.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<Memo, PhotoViewHolder> adapter =
                new FirebaseRecyclerAdapter<Memo, PhotoViewHolder>(Memo.class, R.layout.item_trip_photo, PhotoViewHolder.class, mFbDBReference) {
                    @Override
                    protected void populateViewHolder(PhotoViewHolder viewHolder, Memo model, int position) {
                        //model - Memo{owner=akshat, type='photo', media_url='https://firebasestorage.googleapis.com/v0/b/travelmemo-1de8a.appspot.com/o/fufu%2Fcom.google.android.gms.internal.zzafu%40fc82d7e%2F20082016170329.jpg?alt=media&token=a0b80a34-222d-4b05-b1e6-a40904a50dc1'}
                        if(model.getType().equals("photo")){
                            String pictureString = model.getMediaUrl();
                            byte[] picture = Base64.decode(pictureString, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                            Glide.with(getContext()).load(picture)
                                .fitCenter().into(viewHolder.tripPhoto);
//                            viewHolder.tripPhoto.setImageBitmap(BitmapFactory.decodeByteArray(picture, 0, picture.length));
                            viewHolder.tripText.setText(model.getText());
                            Log.d(Constants.TAG + getClass().getName(), model.getMediaUrl());
//                            System.out.println("Downloaded image with length: " + picture.length);
                        }
                    }

                };

        // Scroll to bottom on new messages
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
//                layoutManager.smoothScrollToPosition(rvTripPhotos, null, adapter.getItemCount());
                layoutManager.smoothScrollToPosition(rvTripPhotos, null, adapter.getItemCount());
            }
        });
        rvTripPhotos.setAdapter(adapter);
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivTripPhoto) ImageView tripPhoto;
        @BindView(R.id.tvTripText) TextView tripText;
        public PhotoViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }


    }
}
