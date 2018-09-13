package com.zp.itisme.utils;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHttpUtils {

    public static String getJSONStr(String url, String str,String... encodingName) {
        byte[] postData = str.getBytes();
        HttpURLConnection huc = null;
        BufferedInputStream bis = null;
        try {
            URL url2 = new URL(url);
            huc = (HttpURLConnection) url2.openConnection();
            huc.setConnectTimeout(3000);
            huc.setRequestMethod("POST");//POST
            huc.setInstanceFollowRedirects(true);
            huc.setRequestProperty("Content-Type", "application/json");//Content-Type      application/json
            huc.connect();
            DataOutputStream dos = new DataOutputStream(huc.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            if (huc.getResponseCode() == 200) {
                bis = new BufferedInputStream(huc.getInputStream());
                StringBuilder builder = new StringBuilder();
                int len;
                byte[] data = new byte[1024];
                while (true) {
                    len = bis.read(data);
                    if (len == -1) {
                        break;
                    } else {
                        if (encodingName.length == 0) {
                            builder.append(new String(data, 0, len));
                        } else {
                            builder.append(new String(data, 0, len, encodingName[0]));
                        }
                    }
                }
                return builder.toString();
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
