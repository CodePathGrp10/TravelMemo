package com.grp10.codepath.travelmemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grp10.codepath.travelmemo.R;

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

        // Initialize Firebase Auth
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainAct = new Intent(SplashScreenActivity.this,TripActivity.class);
                    mainAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainAct);
                    finish();
                }
            },2000);
//            startActivity(new Intent(this, KickflipActivity.class));
//            finish();
        }
    }
}
