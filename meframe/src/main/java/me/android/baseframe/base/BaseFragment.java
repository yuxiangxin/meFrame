package me.android.baseframe.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import me.android.baseframe.net.HttpRespBean;
import me.android.baseframe.net.RequestBean;

/**
 * Created by yuxiangxin on 2020/07/03
 * 描述: 基类
 */
public abstract class BaseFragment extends Fragment implements IBase {

    public final String TAG = getClass().getSimpleName();

    private BaseImpl mBaseHelp;
    protected WeakHandler mHandler;
    protected View mRootView;

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        mRootView = inflater.inflate(getLayoutId(), null);
        mBaseHelp = new BaseImpl<>(this);
        mHandler = new WeakHandler(this);
        init(savedInstanceState);
        return mRootView;
    }

    public final <T extends View> T findViewById (@IdRes int id) {
        return mRootView.findViewById(id);
    }

    protected abstract int getLayoutId ();

    protected abstract void init (Bundle savedInstanceState);

    @Override
    public void onDestroy () {
        super.onDestroy();
        mBaseHelp.detachView();
    }

    @Override
    public void onHttpStart (RequestBean req) {

    }

    @Override
    public boolean onHttpParseData (RequestBean req, HttpRespBean resp) {
        return true;
    }

    @Override
    public void onHttpSuccess (RequestBean req, HttpRespBean resp) {

    }

    @Override
    public void onHttpFailed (RequestBean req, HttpRespBean resp) {

    }

    @Override
    public void onHttpCompleted (RequestBean req) {

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

    protected static class WeakHandler extends Handler {
        private WeakReference<BaseFragment> weak;

        private WeakHandler (BaseFragment activity) {
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
            return weak == null || weak.get() == null;
        }
    }

    private void handleMessage (Message msg) {

    }
}
