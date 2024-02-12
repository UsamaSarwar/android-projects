package com.example.aamir.tablayout.Classes;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.aamir.tablayout.R;


public class Animation {

    public static void shakeView(View view, Context context) {
        android.view.animation.Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake_view);
        view.startAnimation(shake);
    }
}