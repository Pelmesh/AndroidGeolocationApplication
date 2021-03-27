package com.example.application;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.application.childrenActivity.ChildActivity;
import com.example.application.parentActivity.ParentActivity;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_GPS = 1;
    private SharedPreferences sPref;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// разрешение не предоставлено
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
            }
        }
        if (checkingActiveUser() && !role.equals("NULL")) {
            if (role.equals("parent")) {
                Intent intent = new Intent(getApplicationContext(), ParentActivity.class);
                startActivity(intent);
            } else if (role.equals("child")) {
                Intent intent = new Intent(getApplicationContext(), ChildActivity.class);
                startActivity(intent);
            }
        }
    }

    public void signIn(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private boolean checkingActiveUser() {
        role = sPref.getString("role", "NULL");
        return !(sPref.getString("user", "NULL").equals("NULL") || sPref.getInt("id", -1) == -1);
    }

}