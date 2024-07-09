package com.example.smartgreenhouse.utils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author :Kr
 * @date :2024/3/23 20:59
 */
@Component
public class StreamUtil {
    //将流转化为文件的形式
    static final String PREFIX = "stream2file";//前缀字符串定义文件名；必须至少三个字符
    static final String SUFFIX = ".png";//后缀字符串定义文件的扩展名；如果为null，则将使用后缀".tmp"
    public  File stream2file (InputStream in) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

}
