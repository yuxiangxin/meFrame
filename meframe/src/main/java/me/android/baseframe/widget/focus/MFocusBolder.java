package me.android.baseframe.widget.focus;

import android.graphics.Color;
import android.util.TypedValue;

/**
 * Created by yuxiangxin on 2020/08/29
 * 描述:
 */
public class MFocusBolder {

    public static AbsFocusBorder.Builder create () {
        return new FocusBorder.Builder().asColor()
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 0)
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3f)
                .borderColor(Color.RED)
                .padding(2f)
                .animDuration(0)
                .noShimmer()
                .noBreathing()
                .animMode(AbsFocusBorder.Mode.NOLL);
    }
}
