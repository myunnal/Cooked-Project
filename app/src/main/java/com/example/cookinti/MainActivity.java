package com.example.cookinti;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
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

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Sets the fragment the app starts with
        if (AppActivity.LastID == 4) {
            setCurrentFragment(new ProfileFragment());
        } else if (AppActivity.LastID == 2) {
            setCurrentFragment(new SearchFragment());
        } else if (AppActivity.LastID == 3) {
            setCurrentFragment(new FavouritesFragment());
        } else if (AppActivity.LastID == 5) {
            setCurrentFragment(new MapFragment());
        } else {
            setCurrentFragment(new FeedFragment());
        }

        int item = bottomNavigationView.getMenu().getItem(AppActivity.LastID - 1).getItemId();
        findViewById(item).setTranslationZ(1.0f);
        Anims.ScaleAndMoveItem(findViewById(item), true).start();

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {

            int itemId = menuItem.getItemId();

            // Reset all icons
            for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                int id = bottomNavigationView.getMenu().getItem(i).getItemId();
                View itemView = findViewById(id);
                if (itemView != null) {
                    Anims.ScaleAndMoveItem(itemView, false).start();
                }
            }

            // Animate the clicked icon
            View selectedView = findViewById(itemId);
            if (selectedView != null) {
                Anims.ScaleAndMoveItem(selectedView, true).start();
                Log.d("COCK", String.valueOf(21));
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
            } else if (itemId == R.id.map) {
                setCurrentFragment(new MapFragment());
                AppActivity.LastID = 5;
            }

            return true;
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out
                )
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
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