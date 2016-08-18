package com.grp10.codepath.travelmemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.grp10.codepath.travelmemo.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainAct = new Intent(SplashScreenActivity.this,MainActivity.class);
                mainAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainAct);
                finish();
            }
        },2000);


    }


}
