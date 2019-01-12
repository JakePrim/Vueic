package com.prim.plug;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//不用在Manifest中声明
public class LoginActivity extends AppCompatActivity {

    private EditText et_num, et_pass;

    private String extraIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_num = findViewById(R.id.et_num);
        et_pass = findViewById(R.id.et_pass);
        extraIntent = getIntent().getStringExtra("extraIntent");
    }

    public void login(View view) {
        SharedPreferences login = getSharedPreferences("login", Context.MODE_PRIVATE);
        login.edit().putBoolean("isLogin", true).apply();
        try {
            Class<?> aClass = getClassLoader().loadClass(extraIntent);
            Intent intent = new Intent(this, aClass);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
