package com.prim.plug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pluginstand.PluginInterfaceBroadcast;
import com.prim.plug.plugin.PluginManager;

import java.lang.reflect.Constructor;

/**
 * @author prim
 * @version 1.0.0
 * @desc 这是真正注册的广播
 * @time 2018/12/20 - 9:42 PM
 */
public class ProxyBroadcast extends BroadcastReceiver {
    private String className;

    private PluginInterfaceBroadcast bordcast;

    private static final String TAG = "ProxyBroadcast";

    public ProxyBroadcast(String name, Context context) {
        this.className = name;
        Class loadClass = null;
        try {
            loadClass = PluginManager.getInstance().getClassLoader().loadClass(className);
            Constructor constructor = loadClass.getConstructor(new Class[]{});
            bordcast = (PluginInterfaceBroadcast) constructor.newInstance(new Object[]{});
            bordcast.attach(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //class --- object --- p
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "ProxyBroadcast onReceive: ");
        if (bordcast != null) {
            bordcast.onReceive(context, intent);
        }
    }
}
