package com.grp10.codepath.travelmemo.firebase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiming on 8/18/2016.
 */
public class Trip implements Parcelable,Comparable {
    private String id;
    private User owner;          // person who create the trip
    private String name;           // trip title/name
    private String description;            // trip description
    private String profile_image_url;        // trip thumbnail
    private long start_at;      // the date when trip starts
    private Date end_at;        // the date when trip ends
    private boolean isFavorite;
//    private Map<String, Boolean> participants;      // persons who can view/post memos to this trip

    private List<Memo> memoList;
    private List<User> travellers;

    public Trip() {
    }

    public Trip(User owner, String name, String description) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        memoList = new ArrayList<>();
        travellers = new ArrayList<>();
        travellers.add(owner);      // he himself is part of the trip
    }

    public Trip(String id, User owner, String name, String description) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        memoList = new ArrayList<>();
        travellers = new ArrayList<>();
        travellers.add(owner);      // he himself is part of the trip
        start_at = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        isFavorite = isFavorite;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Object getEnd_at() {
        return end_at;
    }

    public void setEnd_at(Date end_at) {
        this.end_at = end_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public long getStartDate() {
        return start_at;
    }

    public void setStartDate(long start_at) {
        this.start_at = start_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Memo> getMemoList() {
        return memoList;
    }

    public void setMemoList(List<Memo> memoList) {
        this.memoList = memoList;
    }

    public List<User> getTravellers() {
        return travellers;
    }

    public void setTravellers(List<User> travellers) {
        this.travellers = travellers;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id",id);
        result.put("name",name);
        result.put("description", description);
        result.put("startDate",start_at);
        result.put("endDate",end_at);
        result.put("image_url",profile_image_url);
        result.put("isFavorite",isFavorite);
        result.put("owner", owner);
        result.put("Memos",memoList);
        result.put("Travellers",travellers);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        return id.equals(trip.id);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    //    public Map<String, Boolean> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(Map<String, Boolean> participants) {
//        this.participants = participants;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.owner, flags);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.profile_image_url);
        dest.writeLong(this.start_at);
        dest.writeLong(this.end_at != null ? this.end_at.getTime() : -1);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.memoList);
        dest.writeList(this.travellers);
    }

    protected Trip(Parcel in) {
        this.id = in.readString();
        this.owner = in.readParcelable(User.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.profile_image_url = in.readString();
        long start_at = in.readLong();
        long tmpEnd_at = in.readLong();
        this.end_at = tmpEnd_at == -1 ? null : new Date(tmpEnd_at);
        this.isFavorite = in.readByte() != 0;
        this.memoList = in.createTypedArrayList(Memo.CREATOR);
        this.travellers = new ArrayList<User>();
        in.readList(this.travellers, User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {
            return new Trip(source);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public int compareTo(Object o) {
        Trip trip = (Trip)o;
        if(this.start_at == trip.start_at)
            return 0;
        return this.start_at < trip.start_at ? -1 : 1;
    }
}
