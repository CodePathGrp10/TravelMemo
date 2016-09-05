package com.grp10.codepath.travelmemo.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.activities.ViewTripActivity;
import com.grp10.codepath.travelmemo.firebase.FirebaseUtil;
import com.grp10.codepath.travelmemo.firebase.Trip;
import com.grp10.codepath.travelmemo.firebase.User;
import com.grp10.codepath.travelmemo.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by traviswkim on 8/16/16.
 */
public class ViewTripInfoFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.rvUserIconList)
    RecyclerView rvUserIconList;
    @BindView(R.id.tvTripNameText)
    TextView tvTripNameText;
    @BindView(R.id.tvTripDateText)
    TextView tvTripDateText;
    @BindView(R.id.tvTravelersText)
    TextView tvTravelersText;
    @BindView(R.id.tvTripDescText)
    TextView tvTripDescText;
    @BindView(R.id.nsvTripInfo)
    NestedScrollView nsvTripInfo;
    private DatabaseReference mFbDBReference;
    private String tripId;
    @BindView(R.id.tvTripName)
    TextView tvTripName;
    @BindView(R.id.tvTripDate)
    TextView tvTripDate;
    @BindView(R.id.tvTripDesc)
    TextView tvTripDesc;

    @BindView(R.id.view_trip_layout)
    LinearLayout viewTripLayout;

    @BindView(R.id.edit_trip_layout)
    LinearLayout editTripLayout;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.editTripName)
    EditText editTripName;

    @BindView(R.id.editTripDate)
    EditText editTripDate;

    @BindView(R.id.editTripDesc)
    EditText editTripDesc;

    private FirebaseRecyclerAdapter<User, UserIconViewHolder> mFirebaseAdapter;

    public static ViewTripInfoFragment newInstance(String tripId) {
        ViewTripInfoFragment viewTripInfoFragment = new ViewTripInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TRIP_ID, tripId);
        viewTripInfoFragment.setArguments(bundle);
        return viewTripInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_trip_detail_info, container, false);
        ButterKnife.bind(this, v);

//        setupTextFont();

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        getTripInfo();
        setupTravelerList(v);
        return v;
    }

    private void setupTextFont() {
        Typeface tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        Typeface tfBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        tvTripNameText.setTypeface(tfRegular);
        tvTripDateText.setTypeface(tfRegular);
        tvTravelersText.setTypeface(tfRegular);
        tvTripDescText.setTypeface(tfRegular);
    }

    public static class UserIconViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivUserIcon)
        CircleImageView ivUserIcon;
//        R.layout.item_user_icon

        public UserIconViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    private void setupTravelerList(View view) {
        rvUserIconList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mFirebaseAdapter = new FirebaseRecyclerAdapter<User,
                UserIconViewHolder>(
                User.class,
                R.layout.item_user_icon,
                UserIconViewHolder.class,
                FirebaseUtil.getTripsRef().child(tripId).child("Travellers").orderByChild("name")) {
            @Override
            protected void populateViewHolder(UserIconViewHolder viewHolder, User model, int position) {
                Glide.with(viewHolder.ivUserIcon.getContext())
                        .load(model.getProfile_image_url())
                        .into(viewHolder.ivUserIcon);
            }
        };
        rvUserIconList.setAdapter(mFirebaseAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getString(Constants.TRIP_ID);
        }
    }

    public void getTripInfo() {
        mFbDBReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mFbDBTripReference = mFbDBReference.child("trips").child(tripId);
        mFbDBTripReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trip trip = dataSnapshot.getValue(Trip.class);

                //Set trip information
                if (trip != null) {
                    tvTripName.setText(trip.getName());
                    //tvTripDate.setText(String.format("%s - %s", trip.getStart_date().toString(), trip.getEnd_at().toString()));
                    tvTripDesc.setText(trip.getDescription());
                    //DataSnapshot { key = -KPinKfmgOsZFl-55mNN, value = {name=a, description=b, owner={name=easy qian, uid=PtVXN1xZPxNGDufWtluplYl3ecZ2}, id=-KPinKfmgOsZFl-55mNN, Travellers={0={name=easy qian, uid=PtVXN1xZPxNGDufWtluplYl3ecZ2}}, Memos={0={text=Dummy Text, mediaUrl=https://firebasestorage.googleapis.com/v0/b/travelmemo-1de8a.appspot.com/o/fufu%2Fcom.google.android.gms.internal.zzafu%40fc82d7e%2F20082016170329.jpg?alt=media&token=a0b80a34-222d-4b05-b1e6-a40904a50dc1, type=photo, owner={name=akshat, uid=hIe5vkxzI4h43MOo91MbNdAesBk2}, createAt=1471737811787}}, isFavorite=false} }
                    Log.d(Constants.TAG + getClass().getName(), "Key=" + dataSnapshot.getKey() + " value= " + trip.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG + getClass().getName(), databaseError.getMessage());
            }

        });
    }

    public void editTrip() {
        editTripLayout.setVisibility(View.VISIBLE);
        viewTripLayout.setVisibility(View.GONE);


        editTripName.setText(tvTripName.getText());
        editTripDate.setText(tvTripDate.getText());
        editTripDesc.setText(tvTripDesc.getText());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                restoreViewUI();
                break;
            case R.id.btnSave:
                updateTripInfo();


        }
    }

    private void restoreViewUI() {
        editTripLayout.setVisibility(View.GONE);
        viewTripLayout.setVisibility(View.VISIBLE);
        ((ViewTripActivity) getActivity()).showFabIcon();
    }

    private void updateTripInfo() {

        mFbDBReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mFbDBTripreference = mFbDBReference.child("trips").child(tripId);
        final Map<String, Object> mapUpdates = new HashMap<>();
        mapUpdates.put("name", editTripName.getText().toString());
        mapUpdates.put("description", editTripDesc.getText().toString());

        final DatabaseReference mUserTripRef = mFbDBReference.child("user-trips").child(FirebaseUtil.getCurrentUserId()).child("trips").child(tripId);

//        Map<String,Object> tripUpdates = new HashMap<>();
//        tripUpdates.put("/trips/" + tripId,mapUpdates);
//        tripUpdates.put("/user-trips/" + FirebaseUtil.getCurrentUserId() + "/trips/" + tripId,mapUpdates);

        mFbDBTripreference.updateChildren(mapUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null || databaseError.getCode() >= 0) {
                    Toast.makeText(getActivity(), "Successfully updated the trip info.", Toast.LENGTH_SHORT).show();
                    mUserTripRef.updateChildren(mapUpdates);
                    restoreViewUI();

                    tvTripName.setText(editTripName.getText());
                    tvTripDate.setText(editTripDate.getText());
                    tvTripDesc.setText(editTripDesc.getText());

                } else {
                    Toast.makeText(getActivity(), "Error updating the trip info. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // On success do this:

    }
}
