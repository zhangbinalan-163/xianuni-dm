package com.alan.dm.common.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * JSON相关工具类
 * Created by zhangbin2 on 15/9/9.
 */
public class JsonUtils {
    /**
     * 将对象转化为JSON字符串
     * @param object
     * @return
     */
    public static String fromObject(Object object){
        //默认不转换为NULL的属性
        return JSON.toJSONString(object);
    }

    /**
     * 将JSON字符串转换为对象
     * @param value
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T toObject(String value,Class<T> targetClass){
        return JSON.parseObject(value, targetClass);
    }

    /**
     * 将JSON字符串转换为List
     * @param value
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> toObjectList(String value,Class<T> targetClass){
        return JSON.parseArray(value, targetClass);
    }
}
