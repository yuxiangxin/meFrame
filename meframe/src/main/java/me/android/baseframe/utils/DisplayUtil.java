package me.android.baseframe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.WindowManager;
import android.widget.ImageView;


import androidx.annotation.ColorRes;
import androidx.core.graphics.drawable.DrawableCompat;
import me.android.baseframe.base.BaseFrame;

/**
 * dp、sp 转换为 px 的工具类
 *
 * @author fxsky 2012.11.12
 */
public class DisplayUtil {

    public static int mScreenWidth = 0;
    public static int mScreenHeight = 0;

    public static int getColor (@ColorRes int color) {
        return BaseFrame.getContext().getResources().getColor(color);
    }

    public static int px2dip (float pxValue) {
        final float scale = BaseFrame.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px (float dipValue) {
        final float scale = BaseFrame.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static float dip2px2Float (float dipValue) {
        final float scale = BaseFrame.getContext().getResources().getDisplayMetrics().density;
        return (dipValue * scale);
    }

    public static int px2sp (float pxValue) {
        final float fontScale = BaseFrame.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getScreenWidth () {
        if (mScreenWidth <= 0) {
            WindowManager wm = (WindowManager) BaseFrame.getContext().getSystemService(Context.WINDOW_SERVICE);
            mScreenWidth = wm.getDefaultDisplay().getWidth();
        }
        return mScreenWidth;
    }

    public static int getScreenHeight () {
        if (mScreenHeight <= 0) {
            WindowManager wm = (WindowManager) BaseFrame.getContext().getSystemService(Context.WINDOW_SERVICE);
            mScreenHeight = wm.getDefaultDisplay().getHeight();
        }
        return mScreenHeight;
    }

    public static int sp2px (float spValue) {
        final float fontScale = BaseFrame.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * TextView适用 文字多种颜色的span
     */
    public static SpannableString setTextSpanColor (String text, int color, int startIndex, int endIndent) {
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        ss.setSpan(span, startIndex, endIndent, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Deprecated
    public static SpannableString setTextSpan (String text, int dp, int startIndex, int endIndent) {
        SpannableString span = new SpannableString(text);
        span.setSpan(new AbsoluteSizeSpan(dip2px(dp)), startIndex, endIndent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static SpannableString setTextSpan2 (String text, int size, int startIndex, int endIndent) {
        SpannableString span = new SpannableString(text);
        span.setSpan(new AbsoluteSizeSpan(size), startIndex, endIndent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static SpannableString setTextSpan4Money (String text, int size) {
        if (TextUtils.isEmpty(text))
            return new SpannableString("");
        SpannableString span = new SpannableString(text);
        span.setSpan(new AbsoluteSizeSpan(dip2px(size)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int lastPoint = text.lastIndexOf(".");
        if (lastPoint != -1) {
            span.setSpan(new AbsoluteSizeSpan(dip2px(size)), lastPoint, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return span;
    }

    public static SpannableString setTudiMoneySpan (String text, int size) {
        if (TextUtils.isEmpty(text))
            return new SpannableString("");
        SpannableString span = new SpannableString(text);
        span.setSpan(new AbsoluteSizeSpan(dip2px(size)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int lastPoint = text.lastIndexOf(".");
        if (lastPoint != -1) {
            span.setSpan(new AbsoluteSizeSpan(dip2px(size)), lastPoint, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return span;
    }


    public static SpannableString setTextSpan4Money (Context context, String text, int size) {
        int lastPoint = text.lastIndexOf(".");
        if (!TextUtils.isEmpty(text) && lastPoint != -1) {

            SpannableString span = new SpannableString(text);
            span.setSpan(new AbsoluteSizeSpan(dip2px(size)), 0, lastPoint,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return span;
        }
        return null;
    }

    public static int getStatusBarHeight (Context context) {
        int statusBarHeight1 = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }


    /**
     * 可见屏幕高度
     **/
    public static int getAppHeight (Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    /**
     * 获取actiobar高度
     **/
    public static int getActionBarHeight (Activity paramActivity) {
        if (true) {
            return dip2px(56);
        }
        int[] attrs = new int[]{android.R.attr.actionBarSize};
        TypedArray ta = paramActivity.obtainStyledAttributes(attrs);
        return ta.getDimensionPixelSize(0, dip2px(56));
    }

    /**
     * 键盘是否在显示
     **/
    public static boolean isKeyBoardShow (Activity paramActivity) {
        int height = getScreenHeight() - getStatusBarHeight(paramActivity)
                - getAppHeight(paramActivity);
        return height != 0;
    }

    public static void setTintDrawable (ImageView view, int resId, int color) {
        Drawable menuDraw = view.getDrawable() != null ? view.getDrawable() : DrawableCompat.wrap(view.getContext().getResources().getDrawable(resId));
        DrawableCompat.setTint(menuDraw, color);
        view.setImageDrawable(menuDraw);
    }

}
