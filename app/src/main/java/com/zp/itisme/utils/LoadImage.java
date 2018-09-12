package com.zp.itisme.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class LoadImage {

    public static void set(Context context, ImageView imageView, String icon_path) {
        if (imageView == null) return;
        if (TextUtils.isEmpty(icon_path)) return;
        Picasso.with(context).load(icon_path).into(imageView);
    }

}
