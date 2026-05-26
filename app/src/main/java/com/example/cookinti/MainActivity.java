package com.example.cookinti;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Set;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor mLight;
    BottomNavigationView bottomNavigationView;
    View root;

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

        root = findViewById(R.id.main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // sets the fragment the app starts with
        setCurrentFragment(new FeedFragment());

        /*int item = bottomNavigationView.getMenu().getItem(0).getItemId();
        findViewById(item).setTranslationZ(1.0f);
        Anims.ScaleAndMoveItem(findViewById(item), true).start();*/

    // PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI
        if (AppActivity.LastID == 4) {
            setCurrentFragment(new ProfileFragment());
        } else if (AppActivity.LastID == 2) {
            setCurrentFragment(new SearchFragment());
        } else if (AppActivity.LastID == 3) {
            setCurrentFragment(new FavouritesFragment());
        } else {
            setCurrentFragment(new FeedFragment());
        }

        int item = bottomNavigationView.getMenu().getItem(AppActivity.LastID - 1).getItemId();
        findViewById(item).setTranslationZ(1.0f);
        Anims.ScaleAndMoveItem(findViewById(item), true).start();
    // PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI PAKEITIMAI

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
                AppActivity.LastID = 1;
            } else if (itemId == R.id.search) {
                setCurrentFragment(new SearchFragment());
                AppActivity.LastID = 2;
            } else if (itemId == R.id.favourites) {
                setCurrentFragment(new FavouritesFragment());
                AppActivity.LastID = 3;
            } else if (itemId == R.id.profile) {
                setCurrentFragment(new ProfileFragment());
                AppActivity.LastID = 4;
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


    /*@Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            root.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        else
            root.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }*/

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = sensorEvent.values[0];

        if (lux < 100)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}