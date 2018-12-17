package skin.prim.com.plugina;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.pluginstand.PluginInterfaceService;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/17 - 5:27 PM
 */
public class BaseService extends Service implements PluginInterfaceService {

    private Service that;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void attach(Service proxyService) {
      this.that = proxyService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
