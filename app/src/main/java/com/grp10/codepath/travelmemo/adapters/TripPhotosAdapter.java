package com.grp10.codepath.travelmemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.activities.ViewPhotoActivity;
import com.grp10.codepath.travelmemo.models.TripPhoto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by traviswkim on 8/18/16.
 */
public class TripPhotosAdapter extends RecyclerView.Adapter<TripPhotosAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivTripPhoto) ImageView tripPhoto;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private List<TripPhoto> mTripPhotos;
    private Context mContext;

    public TripPhotosAdapter(Context context, List<TripPhoto> tripPhotos){
        this.mContext = context;
        this.mTripPhotos = tripPhotos;
    }

    private Context getContext(){
        return mContext;
    }

    @Override
    public TripPhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_trip_photo, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TripPhotosAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        TripPhoto tripPhoto = mTripPhotos.get(position);

        Glide.with(getContext()).load(tripPhoto.getPhotoUrl())
                .fitCenter().into(viewHolder.tripPhoto);

        viewHolder.tripPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ViewPhotoActivity.class);
                mContext.startActivity(i);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTripPhotos.size();
    }

}
