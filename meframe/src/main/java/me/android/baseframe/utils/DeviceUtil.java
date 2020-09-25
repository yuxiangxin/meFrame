package me.android.baseframe.utils;

import android.os.Build;

/**
 * Created by yuxiangxin on 2020/07/03
 * 描述: 获取设备信息,设置设备..
 */
public class DeviceUtil {
    public static String getDevicesModel () {
        return Build.MODEL;
    }

    public static String getDevicesBrand () {
        return Build.BRAND;
    }

    public static boolean isQ () {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    public static int getSDKVersion () {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isN () {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}

