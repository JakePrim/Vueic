package skin.prim.com.plugina;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pluginstand.PluginInterfaceService;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/17 - 5:27 PM
 */
public class BaseService extends Service implements PluginInterfaceService {

    private Service that;

    private static final String TAG = "BaseService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void attach(Service proxyService) {
        this.that = proxyService;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e(TAG, "onConfigurationChanged: ");
    }

    @Override
    public void onLowMemory() {

        Log.e(TAG, "onLowMemory: ");
    }

    @Override
    public void onTrimMemory(int level) {
        Log.e(TAG, "onTrimMemory: ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind: ");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e(TAG, "onTaskRemoved: ");
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");

    }
}
