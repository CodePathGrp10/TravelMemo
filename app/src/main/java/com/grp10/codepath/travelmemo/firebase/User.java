package com.grp10.codepath.travelmemo.firebase;

/**
 * Created by qiming on 8/18/2016.
 */
public class User {
    String uid;
    String name;
    String photoUrl;

    public User(String name, String photoUrl, String uid) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.uid = uid;
    }
}
