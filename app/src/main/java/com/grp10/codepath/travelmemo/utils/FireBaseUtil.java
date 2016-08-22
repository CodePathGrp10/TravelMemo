package com.grp10.codepath.travelmemo.utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by traviswkim on 8/22/16.
 */
public class FireBaseUtil {

    public static boolean isUserLoggedin(String className){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(Constants.TAG + className, user.getDisplayName());
            return true;
        } else {
            Log.d(Constants.TAG + className, "user is not logged in");
            return false;
        }
    }

//    public static boolean isFBDBConnected(String className){
//        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
//        boolean value;
//        connectedRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                boolean connected = snapshot.getValue(Boolean.class);
//                if (connected) {
//                    Log.d("TRAVIS", "connected");
//                    value = true;
//                } else {
//                    Log.d("TRAVIS", "not connected");
//                    value = false;
//                }
//
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                System.err.println("Listener was cancelled");
//            }
//        });
//        return value;
//    }
}
