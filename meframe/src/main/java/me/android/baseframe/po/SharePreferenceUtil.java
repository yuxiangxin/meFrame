package me.android.baseframe.po;

import android.content.Context;
import android.content.SharedPreferences;

import me.android.baseframe.base.BaseFrame;


/**
 * Created by yuxiangxin on 2020/07/15
 * 描述:SharePreference持久化Util
 */
public class SharePreferenceUtil {

    private final static String SP_FILE = "appConfig";


    public static SharedPreferences getEnvPreferences (Context context) {
        if (context == null)
            context = BaseFrame.getContext();
        return context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    }

    public static void putString (Context context, String title, String value) {
        getEnvPreferences(context).edit().putString(title, value).apply();
    }

    public static String getString (Context context, String title, String def) {
        return getEnvPreferences(context).getString(title, def);
    }

    public static void putInt (Context context, String title, int value) {
        getEnvPreferences(context).edit().putInt(title, value).apply();
    }

    public static int getInt (Context context, String title, int def) {
        return getEnvPreferences(context).getInt(title, def);
    }

    public static void putBoolean (Context context, String title, boolean value) {
        getEnvPreferences(context).edit().putBoolean(title, value).apply();
    }

    public static boolean getBoolean (Context context, String title, boolean defValue) {
        return getEnvPreferences(context).getBoolean(title, defValue);
    }

    public static void putLong (Context context, String title, long value) {
        getEnvPreferences(context).edit().putLong(title, value).apply();
    }

    public static long getLong (Context context, String title, long defValue) {
        return getEnvPreferences(context).getLong(title, defValue);
    }


}
