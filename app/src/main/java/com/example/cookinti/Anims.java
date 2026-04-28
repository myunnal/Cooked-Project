package com.example.cookinti;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class Anims {

    public static AnimatorSet ScaleViewAnim(View view) {
        return ScaleViewAnim(view, 2f);
    }

    public static AnimatorSet ScaleViewAnim(View view, float power)
    {
        AnimatorSet anims = new AnimatorSet();

        ObjectAnimator scalex = ObjectAnimator.ofFloat(view, "scaleX", 1f, power);
        scalex.setDuration(200);
        ObjectAnimator scaley = ObjectAnimator.ofFloat(view, "scaleY", 1f, power);
        scaley.setDuration(200);

        scalex.setInterpolator(new DecelerateInterpolator());
        scaley.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator backx = ObjectAnimator.ofFloat(view, "scaleX", power, 1f);
        backx.setDuration(100);
        ObjectAnimator backy = ObjectAnimator.ofFloat(view, "scaleY", power, 1f);
        backy.setDuration(100);

        backx.setInterpolator(new DecelerateInterpolator());
        backy.setInterpolator(new DecelerateInterpolator());

        AnimatorSet a1 = new AnimatorSet();
        a1.playTogether(scalex, scaley);
        AnimatorSet a2 = new AnimatorSet();
        a2.playTogether(backx, backy);

        anims.playSequentially(a1, a2);

        return anims;
    }

    public static AnimatorSet Shake(View view, float power)
    {
        ObjectAnimator shake = ObjectAnimator.ofFloat(view,"translationX",-10f * power, 10f * power);
        shake.setDuration(50);
        shake.setRepeatMode(ObjectAnimator.REVERSE);
        shake.setRepeatCount(5);

        ObjectAnimator stop = ObjectAnimator.ofFloat(view,"translationX",0, 0);

        AnimatorSet anims = new AnimatorSet();
        anims.playSequentially(shake, stop);

        return anims;
    }
}
