package me.android.baseframe.utils;

import android.util.Log;

/**
 * Created by yuxiangxin on 2020/09/2
 * 描述: LOG print
 */
public class LogUtils {

    private static boolean DEBUG = true;
    private static boolean IS_SUPERVISE = false;// "nubia".equals(DeviceUtil.getDevicesBrand());

    public static void setEnable (boolean enable) {
        DEBUG = enable;
    }

    public static void setIsSupervise (boolean isSupervise) {
        IS_SUPERVISE = isSupervise;
    }

    public static void v (String tag, String msg) {
        if (DEBUG) {
            //改数据error
            if (IS_SUPERVISE) {
                Log.e(addFilterTag(tag), msg);
            } else {
                Log.v(addFilterTag(tag), msg);
            }
        }
    }

    public static void v (String tag, String msg, Object... args) {
        if (!DEBUG) {
            return;
        }
        if (args.length > 0) {
            try {
                msg = String.format(msg, args);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        //改数据error
        if (IS_SUPERVISE) {
            Log.e(addFilterTag(tag), msg);
        } else {
            Log.v(addFilterTag(tag), msg);
        }
    }

    public static void d (String tag, String msg) {
        if (DEBUG) {
            Log.d(addFilterTag(tag), msg);
        }
    }

    public static void i (String tag, String msg) {
        if (DEBUG) {
            Log.i(addFilterTag(tag), msg);
        }
    }

    public static void w (String tag, String msg) {
        if (DEBUG) {
            Log.w(addFilterTag(tag), msg);
        }
    }

    public static void e (String tag, String msg) {
        if (DEBUG) {
            Log.e(addFilterTag(tag), msg);
        }
    }

    public static void e (Throwable e) {
        e("LogUtils:Exception", e.getMessage());
    }

    public static void e (String tag, Throwable e) {
        if (DEBUG) {
            e.printStackTrace();
        }
    }

    public static void e (String tag, String msg, Exception e) {
        e(tag, msg);
        if (DEBUG) {
            e.printStackTrace();
        }
    }

    public static void e (String tag, String msg, Throwable e) {
        e(tag, msg);
        if (DEBUG) {
            e.printStackTrace();
        }
    }

    private static String addFilterTag (String tag) {
        return "FG:" + tag;
    }
}