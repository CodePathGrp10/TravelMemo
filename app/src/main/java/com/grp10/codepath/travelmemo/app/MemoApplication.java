package com.grp10.codepath.travelmemo.app;

import android.support.multidex.MultiDexApplication;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grp10.codepath.travelmemo.utils.Constants;

/**
 * Created by akshatjain on 8/18/16.
 */
public class MemoApplication extends MultiDexApplication {

    private static AuthResult mAuthResult;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static StorageReference getFBStorageReference(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(Constants.FIREBASE_STORAGE_URL);

        return storageRef;
    }

    public static void setFirebaseUser(AuthResult authResult){
        mAuthResult = authResult;
    }

    public static AuthResult getAuthResult() {
        return mAuthResult;
    }
}
