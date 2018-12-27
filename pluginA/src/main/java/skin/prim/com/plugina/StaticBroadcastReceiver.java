package skin.prim.com.plugina;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author prim
 * @version 1.0.0
 * @desc 插件静态广播
 * @time 2018/12/26 - 1:28 PM
 */
public class StaticBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION = "com.prim.plugin.host";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "我是插件，收到发送的广播,我将向宿主发送广播", Toast.LENGTH_SHORT).show();
        //接收到广播，然后给宿主发送广播
        context.sendBroadcast(new Intent(ACTION));
    }
}
