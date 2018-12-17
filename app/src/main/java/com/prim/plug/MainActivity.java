package com.prim.plug;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void loadPlugin(View view) {
        loadDownPlugin();
        Intent intent = new Intent(this, ProxyActivity.class);
        //得到全类名
        intent.putExtra("className", PluginManager.getInstance().getPackageInfo().activities[0].name);
        startActivity(intent);
    }

    /**
     * 模拟加载下载的插件(apk)
     */
    private void loadDownPlugin() {
        File pluginDir = this.getDir("plugin", MODE_PRIVATE);
        String pluginName = "pluginA.apk";
        String absolutePath = new File(pluginDir, pluginName).getAbsolutePath();
        File file = new File(absolutePath);
        if (file.exists()) {
            file.delete();
        }
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            //将外置卡的apk
            inputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory(), pluginName));
            //放到私有目录下
            outputStream = new FileOutputStream(absolutePath);
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            File file1 = new File(absolutePath);
            if (file1.exists()) {
                Toast.makeText(this, "dex over wirte", Toast.LENGTH_SHORT).show();
            }
            PluginManager.getInstance().loadPath(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
