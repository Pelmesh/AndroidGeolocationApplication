package com.example.application.parentActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;

public class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
    }
}