package com.example.application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.childrenActivity.ChildActivity;
import com.example.application.parentActivity.ParentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {
    private final SendData sendData = new SendData();
    private Button loginButton;
    private EditText email;
    private EditText password;
    private TextView errorText;
    private RadioButton parentRadio;
    private RadioButton childRadio;
    private final String ERROR_FIELD_NULL = "Введите данные";
    private final String ERROR_400 = "Неверные данные";
    Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        errorText = (TextView) findViewById(R.id.errorText);
        parentRadio = (RadioButton) findViewById(R.id.parent);
        childRadio = (RadioButton) findViewById(R.id.child);
        loginButton = (Button) findViewById(R.id.login);
    }

    public void login(View view) throws JSONException {
        loginButton.setEnabled(false);
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
                response = sendData.isLogin(json);
            } catch (Exception e) {
                loginButton.setEnabled(true);
                return;
            }
            runOnUiThread(() -> {
                try {
                    if (response.code() == 200) {
                        if (parentRadio.isChecked() && saveUser("parent")) {
                            Intent intent = new Intent(getApplicationContext(), ParentActivity.class);
                            startActivity(intent);
                        } else if (childRadio.isChecked() && saveUser("child")) {
                            Intent intent = new Intent(getApplicationContext(), ChildActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        loginButton.setEnabled(true);
                        errorText.setText(ERROR_400);
                    }
                } catch (Exception e) {
                    loginButton.setEnabled(true);
                    errorText.setText(ERROR_400);
                }
                loginButton.setEnabled(true);
            });
        }).start();
    }

    private boolean saveUser(String role) {
        try {
            JSONObject jsonResponse = new JSONObject(response.body().string());
            SharedPreferences sPref;
            sPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("user", jsonResponse.getString("username"));
            ed.putString("id", jsonResponse.getString("id"));
            ed.putString("role", role);
            ed.commit();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}