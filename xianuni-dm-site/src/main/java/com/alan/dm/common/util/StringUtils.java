package com.alan.dm.common.util;

import com.alan.dm.common.exception.DMException;
import com.alibaba.fastjson.JSON;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by zhangbinalan on 15/8/15.
 */
public class StringUtils {
    /**
     * 将对象转换为json格式的字符创
     * @param object
     * @return
     */
    public static String toJsonString(Object object){
        return JSON.toJSONString(object);
    }

    /**
     * 将json转换成对象
     * @param json
     * @param target
     * @param <T>
     * @return
     * @throws com.alan.dm.common.exception.DMException
     */
    public static <T> T jsonToObject(String json, Class<T> target) throws DMException {
        return JSON.parseObject(json, target);
    }
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str==null||str.equals("");
    }

    /**
     * 检查手机号是否合法
     * @param mobile
     * @return
     */
    public static boolean isLegalMobile(String mobile) {
        if (mobile == null) {
            return false;
        }
        Pattern p = Pattern.compile("^(13|14|15|18|17)\\d{9}$");
        Matcher m = p.matcher(mobile);
        boolean b = m.matches();
        return b;
    }

    /**
     * MD5加密
     * @param str
     * @return
     */
    public static String md5(String str) {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            byte[] messageDigest = md.digest(str.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String tmp = Integer.toHexString(0xFF & messageDigest[i]);
                if (tmp.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(tmp);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
