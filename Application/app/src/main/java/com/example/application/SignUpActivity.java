package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    private final SendData sendData = new SendData();
    private final String ERROR_PASS = "Пароли не совпадают";
    private final String ERROR_FIELD_NULL = "Введите данные";
    private final String ERROR_400 = "Такой пользователь уже существует";
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText password2;
    private TextView errorText;
    boolean isSignUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        errorText = (TextView) findViewById(R.id.errorText);
    }

    public void registration(View view) throws JSONException {
        errorText.setText("");
        if (username.getText().toString().equals("") ||
                email.getText().toString().equals("") ||
                password.getText().toString().equals("")) {
            errorText.setText(ERROR_FIELD_NULL);
            return;
        }
        if (!password.getText().toString().equals(password2.getText().toString())) {
            errorText.setText(ERROR_PASS);
            return;
        }
        JSONObject json = new JSONObject();
        json.put("username", username.getText());
        json.put("email", email.getText());
        json.put("password", password.getText());
        new Thread(() -> {
            try {
                isSignUp = sendData.isRegistration(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                if (isSignUp) {
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                } else {
                    errorText.setText(ERROR_400);
                }
            });
        }).start();
    }

}