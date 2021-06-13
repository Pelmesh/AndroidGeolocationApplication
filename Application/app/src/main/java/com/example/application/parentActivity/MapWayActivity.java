package com.example.application.parentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.application.R;
import com.example.application.SendData;
import com.example.application.model.Geolocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class MapWayActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Geolocation> geolocationList;
    private SharedPreferences sPref;
    private SendData sendData = new SendData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_way);
        Intent intent = getIntent();
        new Thread(() -> {
            try {
                geolocationList = getHistory(intent.getStringExtra("DATE"));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            });
        }).start();
    }

    private List<Geolocation> getHistory(String date) throws IOException, JSONException {
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        Response response = sendData
                .getDayHistory(String.valueOf(sPref.getInt("id", -1)), date);
        Type userListType = new TypeToken<ArrayList<Geolocation>>() {
        }.getType();
        JSONArray jsonarray = new JSONArray(response.body().string());
        return new Gson().fromJson(jsonarray.toString(), userListType);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(getApplicationContext(), ParentActivity.class);
            startActivity(intent);
            return;
        }
        googleMap.setMyLocationEnabled(true);
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (Geolocation geo : geolocationList) {
            LatLng position = new LatLng(geo.getLatitude(), geo.getLongitude());
            googleMap.addMarker(
                    new MarkerOptions()
                            .position(position)
                            .title(geo.getDate())
                            .snippet(geo.getTime())
                            .draggable(false));
            options.add(position);
        }
        googleMap.addPolyline(options);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(geolocationList.get(0).getLatitude(), geolocationList.get(0).getLongitude()), 15.0f));
    }
}