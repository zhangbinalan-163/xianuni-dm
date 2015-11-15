package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.SessionUtils;
import com.alan.dm.entity.Admin;
import com.alan.dm.service.IAdminService;
import com.alan.dm.web.Constants;
import com.alan.dm.web.util.IPUtils;
import com.alan.dm.web.util.WebUtils;
import com.alan.dm.web.vo.Request;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 * Created by zhangbinalan on 15/11/15.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
    private static Logger LOG= LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "adminService")
    private IAdminService adminService;

    /**
     * 管理员登录接口
     * TODO 改成异步调用
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws Exception
     */
    @RequestMapping("/user.do")
    @ResponseBody
    public String userLogin(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
        Request request = getRequest(httpServletRequest);
        String number=request.getString("number");
        String password=request.getString("password");
        boolean isAdmin=request.getBoolean("admin");
        if(isAdmin){
            Admin admin = adminService.getBySchoolNumber(number);
            if(admin==null){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("success",false);
                return JsonUtils.fromObject(jsonObject);
            }else{
                if(!password.equals(admin.getPassword())){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("success",false);
                    return JsonUtils.fromObject(jsonObject);
                }
            }
            //设置COOKIE和session
            String cookie=SessionUtils.generateCookie(admin.getId(), IPUtils.getRequestIp(httpServletRequest),System.currentTimeMillis(),2*60*60*1000L);
            Cookie cookieInfo = new Cookie(Constants.COOKIE_NAME,cookie);
            cookieInfo.setPath("/");
            httpServletResponse.addCookie(cookieInfo);
            //设置session
            HttpSession session=httpServletRequest.getSession();
            session.setAttribute(Constants.SESSION_USERID_NAME,String.valueOf(admin.getId()));
            //返回结果
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",true);
            return JsonUtils.fromObject(jsonObject);
        }else{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",false);
            return JsonUtils.fromObject(jsonObject);
        }
    }
    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
        Cookie cookieInfo = new Cookie(Constants.COOKIE_NAME, null);
        cookieInfo.setPath("/");
        cookieInfo.setMaxAge(0);
        httpServletResponse.addCookie(cookieInfo);
        //设置session
        HttpSession session=httpServletRequest.getSession();
        session.removeAttribute(Constants.SESSION_USERID_NAME);
        return "redirect:/html/login.html";
    }
}