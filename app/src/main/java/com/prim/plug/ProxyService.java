package com.prim.plug;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;

import com.pluginstand.PluginInterfaceService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.prim.plug.plugin.PluginManager;
import dalvik.system.DexClassLoader;

/**
 * @author prim
 * @version 1.0.0
 * @desc 插桩 service 需要在manifest中注册
 * @time 2018/12/17 - 5:32 PM
 */
public class ProxyService extends Service {
    private String serviceName;

    private PluginInterfaceService pluginInterfaceService;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void init(Intent intent) {
        //开启某个服务的全类名
        serviceName = intent.getStringExtra("serviceName");
        //生成一个class
        DexClassLoader classLoader = PluginManager.getInstance().getClassLoader();
        try {
            Class<?> aClass = classLoader.loadClass(serviceName);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            //获取某个service对象
            Object newInstance = constructor.newInstance(new Object[]{});
            pluginInterfaceService = (PluginInterfaceService) newInstance;
            pluginInterfaceService.attach(this);
            Bundle bundle = new Bundle();
            pluginInterfaceService.onCreate();
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (pluginInterfaceService == null) {
            init(intent);
        }
        return pluginInterfaceService.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        pluginInterfaceService.onStart(intent, startId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        pluginInterfaceService.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        pluginInterfaceService.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        pluginInterfaceService.onTrimMemory(level);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        pluginInterfaceService.onUnbind(intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        pluginInterfaceService.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        pluginInterfaceService.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pluginInterfaceService.onDestroy();
    }
}
