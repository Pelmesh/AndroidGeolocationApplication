package com.example.application.parentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.SignInActivity;
import com.example.application.SignUpActivity;

public class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
    }


    public void openHistory(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void openPoint(View view) {
        Intent intent = new Intent(this, AllPointsActivity.class);
        startActivity(intent);
    }

    public void addPoint(View view) {
        Intent intent = new Intent(this, MapPointActivity.class);
        startActivity(intent);
    }

}