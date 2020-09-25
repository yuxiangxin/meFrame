package me.android.frame;

import android.app.Application;

import me.android.baseframe.base.BaseFrame;
import me.android.baseframe.base.BuildFrameConfig;

public class App extends Application {

    @Override
    public void onCreate () {
        super.onCreate();
        BaseFrame.init(this, new BuildFrameConfig(BuildConfig.APPLICATION_ID, BuildConfig.DEBUG, BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME));
    }
}
