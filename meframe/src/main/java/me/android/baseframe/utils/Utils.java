package me.android.baseframe.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.KeyEvent;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuxiangxin on 20/8/23
 * ME Utils
 */

public class Utils {

    private static final String TAG = "Utils";

    public static final int _1KB = 1024;
    public static final int _1MB = _1KB * _1KB;

    public static boolean isEmpty (Collection datas) {
        return datas == null || datas.isEmpty();
    }

    //8b245aea-c13d-4510-b098-4e6ca22736b7 38dce4e0-2b18-4ef7-b47b-1120393de78a
    private static boolean isMeDev = false;

    public static void setIsMeDev (boolean isMeDev) {
        Utils.isMeDev = isMeDev;
    }

    public static boolean isMe () {
        return isMeDev;
    }


    public static String toString (List<String> list, String append) {
        if (Utils.isEmpty(list))
            return null;
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(item).append(append);
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public static boolean isIn (String id, String... in) {
        if (TextUtils.isEmpty(id) || in == null || in.length == 0)
            return false;
        for (int i = 0; i < in.length; i++) {
            if (id.equals(in[i]))
                return true;
        }
        return false;
    }

    public static boolean isIn (Object id, Object... in) {
        if (id == null || in == null || in.length == 0)
            return false;
        for (int i = 0; i < in.length; i++) {
            if (id == in[i])
                return true;
        }
        return false;
    }

    public static boolean isInInt (int id, int... in) {
        if (in == null || in.length == 0)
            return false;
        for (int i = 0; i < in.length; i++) {
            if (id == in[i])
                return true;
        }
        return false;
    }

    public static String matcherStr (String text, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        if (m.find())
            return m.group();
        return null;
    }


    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO (Closeable... closeables) {
        if (closeables == null)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void recycleBitmap (Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
            System.gc();
        }
    }

    public static boolean isEmpty (String... values) {
        if (values == null || values.length == 0)
            return true;
        if (values.length == 1)
            return TextUtils.isEmpty(values[0]);

        for (String value : values) {
            if (TextUtils.isEmpty(value))
                return true;
        }
        return false;
    }

    public static boolean isEmpty (Object... values) {
        if (values == null || values.length == 0)
            return true;
        if (values.length == 1)
            return values[0] == null;

        for (Object value : values) {
            if (value == null)
                return true;
        }
        return false;
    }

    public static <T> T getNotNullValue (T... value) {
        if (value == null || value.length == 0)
            return null;
        for (T t : value) {
            if (t != null) {
                return t;
            }
        }
        return null;
    }


    public static String toTrim (String value) {
        return toString(value).trim();
    }

    public static String toString (String value) {
        return TextUtils.isEmpty(value) ? "" : value;
    }

    public static long getIdFromStr (String value) {
        if (TextUtils.isEmpty(value)) {
            return 0;
        }
        char[] chars = value.toCharArray();
        long total = 0;
        for (char ch : chars) {
            total += ch;
        }
        return total;
    }


    public static String returnNotEmptyValue (String... values) {
        if (values != null) {
            for (String value : values) {
                if (!TextUtils.isEmpty(value)) {
                    return value;
                }
            }
        }
        return null;
    }


    public static <T> T returnNotEmptyValue (T... values) {
        if (values != null) {
            for (T value : values) {
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }


    public static int formatKeyCode1_9 (int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
                return 1;
            case KeyEvent.KEYCODE_2:
                return 2;
            case KeyEvent.KEYCODE_3:
                return 3;
            case KeyEvent.KEYCODE_4:
                return 4;
            case KeyEvent.KEYCODE_5:
                return 5;
            case KeyEvent.KEYCODE_6:
                return 6;
            case KeyEvent.KEYCODE_7:
                return 7;
            case KeyEvent.KEYCODE_8:
                return 8;
            case KeyEvent.KEYCODE_9:
                return 9;
            default:
                return -1;
        }
    }

}
