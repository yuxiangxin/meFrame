package me.android.baseframe.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by yuxiangxin on 2020/08/28
 * 描述: 字体自动滚动
 */
public class ScrollTextView extends AppCompatTextView implements IScrollContorl {

    private boolean mScrollEnable = false;

    public ScrollTextView (Context context) {
        super(context);
        setScrollProperties();
    }

    public ScrollTextView (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScrollProperties();
    }

    private void setScrollProperties () {
        setSingleLine(true);
        setScrollEnable(false);
        setMarqueeRepeatLimit(-1);
    }


    @Override
    public boolean isFocused () {
        return mScrollEnable;
    }

    public void setScrollEnable (boolean scrollEnable) {
        mScrollEnable = scrollEnable;
        if (mScrollEnable) {
            setEllipsize(TextUtils.TruncateAt.valueOf("MARQUEE"));
        } else {
            setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        }
        invalidate();
    }

    public boolean isScrollEnable () {
        return mScrollEnable;
    }
}
