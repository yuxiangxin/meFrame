package me.android.frame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by yuxiangxin on 2020/09/09
 * 描述: AbsBase进度条
 */
public abstract class AbsProgressView extends View {

    private Paint mPaint;
    private int progress = 0;
    private int max = 100;

    protected int mWidth, mHeight;

    public AbsProgressView (Context context) {
        this(context, null);
    }

    public AbsProgressView (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(getPaintColor());
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    protected int getPaintColor () {
        return Color.GREEN;
    }

    public void setPaintColor (int color) {
        mPaint.setColor(color);
    }

    public void setProgress (int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMax (int max) {
        this.max = max;
        invalidate();
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        float percent = 1.0F * progress / max;
        onDrawProgress(percent, canvas, mPaint);
    }

    protected abstract void onDrawProgress (float percent, Canvas canvas, Paint paint);
}
