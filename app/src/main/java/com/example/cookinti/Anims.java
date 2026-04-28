package com.example.cookinti;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class Anims {

    public static AnimatorSet ScaleViewAnim(View view)
    {
        AnimatorSet anims = new AnimatorSet();

        ObjectAnimator scalex = ObjectAnimator.ofFloat(view, "scaleX", 1f, 3f);
        scalex.setDuration(200);
        ObjectAnimator scaley = ObjectAnimator.ofFloat(view, "scaleY", 1f, 3f);
        scaley.setDuration(200);

        ObjectAnimator backx = ObjectAnimator.ofFloat(view, "scaleX", 3f, 1f);
        backx.setDuration(100);
        ObjectAnimator backy = ObjectAnimator.ofFloat(view, "scaleY", 3f, 1f);
        backy.setDuration(100);


        AnimatorSet a1 = new AnimatorSet();
        a1.playTogether(scalex, scaley);
        AnimatorSet a2 = new AnimatorSet();
        a2.playTogether(backx, backy);

        anims.playSequentially(a1, a2);

        return anims;
    }
}
