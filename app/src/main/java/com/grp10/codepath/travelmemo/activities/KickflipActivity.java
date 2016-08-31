package com.grp10.codepath.travelmemo.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.firebase.FirebaseUtil;
import com.grp10.codepath.travelmemo.firebase.Memo;
import com.grp10.codepath.travelmemo.firebase.User;
import com.grp10.codepath.travelmemo.kickflip.SECRETS;
import com.grp10.codepath.travelmemo.utils.KickflipUtil;

import java.io.File;

import io.kickflip.sdk.Kickflip;
import io.kickflip.sdk.api.KickflipApiClient;
import io.kickflip.sdk.api.KickflipCallback;
import io.kickflip.sdk.api.json.Response;
import io.kickflip.sdk.api.json.Stream;
import io.kickflip.sdk.av.BroadcastListener;
import io.kickflip.sdk.av.SessionConfig;
import io.kickflip.sdk.exception.KickflipException;

public class KickflipActivity extends Activity {
    private static final String TAG = "KickflipActivity";
    private String mTripId;
    private boolean mKickflipReady = false;
    // By default, Kickflip stores video in a "Kickflip" directory on external storage
    private String mRecordingOutputPath = new File(Environment.getExternalStorageDirectory(), "MySampleApp/index.m3u8").getAbsolutePath();

    private KickflipApiClient mKickflip;
    private Stream mStream;

    private BroadcastListener mBroadcastListener = new BroadcastListener() {
        @Override
        public void onBroadcastStart() {
            Log.i(TAG, "onBroadcastStart");
        }

        @Override
        public void onBroadcastLive(Stream stream) {
            Log.i(TAG, "onBroadcastLive @ " + stream.getKickflipUrl());
            if (mStream == null) {
                mStream = stream;

                addStreamToTrip();
            }
        }

        @Override
        public void onBroadcastStop() {
            Log.i(TAG, "onBroadcastStop");

            finish();

            // If you're manually injecting the BroadcastFragment,
            // you'll want to remove/replace BroadcastFragment
            // when the Broadcast is over.

            //getFragmentManager().beginTransaction()
            //    .replace(R.id.container, MainFragment.getInstance())
            //    .commit();
        }

        @Override
        public void onBroadcastError(KickflipException error) {
            Log.i(TAG, "onBroadcastError " + error.getMessage());
        }

    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kickflip);

        mTripId = getIntent().getStringExtra("tripId");
        mStream = null;

        // This must happen before any other Kickflip interactions
        Kickflip.setup(this, SECRETS.CLIENT_KEY, SECRETS.CLIENT_SECRET, new KickflipCallback() {
            @Override
            public void onSuccess(Response response) {
                mKickflipReady = true;
                if (!handleLaunchingIntent()) {
                    startBroadcastingActivity();
                }
            }

            @Override
            public void onError(KickflipException error) {

            }
        });

    }

    private void addStreamToTrip() {
        User author = FirebaseUtil.getAuthor();
        Memo memo = new Memo(author, mStream.getStreamUrl(), "video", Memo.TYPE_VIDEO, mStream.getLatitude(), mStream.getLongitude());
        memo.setThumbnail_url(mStream.getThumbnailUrl().toString());
        DatabaseReference postRef = FirebaseUtil.getTripsRef().child(mTripId).child("Memos").push();
        postRef.setValue(memo);
        String memoId = postRef.getKey();
    }

    private void startBroadcastingActivity() {
        configureNewBroadcast();
        Kickflip.startBroadcastActivity(this, mBroadcastListener);
    }

    private void configureNewBroadcast() {
        // Should reset mRecordingOutputPath between recordings
        SessionConfig config = KickflipUtil.create720pSessionConfig(mRecordingOutputPath);
        //SessionConfig config = Util.create420pSessionConfig(mRecordingOutputPath);
        Kickflip.setSessionConfig(config);
    }

    private void startStreamClient() {
        mKickflip = new KickflipApiClient(KickflipActivity.this, SECRETS.CLIENT_KEY, SECRETS.CLIENT_SECRET, new KickflipCallback() {
            @Override
            public void onSuccess(Response response) {
                // Update profile display when we add that
                Toast.makeText(KickflipActivity.this, mKickflip.getActiveUser().getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(KickflipException error) {
                Toast.makeText(KickflipActivity.this, "Unable to contact Kickflip", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean handleLaunchingIntent() {
        Uri intentData = getIntent().getData();
        if (intentData != null) {
            Kickflip.startMediaPlayerActivity(this, intentData.toString(), true);
            finish();
            return true;
        }
        return false;
    }
}
