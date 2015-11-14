package com.alan.dm.web.filter;

import com.alan.dm.web.Constants;
import com.alan.dm.web.vo.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求解析的Filter<br/>
 * 封装请求设置到当前请求环境中
 * Created by zhangbinalan on 15/10/16.
 */
public class RequestParseFilter extends HandlerInterceptorAdapter {

    private static Logger LOGGER = LoggerFactory.getLogger(RequestParseFilter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(LOGGER.isTraceEnabled()){
            LOGGER.trace("request uri={} into RequestParseFilter",request.getRequestURI());
        }
        Request requestVO=new Request();
        requestVO.setHttpServletRequest(request);
        requestVO.setHttpServletResponse(response);
        //封装的请求VO设置到request中
        request.setAttribute(Constants.REQUEST_KEY,requestVO);
        return true;
    }
}
