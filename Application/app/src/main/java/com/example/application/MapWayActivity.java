package com.example.application;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapWayActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_way);
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


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        List<LatLng> list = new ArrayList<>();
        list.add(new LatLng(-33.89157843985545, 151.21665650893928));
        list.add(new LatLng(-33.90896107270943, 151.2115066677134));
        list.add(new LatLng(-33.92263671837976, 151.21253663595857));
        Polyline newPolyline;
        PolylineOptions options = new PolylineOptions().width(3).color(Color.BLUE).geodesic(true);
        for (int i = 0;i<list.size();i++){
            Marker marker = googleMap.addMarker(
                    new MarkerOptions()
                            .position(list.get(i))
                            .title(String.valueOf(i))
                            .snippet("Cybggtn")
                            .draggable(false));
            options.add(list.get(i));
        }
        googleMap.addPolyline(options);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(1)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(list.get(1), 12.0f));
    }
}