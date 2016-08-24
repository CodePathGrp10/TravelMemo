package com.grp10.codepath.travelmemo.firebase;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by qiming on 8/20/2016.
 */
public class FirebaseUtil {
    public static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    public static String getCurrentUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getEmail();
        }
        return "";
    }

    public static String getCurrentUserName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }
        return null;
    }

    public static Uri getCurrentUserProfilePhoto() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getPhotoUrl();
        }
        return null;
    }


    public static User getAuthor() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return null;
        return new User(user.getDisplayName(), user.getPhotoUrl().toString(), user.getUid());
    }

    public static DatabaseReference getCurrentUserRef() {
        String uid = getCurrentUserId();
        if (uid != null) {
            return getBaseRef().child("people").child(getCurrentUserId());
        }
        return null;
    }

    public static DatabaseReference getMemosRef() {
        return getBaseRef().child("memos");
    }

    public static String getMemosPath() {
        return "memos/";
    }

    public static DatabaseReference getUsersRef() {
        return getBaseRef().child("users");
    }

    public static DatabaseReference getTripsRef() {
        return getBaseRef().child("trips");
    }

    public static String getTripsPath() {
        return "trips/";
    }

    public static DatabaseReference getCommentsRef() {
        return getBaseRef().child("comments");
    }

    public static DatabaseReference getLikesRef() {
        return getBaseRef().child("likes");
    }

}
