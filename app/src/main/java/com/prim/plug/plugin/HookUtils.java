package com.prim.plug.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.prim.plug.CollentActivity;
import com.prim.plug.CommentActivity;
import com.prim.plug.HookActivity;
import com.prim.plug.LoginActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author prim
 * @version 1.0.0
 * @desc 基于Android API 28实现
 * @time 2019/1/7 - 10:23 PM
 */
public class HookUtils {
    private Context context;
    public static HookUtils hookUtils;


    public static HookUtils getInstance() {
        if (hookUtils == null) {
            synchronized (HookUtils.class) {
                if (hookUtils == null) {
                    hookUtils = new HookUtils();
                }
            }
        }
        return hookUtils;
    }

    public void init(Context context) {
        this.context = context;
        hookStartActivity();
        hookMh();
    }

    public void hookMh() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            //获取静态属性
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            //还原sCurrentActivityThread 静态属性
            Object currentActivityThreadObject = currentActivityThreadField.get(null);
            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            //找到hook点
            Handler mHObj = (Handler) mHField.get(currentActivityThreadObject);
            //通过接口 handleMessage Callback 对Callback 赋值
            Field mCallback = Handler.class.getDeclaredField("mCallback");
            mCallback.setAccessible(true);
            //设置callback
            mCallback.set(mHObj, new activityMH(mHObj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class activityMH implements Handler.Callback {

        private Handler mh;

        public activityMH(Handler mh) {
            this.mh = mh;
        }

        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG, "handleMessage: " + msg.what);
            switch (msg.what) {
                case 159://即将要加载一个Activity
                    //加工完一定要丢给系统
                    handleLunchActivity(msg);
            }
            //做了真正的跳转
            mh.handleMessage(msg);
            return true;
        }
    }

    private static final String TAG = "HookUtils";

    private void handleLunchActivity(Message msg) {
        //还原
        Object obj = msg.obj;
        try {
            Log.e(TAG, "handleLunchActivity: msg -> "+obj );
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            //真实对Intent --》HookActivity 真正对载入
            Intent realIntent = (Intent) intent.get(obj);
            //还原真实的Intent
            Intent oldIntent = realIntent.getParcelableExtra("realIntent");
            if (oldIntent != null) {
                //判断是否登录
                //如果登录了 跳转到原有的意图
                SharedPreferences login = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                boolean isLogin = login.getBoolean("isLogin", false);
                String className = oldIntent.getComponent().getClassName();
                Log.e(TAG, "--------------------handleLunchActivity----------------------");
                Log.e(TAG, "className:" + className + " isLogin:" + isLogin);
                if (!isLogin &&
                        (className.equals(CommentActivity.class.getName())
                                || className.equals(CollentActivity.class.getName()))) {//添加劫持白名单 需要登录的界面
                    //如果没有登录 跳转到登录界面
                    ComponentName componentName = new ComponentName(context, LoginActivity.class);
                    //登录成功后要跳转Activity
                    realIntent.putExtra("extraIntent", oldIntent.getComponent().getClassName());
                    realIntent.setComponent(componentName);
                } else {//已经登录成功就继续之前的
                    realIntent.setComponent(oldIntent.getComponent());
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void hookStartActivity() {
        //Android API 28
        //还原 getService IActivityManagerSingleton 反射
        try {
            Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
            //拿到成员变量
            Field activityManagerSingleton = activityManagerClass.getDeclaredField("IActivityManagerSingleton");
            activityManagerSingleton.setAccessible(true);
            //还原静态的变量
            Object getService = activityManagerSingleton.get(null);
            //拿到Singleton
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            //获取mInstance
            Field mInstance = singletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            //还原IActivityManager 真美猴王 这是真正的系统对象 hook 点
            Object iActivityManager = mInstance.get(getService);
            //两种方式 实现接口 动态代理
            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
            startActivity startActivityMethod = new startActivity(iActivityManager);
            //动态代理 假美猴王 代理IActivityManager 实现了iActivityManagerClass的接口
            //new Class[] 返回的对象实现了哪个接口
            Object proxyIActivityManager =
                    Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                            new Class[]{iActivityManagerClass},
                            startActivityMethod);
            //将假的美猴王设置进去 proxyIActivityManager 替换真美猴王
            //proxyIActivityManager 实现了IActivityManager接口的所有方法
            mInstance.set(getService, proxyIActivityManager);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    class startActivity implements InvocationHandler {
        Object iActivityManager;

        private static final String TAG = "startActivity";

        public startActivity(Object iActivityManager) {
            this.iActivityManager = iActivityManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e(TAG, "invoke: " + method.getName());
            //所有实现接口的方法都会调用 invoke 方法，接口方法Method args为参数
            if (method.getName().equals("startActivity")) {
                Log.e(TAG, "invoke: ----------------startActivity-------------");
                //瞒天过海
                Intent intent = null;
                Bundle options;
                int index = 0;
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {//寻找传进来的Intent
                        intent = (Intent) args[i];
                        index = i;
                    }
                }
                Intent newIntent = new Intent();
                //ComponentName 是什么？
                //public Intent(Context packageContext, Class<?> cls) {
                //        mComponent = new ComponentName(packageContext, cls);
                //    }
                ComponentName componentName = new ComponentName(context, HookActivity.class);
                newIntent.setComponent(componentName);
                //真实的Intent
                newIntent.putExtra("realIntent", intent);
                //重新复制替换的Intent 改变参数
                args[index] = newIntent;
            }
            return method.invoke(iActivityManager, args);
        }
    }
}
