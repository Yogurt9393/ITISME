package com.zp.itisme.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Android Stidio.
 *
 * @author ZP
 * @email try.zp.catch@gmail.com
 * @date 2017/8/15
 */

public class SPUtils {

    public static void put(Context context,String name,String str){
        SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, str);
        editor.commit();
    }
    public static void put(Context context,String name,int str){
        SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(name, str);
        editor.commit();
    }
    public static void put(Context context,String name,float str){
        SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(name, str);
        editor.commit();
    }
    public static void put(Context context,String name,boolean str){
        SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, str);
        editor.commit();
    }
    public static void put(Context context,String name,long str){
        SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(name, str);
        editor.commit();
    }

    public static String get(Context context,String name,String type){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(name, type);
        return str;
    }

    public static int get(Context context,String name,int type){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        int str = sharedPreferences.getInt(name, type);
        return str;
    }

    public static float get(Context context,String name,float type){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        float str = sharedPreferences.getFloat(name, type);
        return str;
    }

    public static boolean get(Context context,String name,boolean type){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        boolean str = sharedPreferences.getBoolean(name, type);
        return str;
    }

    public static long get(Context context,String name,long type){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        long str = sharedPreferences.getLong(name, type);
        return str;
    }

    public static void clear(Context context){
        SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
    }
}
