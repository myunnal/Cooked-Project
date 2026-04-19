package com.example.cookinti;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FollowersRecyclerView extends RecyclerView.Adapter<FollowersRecyclerView.FollowHolder> {

    private AppDatabase db;
    private List<User> users;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class FollowHolder extends RecyclerView.ViewHolder {
        private final TextView followers;
        private final TextView UserName;
        private final TextView descriptionText;
        private final ImageView profileImage;
        private final Button follow;

        public FollowHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            followers = (TextView) view.findViewById(R.id.followers);
            UserName = (TextView) view.findViewById(R.id.UserName);
            descriptionText = (TextView) view.findViewById(R.id.descriptionText);
            profileImage = (ImageView) view.findViewById(R.id.profileImage);
            follow = (Button) view.findViewById(R.id.follow);
        }

        public TextView getFollowers() {
            return followers;
        }

        public TextView getUserName() {
            return UserName;
        }

        public TextView getDescriptionText() {
            return descriptionText;
        }
        public ImageView getProfileImage() {
            return profileImage;
        }
        public Button getFollow() {
            return follow;
        }
    }



    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public FollowersRecyclerView(List<User> dataSet, AppDatabase database)
    {
        users = dataSet;
        db = database;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FollowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_follow_item, viewGroup, false);

        return new FollowHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FollowHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        User rec = users.get(position);
        viewHolder.getUserName().setText(rec.getUsername());
        viewHolder.getDescriptionText().setText(rec.getBio());

        Uri uri;
        if (rec.getPfpLink() != null) {
            uri = Uri.parse(rec.getPfpLink());
            if (uri != null)
                viewHolder.getProfileImage().setImageURI(uri);
            else
                viewHolder.getProfileImage().setImageResource(R.drawable.basically_burger_1);
        }

        Context cntxt = viewHolder.getProfileImage().getContext();

        viewHolder.getUserName().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppActivity.CheckUserProfile(cntxt, rec.getId());
            }
        });

        Boolean notFollowing = db.followDao().isFollowing(AppActivity.currentSession.getId(), rec.getId()).isEmpty();
        if (!notFollowing)
            viewHolder.getFollow().setText("FOLLOWING");
        else
            viewHolder.getFollow().setText("FOLLOW");

        viewHolder.getFollowers().setText("Followers: " + db.followDao().getFollowers(rec.getId()).size());

        viewHolder.getFollow().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppActivity.FollowUser(AppActivity.currentSession.getId(), rec.getId());
                notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return users.size();
    }
}