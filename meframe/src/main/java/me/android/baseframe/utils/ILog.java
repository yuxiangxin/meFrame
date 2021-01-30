package me.android.baseframe.utils;

/**
 * Created by yuxiangxin on 2020/10/20
 * 描述: LOG interface
 */
public interface ILog {
    void v (String tag, String msg);

    void v (String tag, String msg, Object... args);

    void d (String tag, String msg);

    void i (String tag, String msg);

    void w (String tag, String msg);

    void e (String tag, String msg);

    void e (String tag, Throwable e);

    void e (String tag, String msg, Exception e);

    void e (String tag, String msg, Throwable e);
}
