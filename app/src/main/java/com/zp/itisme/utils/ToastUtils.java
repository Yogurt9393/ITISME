package com.zp.itisme.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;
    public static void show(Context mContext,String msg){
        if (toast == null){
            toast = Toast.makeText(mContext,msg,Toast.LENGTH_SHORT);
            toast.show();
        }else{
            toast.cancel();
            toast = Toast.makeText(mContext,msg,Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
