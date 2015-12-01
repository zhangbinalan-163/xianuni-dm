package com.alan.dm.web.filter;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.SessionUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.entity.CookieInfo;
import com.alan.dm.web.Constants;
import com.alan.dm.web.util.WebUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录检查Filter
 * Created by zhangbinalan on 15/10/16.
 */
@Component(value = "adminLoginFilter")
public class LoginFilter extends HandlerInterceptorAdapter {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //先判断session里面是否有登录信息，如果没有，解析cookie
        HttpSession session=request.getSession();
        String userId=(String)session.getAttribute(Constants.SESSION_ADMINID_NAME);
        if(!StringUtils.isEmpty(userId)){
            return true;
        }else{
            String cookie= WebUtils.getCookie(request, Constants.COOKIE_NAME);
            if(!StringUtils.isEmpty(cookie)){
                CookieInfo cookieInfo = SessionUtils.parseCookie(cookie);
                if(cookie!=null){
                    if(SessionUtils.validateCookie(cookieInfo,null,null,20*60*1000L)==0){
                        //验证通过
                        session.setAttribute(Constants.SESSION_ADMINID_NAME, String.valueOf(cookieInfo.getUserId()));
                        return true;
                    }
                }
            }
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success",false);
        jsonObject.put("msg","NOTLOGIN");
        response.getWriter().write(JsonUtils.fromObject(jsonObject));
        return false;
    }
}
