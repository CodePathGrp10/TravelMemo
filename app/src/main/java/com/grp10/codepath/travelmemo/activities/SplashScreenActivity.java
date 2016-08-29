package com.grp10.codepath.travelmemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.firebase.FirebaseUtil;
import com.grp10.codepath.travelmemo.firebase.User;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);


        Looper looper = Looper.getMainLooper();
        handler = new Handler(looper);

        mFirebaseAuth = FirebaseAuth.getInstance();
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Initialize Firebase Auth
                    mFirebaseUser = mFirebaseAuth.getCurrentUser();

                    if (mFirebaseUser == null) {
                        // Not signed in, launch the Sign In activity
                        startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
                        finish();
                        return;
                    } else {
                        addUserToFirebase(mFirebaseUser);

                        Intent mainAct = new Intent(SplashScreenActivity.this, TripActivity.class);
                        mainAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainAct);
                        finish();
                    }
                }
            },2000);


    }

    private void addUserToFirebase(final FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        FirebaseUtil.getUsersRef().child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    User user = new User(firebaseUser.getDisplayName(),
                            firebaseUser.getPhotoUrl().toString(), firebaseUser.getUid(), firebaseUser.getEmail());
                    FirebaseUtil.getUsersRef().child(firebaseUser.getUid()).setValue(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
