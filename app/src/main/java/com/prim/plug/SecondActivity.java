package com.prim.plug;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.prim.plug.plugin.HookUtils;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv = findViewById(R.id.tv);
        tv.setText("集中式登录");
    }

    public void initHook(View view) {
        //初始化
        HookUtils.getInstance().init(getApplicationContext());
    }

    public void removeHook(View view) {
    }

    public void comment(View view) {
        Intent intent = new Intent(this, CommentActivity.class);
        startActivity(intent);
    }

    public void collent(View view) {
        Intent intent = new Intent(this, CollentActivity.class);
        startActivity(intent);
    }

    public void like(View view) {

    }

    public void exit(View view) {
        SharedPreferences login = getSharedPreferences("login", Context.MODE_PRIVATE);
        login.edit().putBoolean("isLogin", false).apply();
    }
}
