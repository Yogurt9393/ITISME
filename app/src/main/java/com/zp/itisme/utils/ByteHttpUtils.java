package com.zp.itisme.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MS on 2018/8/9.
 */

public class ByteHttpUtils {

    /**
     * 该方法实现网络数据的获取，实现原理HttpURLConnection,GET请求
     *
     * @param url 网络资源路径
     * @return 字节数组
     * @author user
     */
    public static Integer getRes(String url, String filepath) {
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
                FileOutputStream out = new FileOutputStream(new File(filepath));
                out.write(baos.toByteArray());
                out.close();
                return 0;
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
        return 1;
    }

}
