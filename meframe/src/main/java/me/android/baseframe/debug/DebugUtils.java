package me.android.baseframe.debug;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.provider.Settings;
import android.view.KeyEvent;

import java.io.File;
import java.io.IOException;

import me.android.baseframe.base.BaseFrame;
import me.android.baseframe.net.HttpRespBean;
import me.android.baseframe.net.RequestBean;
import me.android.baseframe.utils.IoUtils;
import me.android.baseframe.utils.LogUtils;
import me.android.baseframe.utils.ToastUtil;
import me.android.baseframe.utils.Utils;

/**
 * Created by yuxiangxin on 2020/08/24
 * 描述: 调试测试Util
 */
public class DebugUtils {

    private static final String TAG = "DebugUtils";
    private static long mTracingDate = 0;

    public static void startDebugTracingDate () {
        mTracingDate = System.currentTimeMillis();
    }

    public static long getDebugTotalTracingDate () {
        long time = System.currentTimeMillis() - mTracingDate;
        mTracingDate = 0;
        return time;
    }

    public static void endDebugTotalTracingDate (String tag) {
        LogUtils.v(tag, "debugDate:" + getDebugTotalTracingDate());
    }

    public static HttpRespBean debugStart (RequestBean req) {
        if (Utils.isMe()) {

        }
        return null;
    }

    public static String formatKeyEvent (KeyEvent event) {
        String direction = null;
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (event.hasNoModifiers()) {
                    direction = "View.FOCUS_LEFT";
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (event.hasNoModifiers()) {
                    direction = "View.FOCUS_RIGHT";
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (event.hasNoModifiers()) {
                    direction = "View.FOCUS_UP";
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (event.hasNoModifiers()) {
                    direction = "View.FOCUS_DOWN";
                }
                break;
            default:
                direction = "UNKNOW";
        }
        return direction;
    }


    public static void pullDb () {
        File file = new File("/data/user/0/" + BaseFrame.getApplicationId());
        String base = BaseFrame.getContext().getExternalFilesDir(null).getAbsolutePath();
        File baseTarget = new File(base + "/Test");
        if (!baseTarget.exists()) {
            baseTarget.mkdirs();
        }
        File target = new File(baseTarget, "databases");
        if (target.exists()) {
            for (File item : target.listFiles()) {
                item.delete();
            }
            target.delete();
        }
        LogUtils.v(TAG, "数据库目录存在:" + file.exists());
        LogUtils.v(TAG, "保存目录:" + baseTarget.getAbsolutePath());
        try {

            IoUtils.copyDir(file, baseTarget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.v("Test", "" + "pullDb main");
    }

    /**
     * 打开开发者模式界面
     */
    public static void startDevelopment (Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.setAction("android.intent.action.View");
                activity.startActivity(intent);
            } catch (Exception e1) {
                try {
                    Intent intent = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");//部分小米手机采用这种方式跳转
                    activity.startActivity(intent);
                } catch (Exception e2) {
                    ToastUtil.refreshToast("跳转开发者页面失败");
                }
            }
        }
    }

}
