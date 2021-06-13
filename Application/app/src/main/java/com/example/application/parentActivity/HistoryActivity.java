package com.example.application.parentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.SendData;
import com.example.application.model.DaysHistory;
import com.example.application.util.HistoryAdapter;
import com.example.application.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    private HistoryAdapter adapter;
    private SharedPreferences sPref;
    private SendData sendData = new SendData();
    private  List<DaysHistory> daysHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_controll);
        listView = (ListView) findViewById(R.id.list);

        new Thread(() -> {
            try {
                daysHistoryList = getHistory();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                adapter = new HistoryAdapter(this, daysHistoryList);
                listView.setAdapter(adapter);
            });
        }).start();

        listView.setOnItemClickListener((parent, view, position, id) -> {
//                Toast.makeText(getApplicationContext(), adapter.getItem(position).toString(),
//                        Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MapWayActivity.class);

            intent.putExtra("DATE", adapter.getItem(position));
            startActivity(intent);
        });
    }

    private List<DaysHistory> getHistory() throws JSONException, IOException {
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        Response response = sendData.getDaysHistory(String.valueOf(sPref.getInt("id", -1)));
        Type userListType = new TypeToken<ArrayList<DaysHistory>>(){}.getType();
        JSONArray jsonarray = new JSONArray(response.body().string());
        return new Gson().fromJson(jsonarray.toString(), userListType);
    }

}