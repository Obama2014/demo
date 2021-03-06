package com.example.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

import java.io.File;
import java.security.MessageDigest;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class DemoUtils {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DemoUtils.class);
    public static final String DEMO_DOMAIN = "127.0.0.1:8080/";
    public static final String IMAGE_DIR = "D:"+ File.separator+"upload"+File.separator;
    public static String[] IMAGE_FILE_EXT = new String[] {"png", "bmp", "jpg", "jpeg","gif"};

    public static boolean isFileAllowed(String fileExt){
        for(String ext:IMAGE_FILE_EXT){
            if(ext.equals(fileExt.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }

    public static String getJsonString(int code,String msg){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        return json.toJSONString();
    }

    public static String getJsonString(int code,Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        for(Map.Entry<String,Object> entry:map.entrySet()){
            json.put(entry.getKey(),entry.getValue());
        }
        return json.toJSONString();
    }



}
