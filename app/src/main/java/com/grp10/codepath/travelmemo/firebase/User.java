package com.grp10.codepath.travelmemo.firebase;

import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiming on 8/18/2016.
 */
public class User implements Parcelable {
//    String uid;
    String name;
    String profile_image_url;
    private String uid;
    private String emailId;


    public User() {
    }

    public User(String name, String profile_image_url, String uid, String emailId) {
        this.name = name;
        this.profile_image_url = profile_image_url;
        this.uid = uid;
        this.emailId = emailId;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", email='").append(emailId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Map<String,Object> toMap() {
        HashMap<String,Object> results = new HashMap<>();
        results.put("uid",uid);
        results.put("name",name);
        results.put("profile_image_url", profile_image_url);
        results.put("emailId",emailId);
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.profile_image_url);
        dest.writeString(this.uid);
        dest.writeString(this.emailId);
    }

    protected User(android.os.Parcel in) {
        this.name = in.readString();
        this.profile_image_url = in.readString();
        this.uid = in.readString();
        this.emailId = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(android.os.Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
