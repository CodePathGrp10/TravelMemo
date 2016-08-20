package com.grp10.codepath.travelmemo.firebase;

/**
 * Created by qiming on 8/18/2016.
 */
public class Trip {
    private Author author;          // person who create the trip
    private String name;           // trip title/name
    private String text;            // trip description
    private String profile_image_url;        // trip thumbnail
    private Object start_at;      // the date when trip starts
    private Object end_at;        // the date when trip ends
//    private Map<String, Boolean> participants;      // persons who can view/post memos to this trip

    public Trip(Author author, String name, String text) {
        this.author = author;
        this.name = name;
        this.text = text;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Object getEnd_at() {
        return end_at;
    }

    public void setEnd_at(Object end_at) {
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

    public Object getStart_date() {
        return start_at;
    }

    public void setStart_date(Object start_at) {
        this.start_at = start_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public Map<String, Boolean> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(Map<String, Boolean> participants) {
//        this.participants = participants;
//    }
}
