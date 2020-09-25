package me.android.baseframe.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;


public class AnimationsUtil {


    public static AnimatorSet scale (View view, long duration, Animator.AnimatorListener listener, float... values) {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(view, "scaleX", values);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(view, "scaleY", values);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        if (listener != null)
            set.addListener(listener);
        set.setInterpolator(new DecelerateInterpolator());
        set.playTogether(scaleXAnimation, scaleYAnimation);
        set.start();
        return set;
    }

    public static ObjectAnimator translateX (View view, long duration, Animator.AnimatorListener listener, float... values) {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(view, "translationX", values);
        scaleXAnimation.setDuration(duration);
        if (listener != null)
            scaleXAnimation.addListener(listener);
        scaleXAnimation.start();
        return scaleXAnimation;
    }

    public static void translateY (View view, long duration, Animator.AnimatorListener listener, float... values) {
        translateY(view, duration, listener, null, values);
    }

    public static void translateY (View view, long duration, Animator.AnimatorListener listener, TimeInterpolator interpolator, float... values) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", values);
        translationY.setDuration(duration);
        if (listener != null)
            translationY.addListener(listener);
        if (interpolator != null)
            translationY.setInterpolator(interpolator);
        translationY.start();
    }

    public static ObjectAnimator alpha (View view, long duration, Animator.AnimatorListener listener, float... values) {
        //view.setAlpha(values);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", values);
        alpha.setDuration(duration);
        if (listener != null)
            alpha.addListener(listener);
        alpha.start();
        return alpha;
    }

    public static void genAnimatorValue (long duration, ValueAnimator.AnimatorUpdateListener animationListen, Animator.AnimatorListener listener, float... values) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(values);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(animationListen);
        valueAnimator.addListener(listener);
        valueAnimator.start();
    }

    public static void rotate (View view, long duration, Animator.AnimatorListener listener, float... values) {
        rotate(view, duration, null, listener, values);
    }

    public static void colorAnim (View view, long duration, Animator.AnimatorListener listen, int... values) {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", values);
        colorAnim.setDuration(duration);
        colorAnim.setEvaluator(new ArgbEvaluator());
        //colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        //colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        if (listen != null)
            colorAnim.addListener(listen);
        colorAnim.start();
    }

    public static void rotate (View view, long duration, Interpolator interpolator, Animator.AnimatorListener listener, float... values) {
        //view.setRotation();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "rotation", values);
        alpha.setDuration(duration);
        if (interpolator != null) {
            alpha.setInterpolator(interpolator);
        }
        if (listener != null)
            alpha.addListener(listener);
        alpha.start();
    }

}



