package com.oldthank;

import com.nle.mylibrary.forUse.mdbus4150.Modbus4150;
import com.nle.mylibrary.forUse.rfid.RFID;
import com.nle.mylibrary.forUse.zigbee.Zigbee;

public class SettingUtils {

    private static Modbus4150 modbus4150;
    private static Zigbee zigbee;
    private static RFID rfid;

    private static Integer putterOpen,PutterClose;

    private static Integer zigbeeLamplight,zigbeeFen;


    public SettingUtils() {
    }

    public static Modbus4150 getModbus4150() {
        return modbus4150;
    }

    public static void setModbus4150(Modbus4150 modbus4150) {
        SettingUtils.modbus4150 = modbus4150;
    }

    public static Zigbee getZigbee() {
        return zigbee;
    }

    public static void setZigbee(Zigbee zigbee) {
        SettingUtils.zigbee = zigbee;
    }

    public static Integer getPutterOpen() {
        return putterOpen;
    }

    public static void setPutterOpen(Integer putterOpen) {
        SettingUtils.putterOpen = putterOpen;
    }

    public static Integer getPutterClose() {
        return PutterClose;
    }

    public static void setPutterClose(Integer putterClose) {
        PutterClose = putterClose;
    }

    public static Integer getZigbeeLamplight() {
        return zigbeeLamplight;
    }

    public static void setZigbeeLamplight(Integer zigbeeLamplight) {
        SettingUtils.zigbeeLamplight = zigbeeLamplight;
    }

    public static Integer getZigbeeFen() {
        return zigbeeFen;
    }

    public static void setZigbeeFen(Integer zigbeeFen) {
        SettingUtils.zigbeeFen = zigbeeFen;
    }

    public static RFID getRfid() {
        return rfid;
    }

    public static void setRfid(RFID rfid) {
        SettingUtils.rfid = rfid;
    }
}
