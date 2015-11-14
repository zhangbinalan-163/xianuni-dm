package com.alan.dm.web.controller;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.web.Constants;
import com.alan.dm.web.vo.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
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
}
