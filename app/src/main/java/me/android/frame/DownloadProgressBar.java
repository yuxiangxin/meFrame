package me.android.frame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import me.android.baseframe.utils.DisplayUtil;

/**
 * Created by yuxiangxin on 2020/09/01
 * 描述: 应用下载进度条
 */
public class DownloadProgressBar extends View {

    public static final int LOOP_ITEM_COUNT = 4;
    private Paint mPaint;
    private Paint mBgPaint;
    private long mMax;
    private long mProgress;
    private int mWidth, mHeight;
    private RectF mProgressRectF;
    private RectF mBgRectF;
    private boolean mLoop = false;
    private Paint mLoopPaint;
    private ArrayList<RectF> mLoopRectfs;
    private int mLoopItemWidth;
    private int mItemMove;
    private int mCirWidth;

    public DownloadProgressBar (Context context) {
        this(context, null);
    }

    public DownloadProgressBar (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(0xFF328CF0);
        mPaint.setAntiAlias(true);
        mProgressRectF = new RectF();
        mBgPaint = new Paint();
        mBgPaint.setColor(0xFFCCCCCC);
        mBgPaint.setAntiAlias(true);
        mBgRectF = new RectF();
        mLoopPaint = new Paint();
        mLoopPaint.setAntiAlias(true);
        mLoopPaint.setColor(0xFFFFFFFF);
        mLoopItemWidth = DisplayUtil.dip2px(5);
        mItemMove = DisplayUtil.dip2px(10);
        mCirWidth = DisplayUtil.dip2px(5);
    }

    public void setMax (long max) {
        mMax = max;
    }

    public void setProgress (int progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBgRectF, mHeight / 2, mHeight / 2, mBgPaint);
        float percent = 1.0F * mProgress / mMax;
        mProgressRectF.left = 0;
        mProgressRectF.top = 0;
        mProgressRectF.right = percent * mWidth;
        mProgressRectF.bottom = mHeight;
        canvas.drawRoundRect(mProgressRectF, mHeight / 2, mHeight / 2, mPaint);

        if (mLoop) {
            ArrayList<RectF> rectfs = getLoop();
            for (RectF rectf : rectfs) {
                canvas.drawRect(rectf, mLoopPaint);
                //canvas.drawCircle(rectf.left, mHeight / 2, mCirWidth, mLoopPaint);
            }
            getHandler().postDelayed(() -> invalidate(), 10);
        }

    }

    private ArrayList<RectF> getLoop () {
        if (mLoopRectfs == null) {
            mLoopRectfs = new ArrayList<>();
            for (int i = LOOP_ITEM_COUNT; i >= 0; i--) {
                RectF rectF = new RectF();
                int offx = LOOP_ITEM_COUNT - (i * mWidth / LOOP_ITEM_COUNT);
                rectF.left = offx;
                rectF.top = 0;
                rectF.right = mLoopItemWidth + offx;
                rectF.bottom = mHeight;
                mLoopRectfs.add(rectF);
            }
        }
        for (int i = 0; i < mLoopRectfs.size(); i++) {
            RectF each = mLoopRectfs.get(i);
            each.left += mItemMove;
            each.right = each.left + mLoopItemWidth;
            if (each.left >= mWidth) {
                each.left = 0;
                each.right = each.left + mLoopItemWidth;
            }
        }
        return mLoopRectfs;
    }

    public void setLoop (boolean loop) {
        mLoop = loop;
        invalidate();
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight();
        mWidth = getWidth();
        if (mBgRectF == null) {
            mBgRectF = new RectF();
        }
        mBgRectF.left = 0;
        mBgRectF.top = 0;
        mBgRectF.right = mWidth;
        mBgRectF.bottom = mHeight;
    }
}
