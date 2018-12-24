package com.pluginstand;

import android.content.Context;
import android.content.Intent;

/**
 * @author prim
 * @version 1.0.0
 * @desc 定义广播插件的标准
 * @time 2018/12/20 - 9:35 PM
 */
public interface PluginInterfaceBroadcast {
    void attach(Context context);

    void onReceive(Context context, Intent intent);
}
