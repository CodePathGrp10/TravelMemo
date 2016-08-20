package com.grp10.codepath.travelmemo.firebase;

/**
 * Created by qiming on 8/18/2016.
 */
public class User {
//    String uid;
    String name;
    String profile_image_url;
//    Map<String, Boolean> trips;
//    Map<String, Boolean> memos;

    public User(String name, String profile_image_url, String uid) {
        this.name = name;
        this.profile_image_url = profile_image_url;
//        this.uid = uid;
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

//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
}
