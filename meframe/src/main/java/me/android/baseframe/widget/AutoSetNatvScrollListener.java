package me.android.baseframe.widget;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;


/**
 * Created by yuxiangxin on 2020/08/28
 * 描述:聚焦设置自动滚动
 */
public class AutoSetNatvScrollListener implements ViewTreeObserver.OnGlobalFocusChangeListener {

    private int mTvId;

    public AutoSetNatvScrollListener (int tvId) {
        mTvId = tvId;
    }

    @Override
    public void onGlobalFocusChanged (View oldFocus, View newFocus) {
        findNameTvFixScroll(oldFocus, false);
        findNameTvFixScroll(newFocus, true);
    }

    private void findNameTvFixScroll (View parent, boolean enable) {
        if (parent != null) {
            View viewById = parent.findViewById(mTvId);
            if (viewById instanceof IScrollContorl) {
                ((IScrollContorl) viewById).setScrollEnable(enable);
            }
        }
    }

    public void bind (ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.getViewTreeObserver().addOnGlobalFocusChangeListener(this);
        }
    }

    public void unBind (ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
        }
    }
}
