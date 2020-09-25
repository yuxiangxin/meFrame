package me.android.baseframe.base;


import android.content.Context;

import androidx.annotation.WorkerThread;
import me.android.baseframe.net.HttpRespBean;
import me.android.baseframe.net.RequestBean;

/**
 * Created by yuxiangxin on 2020/07/03
 * 描述:提取BaseActivity和BaseFragment共同方法
 */
public interface IBase {

    Context getContext ();


    void onHttpStart (RequestBean req);

    @WorkerThread
    boolean onHttpParseData (RequestBean req, HttpRespBean resp);

    void onHttpSuccess (RequestBean req, HttpRespBean resp);

    void onHttpFailed (RequestBean req, HttpRespBean resp);

    void onHttpCompleted (RequestBean req);

    void post (Runnable runnable);

    void postDelayed (Runnable runnable, long delay);

}

