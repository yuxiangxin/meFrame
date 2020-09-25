package me.android.baseframe.base;

/**
 * Created by yuxiangxin on 2020/09/08
 * 描述:BuildFrameConfig
 */
public class BuildFrameConfig {

    private final String applicationId;
    private final boolean debug;
    private final int versionCode;
    private final String versionName;

    public BuildFrameConfig (String applicationId, boolean debug, int versionCode, String versionName) {
        this.applicationId = applicationId;
        this.debug = debug;
        this.versionCode = versionCode;
        this.versionName = versionName;
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
