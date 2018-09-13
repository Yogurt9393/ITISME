package com.zp.itisme.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapHttpUtils {

    public static Bitmap getBitMap(String url) {
        HttpURLConnection huc = null;
        BufferedInputStream bis = null;
        try {
            URL url2 = new URL(url);
            huc = (HttpURLConnection) url2.openConnection();
            huc.setConnectTimeout(5000);
            huc.connect();
            if (huc.getResponseCode() == 200) {
                bis = new BufferedInputStream(huc.getInputStream());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len;
                byte[] data = new byte[1024];
                while ((len = bis.read(data)) != -1) {
                    baos.write(data, 0, len);
                }
                byte[] img = baos.toByteArray();
                return BitmapFactory.decodeByteArray(img, 0, img.length);
            }
        } catch (Exception e) {
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (huc != null) {
                huc.disconnect();
            }
        }
        return null;
    }

}
