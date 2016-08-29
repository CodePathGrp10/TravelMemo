package com.grp10.codepath.travelmemo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.firebase.Trip;
import com.grp10.codepath.travelmemo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

/**
 * Created by traviswkim on 8/16/16.
 */
public class ViewTripInfoFragment extends Fragment {
    private DatabaseReference mFbDBReference;
    private String tripId;
    @BindView(R.id.tvTripName) TextView tvTripName;
    @BindView(R.id.tvTripDate) TextView tvTripDate;
    @BindView(R.id.tvTripDesc) TextView tvTripDesc;
    @BindView(R.id.fabInfo) FabSpeedDial fabSDInfo;

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
        getTripInfo();
        setListener();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            tripId = getArguments().getString(Constants.TRIP_ID);
        }
    }

    public void getTripInfo(){
        mFbDBReference = FirebaseDatabase.getInstance().getReference();
        mFbDBReference = mFbDBReference.child("trips").child(tripId);
        mFbDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trip trip = dataSnapshot.getValue(Trip.class);

                //Set trip information
                if(trip != null){
                    tvTripName.setText(trip.getName());
                    //tvTripDate.setText(String.format("%s - %s", trip.getStart_date().toString(), trip.getEnd_at().toString()));
                    tvTripDesc.setText(trip.getDescription());
                }

                //DataSnapshot { key = -KPinKfmgOsZFl-55mNN, value = {name=a, description=b, owner={name=easy qian, uid=PtVXN1xZPxNGDufWtluplYl3ecZ2}, id=-KPinKfmgOsZFl-55mNN, Travellers={0={name=easy qian, uid=PtVXN1xZPxNGDufWtluplYl3ecZ2}}, Memos={0={text=Dummy Text, mediaUrl=https://firebasestorage.googleapis.com/v0/b/travelmemo-1de8a.appspot.com/o/fufu%2Fcom.google.android.gms.internal.zzafu%40fc82d7e%2F20082016170329.jpg?alt=media&token=a0b80a34-222d-4b05-b1e6-a40904a50dc1, type=photo, owner={name=akshat, uid=hIe5vkxzI4h43MOo91MbNdAesBk2}, createAt=1471737811787}}, isFavorite=false} }
                Log.d(Constants.TAG + getClass().getName(), "Key=" + dataSnapshot.getKey() + " value= " + trip.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.TAG + getClass().getName(), databaseError.getMessage());
            }

        });
    }

    public void setListener(){
        fabSDInfo.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                // TODO: Do something with your menu items, or return false if you don't want to show them
                return true;
            }
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                switch (id){
                    case R.id.action_add_user :
//                        Toast.makeText(getContext(), "Add user button clicked").show();
                        break;
                }
                //TODO: Start some activity
                return false;
            }
        });
    }
}
