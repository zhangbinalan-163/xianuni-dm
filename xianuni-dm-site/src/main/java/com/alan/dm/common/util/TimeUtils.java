package com.alan.dm.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 工具类
 */
public class TimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeUtils.class);
    /**
     * 将日期转换成字符串
     * @param date
     * @return
     */
    public static String convertToTimeString(Date date) {
        if(date == null) {
            return "";
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reportDate = df.format(date);
            return reportDate;
        } catch (Exception e) {
            LOGGER.warn("convert data to string fail");
            return "";
        }
    }

    /**
     * 将日期转换成字符串
     * @param date
     * @return
     */
    public static String convertToDateString(Date date) {
        if(date == null) {
            return "";
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String reportDate = df.format(date);
            return reportDate;
        } catch (Exception e) {
            LOGGER.warn("convert data to string fail");
            return "";
        }
    }
    /**
     * 将日期转换成字符串
     * @param date
     * @return
     */
    public static String convertToDateString(Date date,String pattern) {
        if(date == null) {
            return "";
        }
        try {
            DateFormat df = new SimpleDateFormat(pattern);
            String reportDate = df.format(date);
            return reportDate;
        } catch (Exception e) {
            LOGGER.warn("convert data to string fail");
            return "";
        }
    }
    /**
     * 将字符串转换成日期
     * @param source
     * @return
     */
    public static Date convertStringToDate(String source) {
        if(StringUtils.isEmpty(source)) {
            return null;
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(source);
        } catch (ParseException e) {
            LOGGER.error("convert string to date fail.");
        }
        return date;
    }
    /**
     * 将字符串转换成日期
     * @param source
     * @return
     */
    public static Date convertStringToDate(String source,String pattern) {
        if(StringUtils.isEmpty(source)) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(source);
        } catch (ParseException e) {
            LOGGER.error("convert string to date fail.");
        }
        return date;
    }
}
