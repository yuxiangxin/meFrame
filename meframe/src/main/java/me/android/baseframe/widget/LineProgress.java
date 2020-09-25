package me.android.baseframe.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * Created by yuxiangxin on 2020/09/09
 * 描述: 直线进度条
 */
public class LineProgress extends AbsProgressView {

    private RectF mRectF;

    public LineProgress (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRectF = new RectF();
    }

    @Override
    protected void onDrawProgress (float percent, Canvas canvas, Paint paint) {
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.right = percent * mWidth;
        mRectF.bottom = mHeight;
        canvas.drawRect(mRectF, paint);
    }

    @Override
    protected int getPaintColor () {
        return Color.RED;
    }
}
