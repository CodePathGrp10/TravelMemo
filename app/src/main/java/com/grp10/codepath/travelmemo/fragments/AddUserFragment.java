package com.grp10.codepath.travelmemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.adapters.UsersArrayAdapter;
import com.grp10.codepath.travelmemo.firebase.FirebaseUtil;
import com.grp10.codepath.travelmemo.firebase.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AddUserFragment extends DialogFragment {

    @BindView(R.id.rvUserList)
    RecyclerView rvUserList;
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;
    private UsersArrayAdapter mUsersArrayAdapter;
    private ArrayList<User> mUsers;
//    private FirebaseRecyclerAdapter<User, UserViewHolder> mFirebaseAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddUserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, view);

//        mFirebaseAdapter = new FirebaseRecyclerAdapter<User,
//                UserViewHolder>(
//                User.class,
//                R.layout.item_user,
//                UserViewHolder.class,
//                FirebaseUtil.getUsersRef().orderByChild("name")) {
//            @Override
//            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
//                viewHolder.tvUserName.setText(model.getName());
//                Glide.with(viewHolder.ivUserProfile.getContext())
//                        .load(model.getProfile_image_url())
//                        .into(viewHolder.ivUserProfile);
//            }
//        };

        mUsers = new ArrayList<>();
//        addFakeUsers();
        mUsersArrayAdapter = new UsersArrayAdapter(mUsers);

        FirebaseUtil.getUsersRef().orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if (user.getName() != null)
                        mUsers.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseUtil.getTripsRef().child("trip id").child("travellers").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    users.add(user);
                }
                mUsers.removeAll(users);
                mUsersArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Set the adapter

        Context context = view.getContext();
        RecyclerView recyclerView = rvUserList;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
//            recyclerView.setAdapter(new ItemUserRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        recyclerView.setAdapter(mUsersArrayAdapter);

        return view;
    }

    private void addFakeUsers() {
        String[] strs = {
                "Vinutha Rumale", "https://lh3.googleusercontent.com/-5RPtgygnBHM/AAAAAAAAAAI/AAAAAAAATV0/eGXHyzd2hhI/s120-c/photo.jpg",
                "Akshat Jain", "https://lh3.googleusercontent.com/-zukcvSypOGU/AAAAAAAAAAI/AAAAAAAAHlI/EeWuW9BUVDE/s120-c/photo.jpg",
                "Travis Kim", "https://lh3.googleusercontent.com/-Bj5zqmLrD9k/AAAAAAAAAAI/AAAAAAAABg8/i4mqXxp85Cw/s120-c/photo.jpg",
                "Bobby Wei", "https://media.licdn.com/mpr/mpr/shrinknp_200_200/p/8/005/066/1e0/0afe3dc.jpg",
                "Charlie Hieger", "http://lh5.googleusercontent.com/-usU3U-Wf7L0/AAAAAAAAAAI/AAAAAAAAABM/qrZMke6grsY/photo.jpg",
                "Nathan Esquenazi", "http://sf15.techinclusion.co/wp-content/uploads/2015/09/nathan.jpg",
                "Supriya Premkumar", "https://yt3.ggpht.com/-35Q6X3k1eMc/AAAAAAAAAAI/AAAAAAAAAAA/on-w1N6hafw/s100-c-k-no-mo-rj-c0xffffff/photo.jpg",
                "Tarash Jain", "https://lh3.googleusercontent.com/-uzvVMiFHZkg/AAAAAAAAAAI/AAAAAAAABKM/pwjwVnfB6uY/s120-c/photo.jpg",
                "Sameer Natekar", "https://lh3.googleusercontent.com/-612THbTDmC8/AAAAAAAAAAI/AAAAAAAAGwE/hYJgTs2FbLI/s120-c/photo.jpg",
                "Divya Kondapalli", "https://lh3.googleusercontent.com/-lVoxZUSkhBA/AAAAAAAAAAI/AAAAAAAAEIA/XfdryGR6xSs/s120-c/photo.jpg",
                "Jenny Jung", "https://lh3.googleusercontent.com/-P_X-SdI3nvs/AAAAAAAAAAI/AAAAAAAAAEY/c4AGXl50G5Q/s120-c/photo.jpg"
        };
        for (int i = 0; i < strs.length; i += 2) {
            DatabaseReference postref = FirebaseUtil.getUsersRef().push();
            String uid = postref.getKey();
            User user = new User(strs[i], strs[i + 1], uid);
            postref.setValue(user);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static AddUserFragment newInstance(String tripId) {
        AddUserFragment frag = new AddUserFragment();
        Bundle args = new Bundle();
        args.putString("tripId", tripId);
        frag.setArguments(args);
        return frag;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction();
    }
}
