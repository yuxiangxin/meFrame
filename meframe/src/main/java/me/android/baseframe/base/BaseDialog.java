package me.android.baseframe.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * Created by yuxiangxin on 2020/09/01
 * 描述:dialog base
 */
public abstract class BaseDialog extends Dialog {


    public BaseDialog (@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setUpDialog();
    }

    public BaseDialog (@NonNull Context context, int themeResId) {
        super(context, themeResId);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setUpDialog();
    }

    private void setUpDialog () {
        Window mWindow = getWindow();
        mWindow.setBackgroundDrawableResource(getBackgroundDrawableResource());
        WindowManager.LayoutParams wl = mWindow.getAttributes();
        wl.gravity = Gravity.CENTER;
        mWindow.setAttributes(wl);
    }

    protected abstract int getBackgroundDrawableResource ();

    public BaseDialog setCancelable2 (boolean canceledOnTouchOutside) {
        super.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return this;
    }

    public BaseDialog setNoBackKeyEvent () {
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey (DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        return this;
    }

    public BaseDialog show2 () {
        super.show();
        return this;
    }

    public <T extends BaseDialog> T cast () {
        return (T) this;
    }

}
