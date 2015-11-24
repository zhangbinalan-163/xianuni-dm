package com.alan.dm.web.controller;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.SessionUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.entity.CookieInfo;
import com.alan.dm.web.Constants;
import com.alan.dm.web.util.WebUtils;
import com.alan.dm.web.vo.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
}
