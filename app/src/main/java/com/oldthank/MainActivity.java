package com.oldthank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nle.mylibrary.forUse.mdbus4150.Modbus4150;
import com.nle.mylibrary.forUse.rfid.RFID;
import com.nle.mylibrary.forUse.rfid.SingleEpcListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    private RFID rfid;
    private Modbus4150 modbus4150;

    private TextView editrfid;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editrfid = findViewById(R.id.editrfid);
        imageView = findViewById(R.id.imageView2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        if (SettingUtils.getZigbee() == null &&
            SettingUtils.getModbus4150() == null &&
            SettingUtils.getRfid() == null){
            Toast.makeText(this, "请先进行TCP连接串口服务器......", Toast.LENGTH_SHORT).show();
        }else {
            rfid = SettingUtils.getRfid();
            modbus4150 = SettingUtils.getModbus4150();
        }
        
    }

    Timer timer = null;
    @Override
    protected void onResume() {
        super.onResume();
        if (!(SettingUtils.getModbus4150() == null)){

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        rfid.readSingleEpc(new SingleEpcListener() {
                            @Override
                            public void onVal(String s) {
                                System.out.println("s = " + s);
                                if (s != "" && s != null) {
                                    runOnUiThread(()->{
                                        editrfid.setText(s);
                                        imageView.setImageResource(R.drawable.menopen);
                                    });
                                    openmen();
                                }
                                else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    editrfid.setText("");
                                                    imageView.setImageResource(R.drawable.menclose);
                                                }
                                            });
                                        }
                                    });
                                }
                            };
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 3000,1000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        modbus4150.getVal(4,(s)->{
                            if (1 == s) {
                                System.out.println("关掉所有继电器......");
                                try {
                                    modbus4150.closeRelay(SettingUtils.getPutterClose(), (s1) -> {
                                    });
                                    modbus4150.closeRelay(SettingUtils.getPutterOpen(), (s2) -> {
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },1000,899);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                startActivity(new Intent(this,Setting.class));
                break;
            case R.id.yinyaunmain:
                startActivity(new Intent(this,YinYuanMain.class));
                break;
        }
        return true;
    }

    public void openmen(){
        try {
            modbus4150.openRelay(SettingUtils.getPutterOpen(), (s) -> {
            });
            Thread.sleep(3000);
            modbus4150.closeRelay(SettingUtils.getPutterOpen(), (s) -> {
            });
            Thread.sleep(1000);
            modbus4150.openRelay(SettingUtils.getPutterClose(), (s) -> {
            });
            Thread.sleep(3000);
            modbus4150.closeRelay(SettingUtils.getPutterClose(), (s) -> {
            });
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}