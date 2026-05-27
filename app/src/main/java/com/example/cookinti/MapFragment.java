package com.example.cookinti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    Button findStoresButton;
    Button popularFoods;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findStoresButton = view.findViewById(R.id.button);
        findStoresButton.setOnClickListener(v -> {
            mMap.clear();

            LatLng location = new LatLng(54.561044273714565, 23.354193104717396);
            mMap.addMarker(new MarkerOptions().position(location).title("PC Maxima"));

            location = new LatLng(54.894494199775785, 23.924643072535385);
            mMap.addMarker(new MarkerOptions().position(location).title("PC Maxima"));

            location = new LatLng(54.67850366295835, 25.27321697848459);
            mMap.addMarker(new MarkerOptions().position(location).title("PC Maxima"));

            location = new LatLng(54.71303206154214, 25.269535587123766);
            mMap.addMarker(new MarkerOptions().position(location).title("PC Maxima"));

            location = new LatLng(55.697143426427594, 21.193056421260845);
            mMap.addMarker(new MarkerOptions().position(location).title("PC Maxima"));

            location = new LatLng(54.90043173615922, 23.968905020591645);
            mMap.addMarker(new MarkerOptions().position(location).title("PC Maxima"));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(54.5, 25.0), 6));
        });

        popularFoods = view.findViewById(R.id.button2);
        popularFoods.setOnClickListener(v -> {
            mMap.clear();

            LatLng location = new LatLng(38.737244523686016, -9.203189129569978);
            mMap.addMarker(new MarkerOptions().position(location).title("Pastel de nata"));

            location = new LatLng(55.18876604926591, 23.92016020510893);
            mMap.addMarker(new MarkerOptions().position(location).title("Šaltibarščiai"));

            location = new LatLng(52.409661451486464, 20.199842835804606);
            mMap.addMarker(new MarkerOptions().position(location).title("Pierogi"));

            location = new LatLng(47.67148576478451, 14.232254985450075);
            mMap.addMarker(new MarkerOptions().position(location).title("Wiener Schnitzel"));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(54.5, 25.0), 4));
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        mMap.setMinZoomPreference(1);
        mMap.setMaxZoomPreference(21);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(54.561044273714565, 23.354193104717396)));
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        return false;
    }
}