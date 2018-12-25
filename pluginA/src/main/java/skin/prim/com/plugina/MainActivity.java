package skin.prim.com.plugina;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pluginstand.BaseActivity;

public class MainActivity extends BaseActivity {

    private TextView pluginA_tv;

    private Button pluginA_btn, pluginA_btn1, pluginA_btn2, pluginA_btn3;

    private MyReceive myReceive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pluginA_tv = findViewById(R.id.pluginA_tv);
        String test = savedInstanceState.getString("test");
        pluginA_tv.setText("接收到宿主传递的数据:" + test);
        pluginA_btn = findViewById(R.id.pluginA_btn);
        pluginA_btn1 = findViewById(R.id.pluginA_btn1);
        pluginA_btn2 = findViewById(R.id.pluginA_btn2);
        pluginA_btn3 = findViewById(R.id.pluginA_btn3);
        pluginA_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myReceive = new MyReceive();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("skin.prim.com.plugina.MyReceive");
                registerReceiver(myReceive, intentFilter);
            }
        });
        pluginA_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("skin.prim.com.plugina.MyReceive");
                sendBroadcast(intent);
            }
        });
        pluginA_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "我是toast", Toast.LENGTH_SHORT).show();

                //需要重写startActivity
                startActivity(new Intent(mActivity, Main2Activity.class));
            }
        });

        pluginA_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(mActivity, OneService.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销广播
        if (myReceive != null) {
            unregisterReceiver(myReceive);
        }
    }
}
