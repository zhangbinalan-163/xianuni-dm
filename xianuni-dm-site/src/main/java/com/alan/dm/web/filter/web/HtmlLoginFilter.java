package com.alan.dm.web.filter.web;


import com.alan.dm.common.util.SessionUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.entity.CookieInfo;
import com.alan.dm.web.Constants;
import com.alan.dm.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录检查Filter
 *
 * Created by zhangbinalan on 15/10/16.
 */
public class HtmlLoginFilter implements Filter {
    private static Logger LOG= LoggerFactory.getLogger(HtmlLoginFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        HttpServletResponse httpServletResponse=(HttpServletResponse)response;
        //先判断session里面是否有登录信息，如果没有，解析cookie
        HttpSession session=httpServletRequest.getSession();
        String userId=(String)session.getAttribute(Constants.SESSION_USERID_NAME);
        if(!StringUtils.isEmpty(userId)){
            chain.doFilter(request,response);
        }else{
            String cookie=WebUtils.getCookie(httpServletRequest, Constants.COOKIE_NAME);
            if(!StringUtils.isEmpty(cookie)){
                CookieInfo cookieInfo = SessionUtils.parseCookie(cookie);
                if(cookie!=null){
                    if(SessionUtils.validateCookie(cookieInfo,null,null,5*60*1000L)==0){
                        //验证通过
                        session.setAttribute(Constants.SESSION_USERID_NAME, String.valueOf(cookieInfo.getUserId()));
                        chain.doFilter(request,response);
                        return;
                    }else{
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/static/html/login.html");
                    }
                }else{
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/static/html/login.html");
                }
            }else{
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/static/html/login.html");
            }
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
