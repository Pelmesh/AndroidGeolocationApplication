package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity{
    private TextView email;
    private TextView password;
    private TextView password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_up);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        password2 = (TextView) findViewById(R.id.password2);
    }

    public void registration(View view) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        foo();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e);
                    }
                }
            }).start();
    }



    public void foo() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("latitudes", 1.226);
        json.put("longitudes", 1.227);
        JSONObject jsonObjectResp = null;

        try {

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, json.toString());
            Request request = new okhttp3.Request.Builder()
                    .url("http://10.0.2.2:8080/api")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String networkResp = response.body().string();
            if (!networkResp.isEmpty()) {
                System.out.println(networkResp);
            }
        } catch (Exception ex) {
            String err = String.format("{\"result\":\"false\",\"error\":\"%s\"}", ex.getMessage());
           // jsonObjectResp = parseJSONStringToJSONObject(err);
        }

//        //String url = "http://localhost:8080/api";
//        String url = "http://10.0.2.2:8080/api";
//        //String url = "https://www.nbrb.by/api/exrates/rates/" + "USD" + "?parammode=2";
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                System.out.println(response.body().string());
//            }
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                System.out.println(e);
//            }
//
//        });


    }
}