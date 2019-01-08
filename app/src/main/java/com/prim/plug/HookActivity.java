package com.prim.plug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/7 - 11:14 PM
 */
public class HookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv = findViewById(R.id.tv);
        tv.setText(" This is HookActivity!");
    }
}
