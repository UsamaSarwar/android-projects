package com.example.aamir.doctor.Classes;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.aamir.doctor.R;

/**
 * Created by Aamir on 8/24/2017.
 */


public class Animation {

    public static void shakeView(View view, Context context) {
        android.view.animation.Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake_view);
        view.startAnimation(shake);
    }

    public static void shakeViewReverse(View view, Context context) {
        android.view.animation.Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake_view_reverse);
        view.startAnimation(shake);
    }
}