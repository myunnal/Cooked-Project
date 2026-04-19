package com.example.cookinti;

import static androidx.constraintlayout.widget.ConstraintSet.INVISIBLE;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class UserRecipes extends AppCompatActivity {

    long userid;
    Boolean notFollowing;
    TextView userName;
    TextView pronouns;
    TextView followers;
    TextView bio;
    RecyclerView recyclerView;
    AppDatabase db;
    Button follow;

    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_recipes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppActivity.getDatabase();
        follow = findViewById(R.id.follow);
        followers = findViewById(R.id.followers);
        userName = findViewById(R.id.UserName);
        userid = getIntent().getExtras().getLong("userid");
        profileImage = (ImageView) findViewById(R.id.profileImage);

        DisplayUser();


        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notFollowing)
                {
                    db.followDao().insert(new Follow(AppActivity.currentSession.getId(), userid));
                }
                else
                {
                    db.followDao().removeFollow(AppActivity.currentSession.getId(), userid);
                }
                DisplayUser();
            }
        });

        recyclerView = findViewById(R.id.recycler_view2);
        RecipeFeedView recipeFeed = new RecipeFeedView(
                db.recipeDao().getUserRecipes(userid), db
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(recipeFeed);
    }

    void DisplayUser()
    {
        notFollowing = db.followDao().isFollowing(AppActivity.currentSession.getId(), userid).isEmpty();

        if (userid == AppActivity.currentSession.getId())
            follow.setVisibility(View.INVISIBLE);


        follow.setText(notFollowing ? "FOLLOW" : "FOLLOWING");
        followers.setText("Followers: " + db.followDao().getFollowers(userid).size());

        User user = db.userDao().getUser(userid);
        userName.setText(user.getUsername());

        Uri uri;
        if (user.getPfpLink() != null) {
            uri = Uri.parse(user.getPfpLink());
            if (uri != null)
                profileImage.setImageURI(uri);
            else
                profileImage.setImageResource(R.drawable.basically_burger_1);
        }
    }

}