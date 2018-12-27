package com.pluginstand;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/17 - 3:12 PM
 */
public class BaseActivity extends AppCompatActivity implements PluginInterfaceActivity {

    protected Activity mActivity;

    @Override
    public void attach(Activity proxyActivity) {
        this.mActivity = proxyActivity;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    //--------------------------凡事用到上下文的地方都需要重写，需要重写的方法-----------------------------//

    /**
     * 这里我们要使用宿主传递过来的上下文进行加载view
     *
     * @param view
     */
    @Override
    public void setContentView(View view) {
        if (mActivity != null) {
            mActivity.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mActivity != null) {
            mActivity.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (mActivity != null) {
            return mActivity.findViewById(id);
        } else {
            return super.findViewById(id);
        }
    }

    @Override
    public Intent getIntent() {
        if (mActivity != null) {
            return mActivity.getIntent();
        }
        return super.getIntent();
    }

    @Override
    public ClassLoader getClassLoader() {
        if (mActivity != null) {
            return mActivity.getClassLoader();
        }
        return super.getClassLoader();
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        if (mActivity != null) {
            return mActivity.getLayoutInflater();
        }
        return super.getLayoutInflater();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if (mActivity != null) {
            return mActivity.getApplicationInfo();
        }
        return super.getApplicationInfo();
    }

    @Override
    public Window getWindow() {
        return mActivity.getWindow();
    }


    @Override
    public WindowManager getWindowManager() {
        return mActivity.getWindowManager();
    }

    @Override
    public void startActivity(Intent intent) {
        if (mActivity != null) {
            //插件的launchMode 只能够是标准的
            Intent intent1 = new Intent();
            intent1.putExtra("className", intent.getComponent().getClassName());
            mActivity.startActivity(intent1);
        } else {
            super.startActivity(intent);
        }
    }

    @Override
    public ComponentName startService(Intent service) {
        if (mActivity != null) {
            Intent m = new Intent();
            m.putExtra("serviceName", service.getComponent().getClassName());
            return mActivity.startService(m);
        } else {
            return super.startService(service);
        }
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (mActivity != null) {
            return mActivity.registerReceiver(receiver, filter);
        }
        return super.registerReceiver(receiver, filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        if (null != mActivity) {
            mActivity.sendBroadcast(intent);
        } else {
            super.sendBroadcast(intent);
        }
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        if (null != mActivity) {
            mActivity.unregisterReceiver(receiver);
        } else {
            super.unregisterReceiver(receiver);
        }
    }
}
