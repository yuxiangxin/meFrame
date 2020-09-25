package me.android.frame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import me.android.baseframe.utils.LogUtils;


/**
 * Created by yuxiangxin on 2020/09/09
 * 描述: 直线进度条
 */
public class LineProgress extends AbsProgressView {

    private RectF mRectF;
    private static final String TAG = "LineProgress";

    public LineProgress (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRectF = new RectF();
    }

    @Override
    protected void onDrawProgress (float percent, Canvas canvas, Paint paint) {
        //canvas.drawLine(0, 0, percent * mWidth, mHeight, paint);
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.right = mWidth/2;
        mRectF.bottom = mHeight;
        LogUtils.v(TAG, "onDrawProgress"+mRectF.right);
        canvas.drawRect(mRectF, paint);
    }

    @Override
    protected int getPaintColor () {
        return Color.RED;
    }
}
