package com.example.application.parentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.SendData;
import com.example.application.model.Point;
import com.example.application.util.PointAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class AllPointsActivity extends AppCompatActivity {

    private ListView listView;
    private PointAdapter adapter;
    private SharedPreferences sPref;
    private SendData sendData = new SendData();
    private List<Point> pointList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_controll);
        listView = findViewById(R.id.list);

        new Thread(() -> {
            try {
                pointList = getPoints();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                adapter = new PointAdapter(this, pointList);
                listView.setAdapter(adapter);
            });
        }).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), adapter.getItem(position).toString(),
//                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MapWayActivity.class);

                intent.putExtra("DATE", adapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private List<Point> getPoints() throws JSONException, IOException {
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        Response response = sendData.getPoints(String.valueOf(sPref.getInt("id", -1)));
        Type userListType = new TypeToken<ArrayList<Point>>(){}.getType();
        JSONArray jsonarray = new JSONArray(response.body().string());
        return new Gson().fromJson(jsonarray.toString(), userListType);
    }

}
