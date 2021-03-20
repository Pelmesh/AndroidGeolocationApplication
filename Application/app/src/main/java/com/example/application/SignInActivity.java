package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.childrenActivity.ChildActivity;
import com.example.application.parentActivity.ParentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SignInActivity extends AppCompatActivity {
    private final SendData sendData = new SendData();
    private EditText email;
    private EditText password;
    private TextView errorText;
    private RadioButton parentRadio;
    private RadioButton childRadio;
    private final String ERROR_FIELD_NULL = "Введите данные";
    private final String ERROR_400 = "Неверные данные";
    boolean isSignIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        errorText = (TextView) findViewById(R.id.errorText);
        parentRadio = (RadioButton) findViewById(R.id.parent);
        childRadio = (RadioButton) findViewById(R.id.child);
    }

    public void login(View view) throws JSONException {
        errorText.setText("");
        if (email.getText().toString().equals("") ||
                password.getText().toString().equals("")) {
            errorText.setText(ERROR_FIELD_NULL);
            return;
        }
        JSONObject json = new JSONObject();
        json.put("email", email.getText());
        json.put("password", password.getText());
        new Thread(() -> {
            try {
                isSignIn = sendData.isLogin(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                if (isSignIn) {
                    if (parentRadio.isChecked()) {
                        Intent intent = new Intent(getApplicationContext(), ParentActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ChildActivity.class);
                        startActivity(intent);
                    }
                } else {
                    errorText.setText(ERROR_400);
                }
            });
        }).start();
    }

}