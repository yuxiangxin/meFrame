package me.android.baseframe.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;

/**
 * Created by yuxiangxin on 2020/09/14
 * 描述:竖向SeekBar
 */
public class VerticalSeekBar extends AppCompatSeekBar {
    private OnSeekBarChangeListener listener;
    private boolean fromUser;
    private int index;


    public VerticalSeekBar (@NonNull Context context) {
        super(context);
    }

    public VerticalSeekBar (@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public final boolean getFromUser () {
        return this.fromUser;
    }

    public final void setFromUser (boolean var1) {
        this.fromUser = var1;
    }

    public boolean onTouchEvent (@NonNull MotionEvent event) {
        boolean handled = super.onTouchEvent(event);
        if (handled) {
            ViewParent var10000;
            switch (event.getAction()) {
                case 0:
                    var10000 = this.getParent();
                    if (var10000 != null) {
                        var10000.requestDisallowInterceptTouchEvent(true);
                    }
                    this.fromUser = true;
                    break;
                case 1:
                case 3:
                    var10000 = this.getParent();
                    if (var10000 != null) {
                        var10000.requestDisallowInterceptTouchEvent(false);
                    }
                    this.fromUser = false;
                case 2:
            }
        }

        return handled;
    }

    public int getIndex () {
        return index;
    }

    public void setIndex (int index) {
        this.index = index;
    }

    public void setOnSeekBarChangeListener (@Nullable OnSeekBarChangeListener l) {
        this.listener = l;
        super.setOnSeekBarChangeListener(l);
    }

    public boolean onKeyDown (int keyCode, @NonNull KeyEvent event) {
        if (this.isEnabled()) {
            if (keyCode == 21 || keyCode == 22) {
                return false;
            }

            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP) {

                if ((keyCode == KeyEvent.KEYCODE_DPAD_UP && getProgress() >= getMax()) || (keyCode == KeyEvent.KEYCODE_DPAD_DOWN && getProgress() <= 0)) {
                    ViewParent parent = this.getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                    fromUser = false;
                    return super.onKeyDown(keyCode, event);
                }

                this.fromUser = true;
                OnSeekBarChangeListener var10000 = this.listener;
                if (var10000 != null) {
                    var10000.onStartTrackingTouch((SeekBar) this);
                }

                int direction = keyCode == 20 ? -1 : 1;
                int currentProgress = this.getProgress() + direction * this.getKeyProgressIncrement();
                if (currentProgress > this.getMax()) {
                    currentProgress = this.getMax();
                } else if (currentProgress < 0) {
                    currentProgress = 0;
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    this.setProgress(currentProgress, true);
                } else {
                    this.setProgress(currentProgress);
                }

                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp (int keyCode, @Nullable KeyEvent event) {
        this.fromUser = false;
        return super.onKeyUp(keyCode, event);
    }


}