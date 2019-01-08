package com.prim.plug;

import android.app.Application;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/7 - 11:03 PM
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HookUtils hookUtils = new HookUtils();
        hookUtils.hookStartActivity(this);
    }
}
