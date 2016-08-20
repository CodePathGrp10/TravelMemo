package com.grp10.codepath.travelmemo.firebase;

/**
 * Created by qiming on 8/19/2016.
 */
public class Author {
    /** Author class is member of Trip and Memo which provides fast way to get name and thumbnail */
    private String full_name;
    private String profile_picture;
    private String uid;

    public Author() {

    }

    public Author(String full_name, String profile_picture, String uid) {
        this.full_name = full_name;
        this.profile_picture = profile_picture;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }
}