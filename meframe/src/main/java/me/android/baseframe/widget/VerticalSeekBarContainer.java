package me.android.baseframe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by yuxiangxin on 2020/09/14
 * 描述: 垂直seekbar容器
 */

public final class VerticalSeekBarContainer extends FrameLayout {

    public VerticalSeekBarContainer (@NonNull Context context) {
        this(context, null);
    }

    public VerticalSeekBarContainer (@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private final VerticalSeekBar getChildSeekBar () {
        View child = this.getChildCount() > 0 ? this.getChildAt(0) : null;
        return child instanceof VerticalSeekBar ? (VerticalSeekBar) child : null;
    }

    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        VerticalSeekBar seekBar = this.getChildSeekBar();
        if (seekBar != null) {
            int hPadding = this.getPaddingLeft() + this.getPaddingRight();
            int vPadding = this.getPaddingTop() + this.getPaddingBottom();
            seekBar.measure(
                    MeasureSpec.makeMeasureSpec(Math.max(0, h - vPadding), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(Math.max(0, w - hPadding), MeasureSpec.AT_MOST));
        }

        this.applyViewRotation(w, h);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        VerticalSeekBar seekBar = this.getChildSeekBar();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (seekBar != null && widthMode != 1073741824) {
            int seekBarWidth = seekBar.getMeasuredHeight();
            int seekBarHeight = seekBar.getMeasuredWidth();
            int hPadding = this.getPaddingLeft() + this.getPaddingRight();
            int vPadding = this.getPaddingTop() + this.getPaddingBottom();
            int innerContentWidthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, widthSize - hPadding), widthMode);
            int innerContentHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, heightSize - vPadding), heightMode);
            seekBar.measure(innerContentHeightMeasureSpec, innerContentWidthMeasureSpec);
            int measuredWidth = View.resolveSizeAndState(seekBarWidth + hPadding, widthMeasureSpec, 0);
            int measuredHeight = View.resolveSizeAndState(seekBarHeight + vPadding, heightMeasureSpec, 0);
            this.setMeasuredDimension(measuredWidth, measuredHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    private final void applyViewRotation (int w, int h) {
        VerticalSeekBar seekBar = this.getChildSeekBar();
        this.setLayoutDirection(0);
        if (seekBar != null) {
            int seekBarMeasuredWidth = seekBar.getMeasuredWidth();
            int seekBarMeasuredHeight = seekBar.getMeasuredHeight();
            int hPadding = this.getPaddingLeft() + this.getPaddingRight();
            int vPadding = this.getPaddingTop() + this.getPaddingBottom();
            byte var9 = 0;
            int var10 = w - hPadding;
            boolean var11 = false;
            float hOffset = (float) (Math.max(var9, var10) - seekBarMeasuredHeight) / 2.0F;
            ViewGroup.LayoutParams lp = seekBar.getLayoutParams();
            byte var15 = 0;
            int var16 = h - vPadding;
            boolean var12 = false;
            int var14 = Math.max(var15, var16);
            lp.width = var14;
            lp.height = -2;
            seekBar.setLayoutParams(lp);
            seekBar.setPivotX(0.0F);
            seekBar.setPivotY(0.0F);
            seekBar.setRotation(270.0F);
            seekBar.setTranslationX(hOffset);
            seekBar.setTranslationY((float) seekBarMeasuredWidth);
        }

    }

}
