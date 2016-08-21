package com.grp10.codepath.travelmemo.firebase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiming on 8/18/2016.
 */
public class Trip {
    private String id;
    private User owner;          // person who create the trip
    private String name;           // trip title/name
    private String description;            // trip description
    private String profile_image_url;        // trip thumbnail
    private Date start_at;      // the date when trip starts
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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
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

    public Object getStart_date() {
        return start_at;
    }

    public void setStart_date(Date start_at) {
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
//    public Map<String, Boolean> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(Map<String, Boolean> participants) {
//        this.participants = participants;
//    }
}
