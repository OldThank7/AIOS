package com.oldthank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nle.mylibrary.forUse.mdbus4150.Modbus4150;
import com.nle.mylibrary.forUse.rfid.RFID;
import com.nle.mylibrary.forUse.zigbee.Zigbee;
import com.nle.mylibrary.transfer.DataBusFactory;

public class Setting extends AppCompatActivity {

    private EditText editIP,editModbusPort,editZigBeePort,editUHFport;

    private EditText editLampLight,editFen;

    private EditText putterClose,putterOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        editIP = findViewById(R.id.editIP);
        editModbusPort = findViewById(R.id.editPort);
        editZigBeePort = findViewById(R.id.editzigbeeport);
        editUHFport = findViewById(R.id.editUHFport);

        editLampLight = findViewById(R.id.edit_LampLight);
        editFen = findViewById(R.id.edit_Fen);

        putterClose = findViewById(R.id.editPutterClose);
        putterOpen = findViewById(R.id.editPutterOpen);
    }


    public void connection(View view) {

        SettingUtils.setPutterClose(EditToIntger(putterClose));
        SettingUtils.setPutterOpen(EditToIntger(putterOpen));

        SettingUtils.setZigbeeFen(EditToIntger(editFen));
        SettingUtils.setZigbeeLamplight(EditToIntger(editLampLight));

        String ip = editIP.getText().toString();
        Integer port = EditToIntger(editModbusPort);
        Integer zigbeeport = EditToIntger(editZigBeePort);
        Integer uhfport = EditToIntger(editUHFport);

        Modbus4150 modbus4150 = new Modbus4150(DataBusFactory.newSocketDataBus(ip, port));
        Zigbee zigbee = new Zigbee(DataBusFactory.newSocketDataBus(ip, zigbeeport));

        SettingUtils.setModbus4150(modbus4150);
        SettingUtils.setZigbee(zigbee);
        SettingUtils.setRfid(new RFID(DataBusFactory.newSocketDataBus(ip,uhfport)));
    }

    public void close(View view) {
        SettingUtils.getModbus4150().stopConnect();
        SettingUtils.getZigbee().stopConnect();
    }

    public Integer EditToIntger(EditText text){
        return Integer.valueOf(text.getText().toString());
    }
}