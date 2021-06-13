package com.example.application.parentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.application.R;
import com.example.application.SendData;
import com.example.application.model.Point;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Response;

public class PointOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private Point point;
    private GoogleMap mMap;
    private SendData sendData = new SendData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_on_map);
        new Thread(() -> {
            try {
                point = getPoint();
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            });
        }).start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        LatLng position = new LatLng(point.getLatitude(), point.getLongitude());
        googleMap.addMarker(
                new MarkerOptions()
                        .position(position)
                        .title(point.getName())
                        .draggable(false));
        options.add(position);

        googleMap.addPolyline(options);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0f));
    }


    private Point getPoint() throws IOException {
        Intent intent = getIntent();
        Response response = sendData.getPointById(intent.getIntExtra("ID", 0));
        return new Gson().fromJson(response.body().string(), Point.class);
    }

}
