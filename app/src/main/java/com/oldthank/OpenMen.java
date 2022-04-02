package com.oldthank;

import com.nle.mylibrary.forUse.mdbus4150.Modbus4150;

public class OpenMen implements Runnable {

    private Modbus4150 modbus4150 = SettingUtils.getModbus4150();


    @Override
    public void run() {
        if (null != modbus4150) {
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
}
