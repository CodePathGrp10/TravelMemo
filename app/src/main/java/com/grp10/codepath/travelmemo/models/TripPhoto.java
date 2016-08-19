package com.grp10.codepath.travelmemo.models;

import com.grp10.codepath.travelmemo.data.DemoImages;

import java.util.ArrayList;

/**
 * Created by traviswkim on 8/18/16.
 */
public class TripPhoto {
    int photoUrl;

    public int getPhotoUrl() {
        return photoUrl;
    }

    public TripPhoto(){}

    public TripPhoto(int photoUrl){
        this.photoUrl = photoUrl;
    }
    public static ArrayList<TripPhoto> createDemoTripPhotoList() {
        ArrayList<TripPhoto> tripPhotos = new ArrayList<TripPhoto>();

        tripPhotos.add(new TripPhoto(DemoImages.covers[0]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[1]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[2]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[3]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[4]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[5]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[0]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[1]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[2]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[3]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[4]));
        tripPhotos.add(new TripPhoto(DemoImages.covers[5]));

        return tripPhotos;
    }
}
