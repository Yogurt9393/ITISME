package com.zp.itisme.myapp;

import android.app.Activity;
import android.app.Application;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application{

    public static List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        activityList = new ArrayList<Activity>();
    }

    // 添加Activity到容器中
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish
    public static void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

}
