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

import static io.kickflip.sdk.Kickflip.isKickflipUrl;

public class KickflipActivity extends Activity {
    private static final String TAG = "KickflipActivity";
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
            mStream = stream;

            createExampleMemo();
        }

        @Override
        public void onBroadcastStop() {
            Log.i(TAG, "onBroadcastStop");
            // Play with Kickflip's built-in Media Player
//            Kickflip.startMediaPlayerActivity(KickflipActivity.this, mStream.getStreamUrl(), false);

//            startActivity(new Intent(KickflipActivity.this, TripActivity.class));
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

//        createExampleMemo();

        // This must happen before any other Kickflip interactions
        Kickflip.setup(this, SECRETS.CLIENT_KEY, SECRETS.CLIENT_SECRET, new KickflipCallback() {
            @Override
            public void onSuccess(Response response) {
                mKickflipReady = true;
                startBroadcastingActivity();
//                Kickflip.startMediaPlayerActivity(KickflipActivity.this, "https://kickflip.io/bb6ba0db-d541-4ed4-8d38-7b3391ed061f/", false);
//                Kickflip.startMediaPlayerActivity(KickflipActivity.this, "https://d3mf59dq42lc44.cloudfront.net/travelmemo/1qighnpcv6ue/487023f1-6475-496e-8aa6-44dfe8026cd9/index.m3u8", false);
            }

            @Override
            public void onError(KickflipException error) {

            }
        });

        if (!handleLaunchingIntent()) {
            if (savedInstanceState == null) {
//                startStreamClient();
            }
        }

    }

    private void createExampleMemo() {
        User author = FirebaseUtil.getAuthor();
        Memo memo = new Memo(author, mStream.getStreamUrl(), "video", Memo.TYPE_PHOTO, 0.0, 0.0);
        DatabaseReference postRef = FirebaseUtil.getTripsRef().child("-KQEjI8Dnar-pz4QRf_C").child("Memos").push();
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
        if (isKickflipUrl(intentData)) {
            Kickflip.startMediaPlayerActivity(this, intentData.toString(), true);
            finish();
            return true;
        }
        return false;
    }
}
