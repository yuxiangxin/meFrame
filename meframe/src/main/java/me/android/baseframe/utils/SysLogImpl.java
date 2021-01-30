package me.android.baseframe.utils;

import android.util.Log;

/**
 * Created by yuxiangxin on 2020/10/20
 * 描述: android LogImpl
 */
class SysLogImpl implements ILog {

    @Override
    public void v (String tag, String msg) {
        Log.v(addFilterTag(tag), msg);
    }

    @Override
    public void v (String tag, String msg, Object... args) {
        if (args.length > 0) {
            try {
                msg = String.format(msg, args);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Log.v(addFilterTag(tag), msg);
    }

    @Override
    public void d (String tag, String msg) {
        Log.d(addFilterTag(tag), msg);
    }

    @Override
    public void i (String tag, String msg) {
        Log.i(addFilterTag(tag), msg);
    }

    @Override
    public void w (String tag, String msg) {
        Log.w(addFilterTag(tag), msg);
    }

    @Override
    public void e (String tag, String msg) {
        Log.e(addFilterTag(tag), msg);
    }


    @Override
    public void e (String tag, Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void e (String tag, String msg, Exception e) {
        e(tag, msg);
    }

    @Override
    public void e (String tag, String msg, Throwable e) {
        e(tag, msg);
    }

    private static String addFilterTag (String tag) {
        return "FG:" + tag;
    }
}
