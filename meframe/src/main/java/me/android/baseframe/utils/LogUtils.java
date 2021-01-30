package me.android.baseframe.utils;


/**
 * Created by yuxiangxin on 2020/09/2
 * 描述: LOG print
 */
public class LogUtils {

    private static ILog SLogImpl = new SysLogImpl();

    public static void setSLogImpl (ILog SLogImpl) {
        LogUtils.SLogImpl = SLogImpl;
    }

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
                SLogImpl.e(tag, msg);
            } else {
                SLogImpl.v(tag, msg);
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
            SLogImpl.e(tag, msg);
        } else {
            SLogImpl.v(tag, msg);
        }
    }

    public static void d (String tag, String msg) {
        if (DEBUG) {
            SLogImpl.d(tag, msg);
        }
    }

    public static void i (String tag, String msg) {
        if (DEBUG) {
            SLogImpl.i(tag, msg);
        }
    }

    public static void w (String tag, String msg) {
        if (DEBUG) {
            SLogImpl.w(tag, msg);
        }
    }

    public static void e (String tag, String msg) {
        if (DEBUG) {
            SLogImpl.e(tag, msg);
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
        if (DEBUG) {
            SLogImpl.e(tag, msg);
            e.printStackTrace();
        }
    }

    public static void e (String tag, String msg, Throwable e) {
        if (DEBUG) {
            SLogImpl.e(tag, msg);
            e.printStackTrace();
        }
    }
}