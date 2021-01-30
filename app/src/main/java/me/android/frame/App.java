package me.android.frame;

import android.app.Application;

import me.android.baseframe.base.BaseFrame;

public class App extends Application {

    @Override
    public void onCreate () {
        super.onCreate();
        BaseFrame.init(this, BuildConfig.class);
    }
}
