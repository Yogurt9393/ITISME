package com.zp.itisme.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by MS on 2018/9/13.
 */

public class LoadItemImage {

    /**
     * 加载图片的时候自动调整大小
     *
     * @param context
     * @param imageView
     * @param url
     */
    private static WindowManager manager;
    private static Display display;
    public static void set(Context context, final ImageView imageView, String url){
        manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = manager.getDefaultDisplay();
        new BitMapAsyncTask(new BitMapAsyncTask.CallBack() {
            @Override
            public void senBit(Bitmap bitmap, String url) {
                float bili = ((float) display.getWidth() / (float) bitmap.getWidth());
                ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(imageView.getLayoutParams());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
                layoutParams.width = (int) (bitmap.getWidth() * bili);
                layoutParams.height = (int) (bitmap.getHeight() * bili);
                imageView.setLayoutParams(layoutParams);
                int width = layoutParams.width;
                int height = layoutParams.height;
            }
        }, new BitMapAsyncTask.ErrorBack() {
            @Override
            public void sendError(String result) {

            }
        }).execute(url);
    }

}
