package com.prim.plug;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/17 - 5:32 PM
 */
public class ProxyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
