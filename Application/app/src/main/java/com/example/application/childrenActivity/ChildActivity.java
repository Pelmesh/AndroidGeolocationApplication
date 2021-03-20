package com.example.application.childrenActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.application.R;

public class ChildActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_GPS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        startService(new Intent(ChildActivity.this, GPSTrackerService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ChildActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// разрешение не предоставлено
                ActivityCompat.requestPermissions(ChildActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
            }
        }
    }
}