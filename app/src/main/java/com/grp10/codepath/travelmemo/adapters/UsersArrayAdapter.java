package com.grp10.codepath.travelmemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.firebase.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 8/24/2016.
 */
public class UsersArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<User> mUsers;
    private Set<User> mSelectedUsers;
    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mOnProfileImageViewClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public UsersArrayAdapter(ArrayList<User> users) {
        mUsers = users;
        mSelectedUsers = new HashSet<>();
    }

    public void addAll(List<User> users) {
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivUserProfile)
        CircleImageView ivUserProfile;
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.ivUserSelected)
        ImageView ivUserSelected;
//        R.layout.item_user

        public UserViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.item_user, parent, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView recyclerView = (RecyclerView) view.getParent();
                UserViewHolder viewHolder = (UserViewHolder) recyclerView.getChildViewHolder(view);
                User user = mUsers.get(viewHolder.getAdapterPosition());
                boolean selected = viewHolder.ivUserSelected.getVisibility() == View.VISIBLE ? false : true;
                if (selected) {
                    viewHolder.ivUserSelected.setVisibility(View.VISIBLE);
                    mSelectedUsers.add(user);
                } else {
                    viewHolder.ivUserSelected.setVisibility(View.GONE);
                    mSelectedUsers.remove(user);
                }
//                mOnClickListener.onClick(view);
            }
        });
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = mUsers.get(position);
        UserViewHolder vh = (UserViewHolder) holder;
        vh.tvUserName.setText(user.getName());
        Glide.with(vh.ivUserProfile.getContext())
                .load(user.getProfile_image_url())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(vh.ivUserProfile);
    }

    @Override
    public int getItemCount() {
        if (mUsers == null)
            return 0;
        else
            return mUsers.size();
    }
}
