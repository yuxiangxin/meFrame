package me.android.baseframe.base;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;

import me.android.baseframe.utils.LogUtils;

/**
 * Created by yuxiangxin on 2020/09/08
 * 描述:BuildFrameConfig
 */
public class BuildFrameConfig {

    private static final String TAG = "BuildFrameConfig";
    private String applicationId;
    private boolean debug;
    private int versionCode;
    private String versionName;

     BuildFrameConfig (Class BuildConfigClass) {
        try {
            Object instance = BuildConfigClass.newInstance();
            Field[] fields = BuildConfigClass.getDeclaredFields();
            for (Field field : fields) {
                switch (field.getName()) {
                    case "APPLICATION_ID":
                        applicationId = (String) field.get(instance);
                        break;
                    case "DEBUG":
                        debug = (boolean) field.get(instance);
                        break;
                    case "VERSION_CODE":
                        versionCode = (int) field.get(instance);
                        break;
                    case "VERSION_NAME":
                        versionName = (String) field.get(instance);
                        break;
                    default:
                        //doNothing
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.v(TAG, "" + JSON.toJSONString(this));

    }

    public String getApplicationId () {
        return applicationId;
    }

    public boolean isDebug () {
        return debug;
    }

    public int getVersionCode () {
        return versionCode;
    }

    public String getVersionName () {
        return versionName;
    }


}
