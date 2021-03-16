package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapPointActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private Marker marker;
    private Location myLocation;
    private GoogleMap g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_point);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        LatLng sydney = new LatLng(0, 0);
        marker = googleMap.addMarker(
                new MarkerOptions()
                        .position(sydney)
                        .draggable(true));
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            LatLng temp = null;
            @Override
            public void onMarkerDragStart(Marker marker) {
                // TODO Auto-generated method stub
                temp=marker.getPosition();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                marker.setPosition(temp);
                System.out.println(marker.getPosition());
                myLocation  = googleMap.getMyLocation();
                System.out.println("==============="+myLocation);
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                marker.setPosition(temp);
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }


    public void setOnMe(View view){
        LatLng latlng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        marker.setPosition(latlng);
    }

    public void savePoint(View view){

    }
}