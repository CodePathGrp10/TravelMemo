package com.grp10.codepath.travelmemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.utils.Constants;

import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddCaptureActivityFragment extends DialogFragment {
    private static final String TAG = Constants.TAG;
    private static final int TAKE_PHOTO_CODE = 1;
    private static final int PICK_PHOTO_CODE = 2;
    private String mTripId;
    public String photoFileName = "photo.jpg";
    AddCaptureListener mAddCaptureListener;

    public static AddCaptureActivityFragment newInstance(String tripId) {
        AddCaptureActivityFragment frag = new AddCaptureActivityFragment();
        Bundle args = new Bundle();
        args.putString("tripId", tripId);
        frag.setArguments(args);
        return frag;
    }

    public interface AddCaptureListener {
        void onFinishAddCapture(String filePath);
        void onNoImageCaptured();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAddCaptureListener = (AddCaptureListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public AddCaptureActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_capture, container, false);

        /*
        FloatingActionButton fabAddCapture = (FloatingActionButton) view.findViewById(R.id.fabAddCapture);
        fabAddCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(getContext(),"save photo", Toast.LENGTH_LONG).show();
                // upload photo
                // create new memo
                dismiss();
            }
        });
        */

        mTripId = getArguments().getString("tripId");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mTripId = getArguments().getString("tripId");
        startCameraApp();
    }

    private void startCameraApp() {
        // Take the user to the camera app
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getView().getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, TAKE_PHOTO_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TAKE_PHOTO_CODE) {
                // Extract the photo that was just taken by the camera
                if (resultCode == Activity.RESULT_OK) {
                    Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                    // by this point we have the camera photo on disk
                    Log.d(TAG,"Image saved here : " + takenPhotoUri.getPath());
                    mAddCaptureListener.onFinishAddCapture(takenPhotoUri.getPath());

                    // RESIZE BITMAP, see section below
                    // Load the taken image into a preview
                } else { // Result was a failure
                    Toast.makeText(getView().getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
                // Call the method below to trigger the cropping
                // cropPhoto(photoUri)
            } else if (requestCode == PICK_PHOTO_CODE) {
                // Extract the photo that was just picked from the gallery

                // Call the method below to trigger the cropping
                // cropPhoto(photoUri)
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            mAddCaptureListener.onNoImageCaptured();
            dismiss();
        }
    }


    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getView().getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

}
