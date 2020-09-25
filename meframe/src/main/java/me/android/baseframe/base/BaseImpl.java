package me.android.baseframe.base;

import android.content.Context;

import androidx.fragment.app.Fragment;
import me.android.baseframe.net.HttpRespBean;
import me.android.baseframe.net.HttpUtil;
import me.android.baseframe.net.ICallResponse;
import me.android.baseframe.net.RequestBean;

/**
 * Created by yuxiangxin on 2020/08/23
 * 描述:Activity&Fragment Presenter
 */
public class BaseImpl<T extends IBase> implements ICallResponse {

    private final String TAG;

    public T mView;


    BaseImpl (T view) {
        mView = view;
        TAG = view.getClass().getSimpleName();
    }


    private boolean isFragment () {
        return mView instanceof Fragment;
    }

    private Context getContext () {
        return mView.getContext();
    }

    public void doHttpReq (RequestBean req) {
        req.setTag(TAG);
        mView.onHttpStart(req);
        HttpUtil.get().doHttp(req, this);
    }


    @Override
    public void onSuccess (RequestBean req, HttpRespBean resp) {
        boolean parseSuccess = mView.onHttpParseData(req, resp);
        if (parseSuccess) {
            mView.post(() -> {
                mView.onHttpSuccess(req, resp);
                mView.onHttpCompleted(req);
            });
        } else {
            resp.setParseFailed();
            mView.post(() -> {
                mView.onHttpFailed(req, resp);
                mView.onHttpCompleted(req);
            });
        }
    }

    @Override
    public void onFailed (RequestBean req, HttpRespBean resp) {
        mView.post(() -> {
            mView.onHttpFailed(req, resp);
            mView.onHttpCompleted(req);
        });
    }

    public void detachView () {
        mView = null;
        HttpUtil.get().cancelRequestByTag(TAG);
    }
}
