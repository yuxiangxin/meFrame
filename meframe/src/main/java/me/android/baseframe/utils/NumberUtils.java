package me.android.baseframe.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * Created by yuxiangxin on 2020/08/23
 * 描述:
 */
public class NumberUtils {


    /**
     * 解析int类型的数据
     */
    public static int valueOf (String value, int defaultValue) {
        if (!TextUtils.isEmpty(value)) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * 解析Long类型的数据
     */
    public static long valueOf (String value, long defaultValue) {
        if (!TextUtils.isEmpty(value)) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * 解析double类型的数据
     */
    public static double valueOf (String value, double defaultValue) {
        if (!TextUtils.isEmpty(value)) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * 解析double类型的数据
     */
    public static float valueOf (String value, float defaultValue) {
        if (!TextUtils.isEmpty(value)) {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * float保留xx位小数
     */
    public static float scaleValue (float value, int scale) {
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double) value);
        bd = bd.setScale(scale, roundingMode);
        return bd.floatValue();
    }

    /**
     * 格式化角标数 max 99+
     */
    public static String formatNewMsgCount (int value, String zeroValue) {
        if (value == 0)
            return zeroValue;
        if (value > 99)
            return 99 + "+";
        return value + "";
    }

    /**
     * 格式化角标数 max 99
     */
    public static String formatNewMsgCountNoMore (int value, String zeroValue) {
        if (value == 0)
            return zeroValue;
        if (value > 99)
            return 99 + "";
        return value + "";
    }

    public static String formatNumCount (int value, int maxValue, String zeroValue) {
        if (value == 0)
            return zeroValue;
        if (value > maxValue)
            return maxValue + "+";
        return value + "";
    }

    /**
     * 格式化空间站人数
     */
    public static String formatSpacePeopleCount (int num, String zeroValue) {
        if (num > 10000) {
            return scaleValue((num / 10000F), 1) + "万";
        }
        if (num == 0) {
            return zeroValue;
        }
        return num + "";
    }
}
