package com.alan.dm.web.vo;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.exception.InvalidParamException;
import com.alan.dm.common.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 请求的封装
 * Created by zhangbinalan on 15/10/20.
 */
public class Request {
    /**
     * 实际的HTTP请求
     */
    private HttpServletRequest httpServletRequest;
    /**
     * 实际的HTTP响应
     */
    private HttpServletResponse httpServletResponse;


    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public int getInt(String key) throws DMException {
        String str = httpServletRequest.getParameter(key);
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidParamException("参数缺失或格式错误:" + key);
        }
    }

    public int getInt(String key, int defau) throws DMException {
        String str = httpServletRequest.getParameter(key);
        if (StringUtils.isEmpty(str)) {
            return defau;
        }
        return getInt(key);
    }

    public long getLong(String key) throws DMException {
        String str = httpServletRequest.getParameter(key);
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new InvalidParamException("参数缺失或格式错误:" + key);
        }
    }

    public long getLong(String key, long defau) throws DMException {
        String str = httpServletRequest.getParameter(key);
        if (StringUtils.isEmpty(str)) {
            return defau;
        }
        return getLong(key);
    }

    public String getString(String key) throws DMException {
        String str = httpServletRequest.getParameter(key);
        if (StringUtils.isEmpty(str)) {
            throw new InvalidParamException("参数缺失或格式错误:" + key);
        }
        return str;
    }
    public float getFloat(String key) throws DMException {
        String str = httpServletRequest.getParameter(key);
        if (StringUtils.isEmpty(str))
            throw new InvalidParamException("参数缺失或格式错误:" + key);
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            throw new InvalidParamException("参数缺失或格式错误:" + key, e);
        }
    }

    public String getString(String key, String defau) {
        String str = httpServletRequest.getParameter(key);
        if(StringUtils.isEmpty(str)) {
            return defau;
        }
        return str;
    }

    public String[] getStringArray(String key) throws DMException {
        String[] arr = httpServletRequest.getParameterValues(key);
        if ((arr == null) || (arr.length == 0)) {
            throw new InvalidParamException("参数缺失或格式错误:" + key);
        }
        return arr;
    }

    public String[] getStringArray(String key, String[] defau) {
        String[] arr = httpServletRequest.getParameterValues(key);
        if ((arr == null) || (arr.length == 0)) {
            return defau;
        }
        return arr;
    }

    public Date getDate(String key, String pattern) throws DMException {
        String str = httpServletRequest.getParameter(key);
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        if ((pattern == null) || (pattern.trim().equals(""))) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(str);
        } catch (Exception e) {
            throw new InvalidParamException("参数缺失或格式错误:" + key);
        }
    }

    public Date getDate(String key) throws DMException {
        return getDate(key, "yyyy-MM-dd");
    }

    public boolean getBoolean(String key) {
        return "true".equals(httpServletRequest.getParameter(key));
    }
}
