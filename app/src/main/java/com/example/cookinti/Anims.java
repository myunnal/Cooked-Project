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

    public static AnimatorSet ScaleAndMoveItem(View view, boolean isSelected){
        float scale = isSelected ? 1.2f : 1.0f;
        float translationY = isSelected ? -30f : 0f;

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scale);
        scaleX.setDuration(200);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scale);
        scaleY.setDuration(200);
        scaleX.setInterpolator(new DecelerateInterpolator());
        scaleY.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "translationY", translationY);
        translateY.setDuration(200);
        translateY.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(scaleX, scaleY, translateY);

        return animSet;
    }

    public static AnimatorSet ItemAppearing(View view, boolean isAppearing){
        ObjectAnimator scaleX = new ObjectAnimator();
        ObjectAnimator scaleY = new ObjectAnimator();

        if(isAppearing){
            scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1.2f, 1f);
            scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1.2f, 1f);
        }
        else{
            scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
            scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f);
        }

        scaleX.setDuration(400);
        scaleY.setDuration(400);
        scaleX.setInterpolator(new AccelerateInterpolator());
        scaleY.setInterpolator(new AccelerateInterpolator());

        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(scaleX, scaleY);

        return animSet;
    }
}
