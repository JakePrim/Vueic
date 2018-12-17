package uicc.prim.com.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pluginstand.PluginInterfaceActivity;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/25 - 下午10:41
 */
public class BaseActivity extends Activity implements PluginInterfaceActivity {

    private Activity thisActivity;//主app 提供的Activity

    @Override
    public void attach(Activity activity) {
        thisActivity = activity;
    }

    //凡是用到上下文的方法 都需要重写

    @Override
    public void setContentView(int layoutResID) {
        if (thisActivity != null) {//作为插件运行的
            thisActivity.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(View view) {
        if (thisActivity != null) {//作为插件运行的
            thisActivity.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (thisActivity != null){
            return thisActivity.findViewById(id);
        }
        return super.findViewById(id);
    }

    @Override
    public Intent getIntent() {
        if (thisActivity != null){
            return thisActivity.getIntent();
        }
        return super.getIntent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
