package me.android.baseframe.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.List;

import me.android.baseframe.R;

/**
 * Created by yuxiangxin on 2020/08/22
 * 描述: assset,drawable...资源文件处理
 */
public class ResourcesUtil {

    public static <T> List<T> parseArray (Context context, String fileName, Class<T> clz) {
        String appsJson = IoUtils.readAssetsFileContent(context, fileName);
        return appsJson != null ? JSON.parseArray(appsJson, clz) : null;
    }

    public static <T> T parseData (Context context, String fileName, Class<T> clz) {
        String appsJson = IoUtils.readAssetsFileContent(context, fileName);
        return appsJson != null ? JSON.parseObject(appsJson, clz) : null;
    }

    public static int formatResourcesId (String fileName, int def) {
        if (TextUtils.isEmpty(fileName))
            return def;
        try {
            Field field = R.drawable.class.getField(fileName);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    private int formatStringId (String tvName, int def) {
        if (TextUtils.isEmpty(tvName))
            return def;
        try {
            Field field = R.string.class.getField(tvName);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;

    }
}
