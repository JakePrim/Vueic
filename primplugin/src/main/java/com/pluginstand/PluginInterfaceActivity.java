package com.pluginstand;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

/**
 * @author prim
 * @version 1.0.0
 * @desc 定义插件化标准 插件之间的桥梁
 * 通过传递上下文来启动activity
 * @time 2018/7/17 - 下午10:54
 */
public interface PluginInterfaceActivity {

    /**
     * 上下文的传递 通过上下文来启动Activity
     *
     * @param activity
     */
    void attach(Activity activity);

    //---------------------- 生命周期 传递到插件中 -------------------------//

    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onRestart();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    boolean onTouchEvent(MotionEvent event);

    void onBackPressed();
}
