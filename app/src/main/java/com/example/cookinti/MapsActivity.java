package com.example.cookinti;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.cookinti.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button returnButton = (Button) findViewById(R.id.buttonReturn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        getSupportActionBar().setTitle("Find grocery stores");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng max1 = new LatLng(54.561044273714565, 23.354193104717396);
        mMap.addMarker(new MarkerOptions().position(max1).title("PC Maxima"));

        max1 = new LatLng(54.894494199775785, 23.924643072535385);
        mMap.addMarker(new MarkerOptions().position(max1).title("PC Maxima"));

        max1 = new LatLng(54.67850366295835, 25.27321697848459);
        mMap.addMarker(new MarkerOptions().position(max1).title("PC Maxima"));

        max1 = new LatLng(54.71303206154214, 25.269535587123766);
        mMap.addMarker(new MarkerOptions().position(max1).title("PC Maxima"));

        max1 = new LatLng(55.697143426427594, 21.193056421260845);
        mMap.addMarker(new MarkerOptions().position(max1).title("PC Maxima"));

        max1 = new LatLng(54.90043173615922, 23.968905020591645);
        mMap.addMarker(new MarkerOptions().position(max1).title("PC Maxima"));



        //mMap.moveCamera(CameraUpdateFactory.newLatLng(max1));
        mMap.setMinZoomPreference(6);
        mMap.setMaxZoomPreference(21);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(max1));
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

        return false;
    }
}