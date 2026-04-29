package com.example.cookinti;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Set;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // sets the fragment the app starts with
        setCurrentFragment(new FeedFragment());

        int item = bottomNavigationView.getMenu().getItem(0).getItemId();
        findViewById(item).setTranslationZ(1.0f);
        Anims.ScaleAndMoveItem(findViewById(item), true).start();

        //ImageView circle = findViewById(R.id.circle_center);
        //circle.setTranslationX(findViewById(item).getTranslationX());
        //circle.setTranslationY(findViewById(item).getTranslationY());

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        // footer things, apparently if-else is better than switch for android
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {

            int itemId = menuItem.getItemId();

            // 1. Reset all icons first (optional, but ensures only one is raised)
            for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                int id = bottomNavigationView.getMenu().getItem(i).getItemId();
                View itemView = findViewById(id);
                if (itemView != null) {
                    Anims.ScaleAndMoveItem(itemView, false).start();
                }
            }

            // 2. Animate the specific clicked icon
            View selectedView = findViewById(itemId);
            if (selectedView != null) {
                Anims.ScaleAndMoveItem(selectedView, true).start();
            }
            if (itemId == R.id.feed) {
                setCurrentFragment(new FeedFragment());
            } else if (itemId == R.id.search) {
                setCurrentFragment(new SearchFragment());
            } else if (itemId == R.id.favourites) {
                setCurrentFragment(new FavouritesFragment());
            } else if (itemId == R.id.profile) {
                setCurrentFragment(new ProfileFragment());
            }

            // Return true to indicate that we handled the item click
            return true;
        });
    }



    // This function replaces the current fragment with the one passed as a parameter
    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out
                )
                // Replace the fragment inside the container with the new fragment
                .replace(R.id.fragment_container, fragment)
                // Commit the transaction to actually perform the change
                .commit();
    }
}