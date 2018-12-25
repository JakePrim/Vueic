package skin.prim.com.plugina;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.pluginstand.BaseService;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/17 - 6:22 PM
 */
public class OneService extends BaseService {
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    private static final String TAG = "OneService";

    private int i = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Log.i(TAG, "run: " + (i++));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
