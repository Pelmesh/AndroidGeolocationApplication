package com.example.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GPSTrackerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_tracker);

        startService(new Intent( GPSTrackerActivity.this,GPSTrackerService.class));
    }
}