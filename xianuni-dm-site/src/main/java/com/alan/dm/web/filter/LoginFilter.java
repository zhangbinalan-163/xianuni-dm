package com.alan.dm.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查Filter
 * Created by zhangbinalan on 15/10/16.
 */
public class LoginFilter extends HandlerInterceptorAdapter {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.sendRedirect(request.getContextPath() + "login.jsp");
        return false;
    }
}
