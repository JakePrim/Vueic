package skin.prim.com.plugina;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.pluginstand.PluginInterfaceBroadcast;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/20 - 9:37 PM
 */
public class MyReceive extends BroadcastReceiver implements PluginInterfaceBroadcast {
    @Override
    public void attach(Context context) {
        Toast.makeText(context, "注册广播成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "接收广播成功", Toast.LENGTH_SHORT).show();

    }
}
