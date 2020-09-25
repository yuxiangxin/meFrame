package me.android.baseframe.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by yuxiangxin on 2020/08/23
 * 描述: view通用工具类
 */
public class ViewUtils {

    private static final String TAG = "RecyclerViewPagerUtils";

    /**
     * 如果有内容则显示并赋值,否则不显示
     *
     * @param view    TextView
     * @param content
     */
    public static boolean setTextWithVisible (TextView view, CharSequence content) {
        if (TextUtils.isEmpty(content)) {
            view.setVisibility(GONE);
            return true;
        } else {
            view.setVisibility(VISIBLE);
            view.setText(content);
            return false;
        }
    }

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    public static void setViewVisible (View view, @Visibility int visible) {
        if (view != null && view.getVisibility() != visible) {
            view.setVisibility(visible);
        }
    }

    public static boolean setText (TextView view, CharSequence text) {
        if (view == null) {
            LogUtils.e(TAG, "TextView is Null,setText cancel!");
            return false;
        }
        if (TextUtils.isEmpty(text)) {
            view.setText("");
            return false;
        } else {
            view.setText(text);
            return true;
        }
    }


    public static boolean setTextWithInVisible (TextView view, String content) {
        if (TextUtils.isEmpty(content)) {
            view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            view.setVisibility(VISIBLE);
            view.setText(content);
            return false;
        }
    }

}
