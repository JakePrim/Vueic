package com.prim.plug;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pluginstand.PluginInterfaceActivity;
import com.prim.plug.plugin.PluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 插桩 中间跳转 需要在manifest中注册
 * @time 2018/7/25 - 下午11:03
 */
public class ProxyActivity extends AppCompatActivity {

    //需要加载插件的全类名
    private String className;

    private PluginInterfaceActivity pluginInterfaceActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra("className");
//        Class.forName()
        //插件并没有被安装到手机上的
        try {
            //className 代表activity的全类名
            Class activityClass = getClassLoader().loadClass(className);
            //调用构造函数
            Constructor constructors = activityClass.getConstructor(new Class[]{});
            //得到Activity对象
            Object newInstance = constructors.newInstance(new Object[]{});
            //最好不要反射 onCreate()
            //通过标准来
            pluginInterfaceActivity = (PluginInterfaceActivity) newInstance;
            //注入上下文
            pluginInterfaceActivity.attach(this);
            Bundle bundle = new Bundle();//将一些信息传递
            bundle.putString("test", "test --> ");
            pluginInterfaceActivity.onCreate(bundle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("className", className);
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        String serviceName = service.getStringExtra("serviceName");
        Intent intent = new Intent(this, ProxyService.class);
        intent.putExtra("serviceName", serviceName);
        return super.startService(intent);
    }

    private List<ProxyBroadcast> proxyBroadcastList = new ArrayList<>();

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        //重写真正注册的是ProxyBroadcast 转发
        IntentFilter filter1 = new IntentFilter();
        for (int i = 0; i < filter.countActions(); i++) {
            filter1.addAction(filter.getAction(i));
            Log.e(TAG, "sendBroadcast: 注册插件的广播 -> " + filter1.getAction(i));
        }
        ProxyBroadcast proxyBroadcast = new ProxyBroadcast(receiver.getClass().getName(), this);
        proxyBroadcastList.add(proxyBroadcast);
        return super.registerReceiver(proxyBroadcast, filter1);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        if (proxyBroadcastList != null && proxyBroadcastList.size() > 0) {
            for (ProxyBroadcast proxyBroadcast : proxyBroadcastList) {
                if (proxyBroadcast.getClass().getName().equals(receiver.getClass().getName())) {
                    super.unregisterReceiver(proxyBroadcast);
                }
            }
        } else {
            super.unregisterReceiver(receiver);
        }
    }

    private static final String TAG = "ProxyActivity";

    @Override
    public void sendBroadcast(Intent intent) {
        Log.e(TAG, "sendBroadcast: 收到插件发送的广播 -> " + intent.getAction());
        super.sendBroadcast(intent);
    }

    //对外
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pluginInterfaceActivity.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pluginInterfaceActivity.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pluginInterfaceActivity.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pluginInterfaceActivity.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pluginInterfaceActivity.onDestroy();
    }
}
