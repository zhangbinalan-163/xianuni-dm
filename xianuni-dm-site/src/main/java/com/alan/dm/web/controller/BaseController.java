package com.alan.dm.web.controller;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.SessionUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.entity.CookieInfo;
import com.alan.dm.web.Constants;
import com.alan.dm.web.util.WebUtils;
import com.alan.dm.web.vo.Request;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * Created by zhangbinalan on 15/11/11.
 */
public class BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获得封装的请求
     * @param httpServletRequest
     * @return
     * @throws DMException
     */
    public Request getRequest(HttpServletRequest httpServletRequest) throws DMException {
        Request request=(Request)httpServletRequest.getAttribute(Constants.REQUEST_KEY);
        if(request==null){
            throw new DMException("请求错误");
        }
        return request;
    }

    /**
     * 获得当前登录管理员
     * 一定会存在，前面必须经过登录检查过滤器的检查
     * @return
     * @throws DMException
     */
    protected Integer getOnlinePersonId(HttpServletRequest httpServletRequest) throws DMException{
        HttpSession session=httpServletRequest.getSession();
        String userId=(String)session.getAttribute(Constants.SESSION_PERSONID_NAME);
        if(!StringUtils.isEmpty(userId)){
            return Integer.parseInt(userId);
        }else {
            String cookie = WebUtils.getCookie(httpServletRequest, Constants.SESSION_PERSONID_NAME);
            if (!StringUtils.isEmpty(cookie)) {
                CookieInfo cookieInfo = SessionUtils.parseCookie(cookie);
                if (cookie != null) {
                    if (SessionUtils.validateCookie(cookieInfo, null, null, 20 * 60 * 1000L) == 0) {
                        session.setAttribute(Constants.SESSION_PERSONID_NAME, String.valueOf(cookieInfo.getUserId()));
                        return cookieInfo.getUserId();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获得当前登录管理员
     * @return
     * @throws DMException
     */
    protected Integer getOnlineAdminId(HttpServletRequest httpServletRequest) throws DMException{
        HttpSession session=httpServletRequest.getSession();
        String userId=(String)session.getAttribute(Constants.SESSION_ADMINID_NAME);
        if(!StringUtils.isEmpty(userId)){
            return Integer.parseInt(userId);
        }else {
            String cookie = WebUtils.getCookie(httpServletRequest, Constants.COOKIE_NAME);
            if (!StringUtils.isEmpty(cookie)) {
                CookieInfo cookieInfo = SessionUtils.parseCookie(cookie);
                if (cookie != null) {
                    if (SessionUtils.validateCookie(cookieInfo, null, null, 20 * 60 * 1000L) == 0) {
                        session.setAttribute(Constants.SESSION_ADMINID_NAME, String.valueOf(cookieInfo.getUserId()));
                        return cookieInfo.getUserId();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 自定义异常统一处理
     *
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    protected String handleException(HttpServletRequest httpRequest,Exception exception) {
        //记录当前异常信息
        String uri=httpRequest.getRequestURI();
        LOGGER.error("[{}]request fail", uri, exception);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success",false);
        jsonObject.put("msg", exception.getMessage());
        return JsonUtils.fromObject(jsonObject);
    }
}
