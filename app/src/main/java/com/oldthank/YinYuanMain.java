package com.oldthank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nle.mylibrary.forUse.zigbee.Zigbee;

import java.util.Timer;
import java.util.TimerTask;

public class YinYuanMain extends AppCompatActivity {

    Zigbee zigbee = null;
    private TextView editCo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_mai);

        editCo2 =findViewById(R.id.editCo2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SettingUtils.getZigbee() == null){
            Toast.makeText(this, "请先进行TCP连接串口服务器......", Toast.LENGTH_SHORT).show();
        }else{
            zigbee = SettingUtils.getZigbee();
        }

    }

    Timer timer = new Timer();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                double zigbeeCO = zigbee.getCO();
                editCo2.setText(String.valueOf(zigbeeCO));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        if (zigbee != null) {
            timer.schedule(timerTask,1000,500);
        }

    }

    boolean lamp = false;
    public void openLamp(View view) {
        try {
            zigbee.ctrlDoubleRelay(1,1,!lamp,(s)->{});
            lamp = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean fen= false;
    public void openFen(View view) {
        try {
            zigbee.ctrlDoubleRelay(1,2,!fen,(s)->{});
            fen = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}