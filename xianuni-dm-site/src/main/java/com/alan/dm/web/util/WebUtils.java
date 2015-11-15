package com.alan.dm.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * WEB工具类
 * Created by zhangbinalan on 15/8/15.
 */
public class WebUtils {
    /**
     * 从http request中获得响应的HEADER信息
     * @param request
     * @param name
     * @return
     */
    public static String getHeader(HttpServletRequest request, String name) {
        Enumeration headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return null;
        }
        while (headerNames.hasMoreElements()) {
            String key = (String)headerNames.nextElement();
            if (name.equals(key)) {
                return request.getHeader(key);
            }
        }
        return null;
    }
    /**
     * 获得指定的cookie
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0){
            return null;
        }
        for(Cookie cookie:cookies){
            if(name.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
