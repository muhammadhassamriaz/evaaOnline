package com.ciesto.evaafashion.Other;

import android.app.Activity;
import android.content.Context;

import com.ciesto.evaafashion.R;

@SuppressWarnings("ALL")
public class Functions {


    public static void animNext(Context context) {
        //((Activity) context).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void animBack(Context context) {
        //((Activity) context).overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        ((Activity) context).overridePendingTransition(0, R.anim.fade_out);

    }




}
