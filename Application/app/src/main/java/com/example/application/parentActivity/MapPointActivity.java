package com.example.application.parentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.application.R;
import com.example.application.SendData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;


public class MapPointActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private SharedPreferences sPref;
    private SendData sendData = new SendData();
    private Response response;
    private Marker marker;
    private Location myLocation;
    private static final int MY_PERMISSIONS_REQUEST_GPS = 1;
    private EditText editText;
    private TextView error;
    private static final String ERROR = "Ошибка";

    public MapPointActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_point);
        editText = findViewById(R.id.editText);
        error = findViewById(R.id.error);
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapPointActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// разрешение не предоставлено
                ActivityCompat.requestPermissions(MapPointActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
            }
        }
        googleMap.setMyLocationEnabled(true);
        myLocation = getMyLocation();

        LatLng startLatLNG = new LatLng(0, 0);

        if (myLocation != null)
            startLatLNG = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

        marker = googleMap.addMarker(
                new MarkerOptions()
                        .position(startLatLNG)
                        .draggable(true));
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            LatLng temp = null;

            @Override
            public void onMarkerDragStart(Marker marker) {
                temp = marker.getPosition();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                marker.setPosition(temp);
                myLocation = googleMap.getMyLocation();
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

    public void savePoint(View view) throws JSONException {
        if (editText.getText().toString().equals("")) {
            error.setText(ERROR);
            return;
        }
        JSONObject userJson = new JSONObject();
        userJson.put("id", sPref.getInt("id", -1));
        JSONObject json = new JSONObject();
        json.put("user", userJson);
        json.put("name", editText.getText());
        json.put("latitude", myLocation.getLatitude());
        json.put("longitude", myLocation.getLongitude());
        new Thread(() -> {
            try {
                response = sendData.savePoint(json);
            } catch (Exception e) {
                return;
            }
            runOnUiThread(() -> {
                if (response.code() == 200) {
                    Intent intent = new Intent(getApplicationContext(), ParentActivity.class);
                    startActivity(intent);
                } else {
                    error.setText(ERROR);
                }
            });
        }).start();
    }

    private Location getMyLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapPointActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// разрешение не предоставлено
                ActivityCompat.requestPermissions(MapPointActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
            }
        }
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            String provider = lm.getBestProvider(criteria, true);
            myLocation = lm.getLastKnownLocation(provider);
        }
        return myLocation;
    }
}