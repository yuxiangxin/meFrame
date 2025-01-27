package me.android.baseframe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class SquareLayout extends FrameLayout {
    public SquareLayout (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout (Context context) {
        super(context);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        int min = Math.min(childWidthSize, childHeightSize);
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                min, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
