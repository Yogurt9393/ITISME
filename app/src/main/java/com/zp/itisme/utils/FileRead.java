package com.zp.itisme.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileRead {

    public static byte[] byByte(File file) {
        // 需要读取的文件，参数是文件的路径名加文件名
        if (file.isFile()) {
            // 以字节流方法读取文件
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                // 设置一个，每次 装载信息的容器
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                // 开始读取数据
                int len = 0;// 每次读取到的数据的长度
                while ((len = fis.read(buffer)) != -1) {// len值为-1时，表示没有数据了
                    // append方法往sb对象里面添加数据
                    outputStream.write(buffer, 0, len);
                }
                // 输出字符串
                return outputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
