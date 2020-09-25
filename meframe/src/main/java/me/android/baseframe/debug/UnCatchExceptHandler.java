package me.android.baseframe.debug;


import me.android.baseframe.base.BaseFrame;
import me.android.baseframe.po.SharePreferenceUtil;
import me.android.baseframe.utils.LogUtils;
import me.android.baseframe.utils.TimeUtil;

/**
 * Created by yuxiangxin on 2020/08/23
 * 描述:未处理异常记录
 */
public class UnCatchExceptHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "UnCatchExceptHandler";
    private static final String _N_R = "\r\n";
    private static final String _T = "\t";

    @Override
    public void uncaughtException (Thread thread, Throwable ex) {
        ex.printStackTrace();
        StringBuilder crashSb = new StringBuilder();
        crashSb.append("Date:").append(TimeUtil.formatTime(TimeUtil.FORMAT_DETAIL, System.currentTimeMillis())).append(_N_R);
        crashSb.append("Thread:").append(thread.getName()).append(_N_R)
                .append("Exception:").append(ex.getClass().getCanonicalName()).append(_N_R)
                .append("Message:").append(ex.getMessage()).append(_N_R)
                .append("stackTraceInfo:").append(_N_R);
        StackTraceElement[] stackTrace = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            crashSb.append(stackTraceElement).append(_N_R);
        }
        Throwable cause = ex.getCause();
        if (cause != null) {
            crashSb.append("Caused by: ").append(_N_R);
            crashSb.append(_T).append(ex.getClass().getCanonicalName()).append(ex.getMessage()).append(_N_R);
            StackTraceElement[] causeStackTrace = cause.getStackTrace();
            for (StackTraceElement stackTraceElement : causeStackTrace) {
                crashSb.append(_T).append(stackTraceElement).append(_N_R);
            }
        }
        SharePreferenceUtil.putString(BaseFrame.getContext(), "UnCatchExceptHandler", crashSb.toString());

        //System.exit(-1);
    }

    public static void printLastUnCatchExcept () {
        String exceptString = SharePreferenceUtil.getString(BaseFrame.getContext(), "UnCatchExceptHandler", null);
        if (exceptString != null) {
            LogUtils.e(TAG, "最近未捕获异常:\n" + exceptString);
        }
    }

}
