package com.grp10.codepath.travelmemo.firebase;

/**
 * Created by qiming on 8/18/2016.
 */
public class Trip {
    private String id;
    private String title;
    private String body;
    private String photoUrl;

    public Trip(String title, String body, String photoUrl) {
        this.title = title;
        this.body = body;
        this.photoUrl = photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
