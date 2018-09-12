package com.zp.itisme.myapp;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Android Stidio.
 *
 * @author ZP
 * @email try.zp.catch@gmail.com
 * @date 2018/9/11
 */
public class MyApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
