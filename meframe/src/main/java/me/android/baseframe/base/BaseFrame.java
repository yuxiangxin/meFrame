package me.android.baseframe.base;


import android.content.Context;

import me.android.baseframe.utils.LogUtils;

/**
 * Created by yuxiangxin on 2020/09/2
 * 描述: frame 基础, 启动前需初始化
 */
public class BaseFrame {

    private static Context sContext;
    private static BuildFrameConfig sConfig;

    public static void init (Context app, BuildFrameConfig config) {
        sContext = app;
        sConfig = config;
    }

    public static Context getContext () {
        return sContext;
    }

    public static void enableLog (boolean enable) {
        LogUtils.setEnable(enable);
    }


    public static String getApplicationId () {
        return sConfig.getApplicationId();
    }

    public static boolean isDebug () {
        return sConfig.isDebug();
    }

    public static int getVersionCode () {
        return sConfig.getVersionCode();
    }

    public static String getVersionName () {
        return sConfig.getVersionName();
    }

}
