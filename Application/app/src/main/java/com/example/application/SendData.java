package com.example.application;

import com.example.application.util.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendData {
    private final UserUtil userUtil = new UserUtil();
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String URL = "http://10.0.2.2:8080/api/locations";
    private static final String URL_HISTORY = "http://10.0.2.2:8080/api/locations/history";
    private static final String URL_REG = "http://10.0.2.2:8080/api/user/registration";
    private static final String URL_LOG = "http://10.0.2.2:8080/api/user/login";
    private static final String URL_POINTS = "http://10.0.2.2:8080/api/point/user";
    private static final String URL_SAVE_POINT = "http://10.0.2.2:8080/api/point";

    public void sendLocation(JSONObject json) throws IOException, JSONException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new okhttp3.Request.Builder()
                .url(URL)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
    }

    public boolean isRegistration(JSONObject json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new okhttp3.Request.Builder()
                .url(URL_REG)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.code() == 200;
    }

    public Response isLogin(JSONObject json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new okhttp3.Request.Builder()
                .url(URL_LOG)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public Response getDaysHistory(String id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new okhttp3.Request.Builder()
                .url(URL_HISTORY+"/"+id)
                .build();
        return client.newCall(request).execute();
    }

    public Response getDayHistory(String id, String date) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new okhttp3.Request.Builder()
                .url(URL_HISTORY+"/"+id+"/"+date)
                .build();
        return client.newCall(request).execute();
    }

    public Response getPoints(String id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new okhttp3.Request.Builder()
                .url(URL_POINTS+"/"+id)
                .build();
        return client.newCall(request).execute();
    }

    public Response savePoint(JSONObject json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new okhttp3.Request.Builder()
                .url(URL_SAVE_POINT)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

}
