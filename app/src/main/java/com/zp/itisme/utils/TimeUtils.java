package com.zp.itisme.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MS on 2018/9/13.
 */

public class TimeUtils {


    public static String setMMddHHmm(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM/dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        long i = Long.parseLong(time);
        String times = sdr.format(new Date(i));
        return times;
    }

    public static String setHHmm(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        long i = Long.parseLong(time);
        String times = sdr.format(new Date(i));
        return times;
    }

    public static int getm(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        long i = Long.parseLong(time);
        String times = sdr.format(new Date(i));
        return Integer.parseInt(times);
    }

    public static int getH(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        long i = Long.parseLong(time);
        String times = sdr.format(new Date(i));
        return Integer.parseInt(times);
    }

    public static int getD(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        long i = Long.parseLong(time);
        String times = sdr.format(new Date(i));
        return Integer.parseInt(times);
    }

}
