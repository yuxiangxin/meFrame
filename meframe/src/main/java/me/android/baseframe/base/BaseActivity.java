package me.android.baseframe.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import me.android.baseframe.net.HttpRespBean;
import me.android.baseframe.net.HttpUtil;
import me.android.baseframe.net.RequestBean;
import me.android.baseframe.utils.LogUtils;

/**
 * Created by yuxiangxin on 2020/07/03
 * 描述: 基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IBase {

    public final String TAG = getClass().getSimpleName();

    private BaseImpl mBaseHelp;
    protected WeakHandler mHandler;
    private SparseArray<RequestBean> mReqs;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        LogUtils.v(TAG, "" + "onCreate");
        super.onCreate(savedInstanceState);
        mHandler = new WeakHandler(this);
        setContentView(getLayoutId());
        mBaseHelp = new BaseImpl<>(this);
        init(savedInstanceState);
    }

    protected abstract void init (Bundle savedInstanceState);

    protected abstract int getLayoutId ();

    @Override
    public Context getContext () {
        return this;
    }

    @Override
    public void post (Runnable runnable) {
        if (mHandler != null) {
            mHandler.post(runnable);
        }
    }

    @Override
    public void postDelayed (Runnable runnable, long delay) {
        if (mHandler != null) {
            mHandler.postDelayed(runnable, delay);
        }
    }

    protected String getIntentStringValue (String key) {
        if (getIntent().hasExtra(key)) {
            return getIntent().getStringExtra(key);
        }
        return null;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        HttpUtil.get().cancelRequestByTag(TAG);
        mHandler.removeCallbacksAndMessages(null);
        mBaseHelp.detachView();
    }

    protected static class WeakHandler extends Handler {
        private WeakReference<BaseActivity> weak;

        private WeakHandler (BaseActivity activity) {
            this.weak = new WeakReference<>(activity);
        }


        @Override
        public void handleMessage (Message msg) {
            if (isMiss()) {
                return;
            }
            weak.get().handleMessage(msg);
        }

        private boolean isMiss () {
            return weak == null || weak.get() == null ||
                    weak.get().isFinishing() || weak.get().isDestroyed();
        }
    }

    public RequestBean getCacheReq (int action) {
        checkReqsCaches();
        return mReqs.get(action);
    }

    protected void doHttpReq (RequestBean req) {
        checkReqsCaches();
        mReqs.put(req.action, req);
        mBaseHelp.doHttpReq(req);
    }

    private void checkReqsCaches () {
        if (mReqs == null) {
            mReqs = new SparseArray<>();
        }
    }

    @Override
    public void onHttpStart (RequestBean req) {

    }

    @Override
    public boolean onHttpParseData (RequestBean req, HttpRespBean resp) {
        return true;
    }

    @Override
    public void onHttpSuccess (RequestBean req, HttpRespBean parseData) {

    }

    @Override
    public void onHttpFailed (RequestBean req, HttpRespBean resp) {

    }

    @Override
    public void onHttpCompleted (RequestBean req) {

    }

    protected void handleMessage (Message msg) {

    }


}
