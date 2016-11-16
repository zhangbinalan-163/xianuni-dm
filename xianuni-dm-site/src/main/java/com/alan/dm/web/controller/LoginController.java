package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.SessionUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Person;
import com.alan.dm.service.IAdminService;
import com.alan.dm.service.IOrgnizationService;
import com.alan.dm.service.IPersonService;
import com.alan.dm.web.Constants;
import com.alan.dm.web.util.IPUtils;
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

    @Resource(name = "personService")
    private IPersonService personService;

    @Resource(name = "orgnizationService")
    private IOrgnizationService orgnizationService;

    /**
     * 是不是党支部
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/notZhibuOrg.do")
    @ResponseBody
    public String notZhibuOrg(HttpServletRequest httpServletRequest) throws Exception {
        Integer adminId = getOnlineAdminId(httpServletRequest);
        Admin adminInfo = adminService.getById(adminId);
        if(adminInfo!=null){
            if(adminInfo.getType()==Admin.SYSTEM_ADMIN) {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("success",true);
                return JsonUtils.fromObject(jsonObject);
            }else{
                Orgnization org = orgnizationService.getOrgById(adminInfo.getOrgId());
                if(org!=null){
                    if(orgnizationService.countSubOrg(org)==0){
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("success",false);
                        return JsonUtils.fromObject(jsonObject);
                    }else{
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("success",true);
                        return JsonUtils.fromObject(jsonObject);
                    }
                }
            }
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success",false);
        return JsonUtils.fromObject(jsonObject);
    }

    /**
     * 获取当前用户信息
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws Exception
     */
    @RequestMapping("/userInfo.do")
    @ResponseBody
    public String userInfo(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
        Integer adminId = getOnlineAdminId(httpServletRequest);
        Admin adminInfo = adminService.getById(adminId);
        if(adminInfo!=null){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",true);
            jsonObject.put("adminType", adminInfo.getType() == Admin.ORG_ADMIN ? "部门管理员" : "系统管理员");
            jsonObject.put("adminNumber",adminInfo.getSchoolNumber());
            return JsonUtils.fromObject(jsonObject);
        }else{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",false);
            return JsonUtils.fromObject(jsonObject);
        }
    }

    /**
     * 管理员登录接口
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
        boolean isAdmin=request.getString("admin","false").equals("on");
        if(isAdmin){
            Admin admin = adminService.getBySchoolNumber(number);
            if(admin==null){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("success",false);
                jsonObject.put("msg","用户不存在:"+number);
                return JsonUtils.fromObject(jsonObject);
            }else{
                if(!password.equals(admin.getPassword())&& !StringUtils.md5(password).equals(admin.getPassword())){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("success",false);
                    jsonObject.put("msg","用户名或密码错误");
                    return JsonUtils.fromObject(jsonObject);
                }
            }
            //设置COOKIE和session
            String cookie=SessionUtils.generateCookie(admin.getId(), IPUtils.getRequestIp(httpServletRequest),System.currentTimeMillis(),10*60*60*1000L);
            Cookie cookieInfo = new Cookie(Constants.COOKIE_NAME,cookie);
            cookieInfo.setPath("/");
            httpServletResponse.addCookie(cookieInfo);
            //设置session
            HttpSession session=httpServletRequest.getSession();
            session.setAttribute(Constants.SESSION_ADMINID_NAME,String.valueOf(admin.getId()));
            //返回结果
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",true);
            jsonObject.put("type","admin");
            return JsonUtils.fromObject(jsonObject);
        }else{
            //学生用户
            Person person=personService.getByNumber(number);
            if(person==null){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("success",false);
                jsonObject.put("msg","用户不存在:"+number);
                return JsonUtils.fromObject(jsonObject);
            }else{
                if(!password.equals(person.getPassword())&& !StringUtils.md5(password).equals(person.getPassword())){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("success",false);
                    jsonObject.put("msg","用户名或密码错误");
                    return JsonUtils.fromObject(jsonObject);
                }
            }
            //设置COOKIE和session
            String cookie=SessionUtils.generateCookie(person.getId(), IPUtils.getRequestIp(httpServletRequest),System.currentTimeMillis(),10*60*60*1000L);
            Cookie cookieInfo = new Cookie(Constants.COOKIE_NAME_PERSON,cookie);
            cookieInfo.setPath("/");
            httpServletResponse.addCookie(cookieInfo);
            //设置session
            HttpSession session=httpServletRequest.getSession();
            session.setAttribute(Constants.SESSION_PERSONID_NAME,String.valueOf(person.getId()));
            //返回结果
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",true);
            jsonObject.put("type","person");
            return JsonUtils.fromObject(jsonObject);
        }
    }
    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
        Cookie cookieInfo = new Cookie(Constants.COOKIE_NAME, null);
        cookieInfo.setPath("/");
        cookieInfo.setMaxAge(0);
        httpServletResponse.addCookie(cookieInfo);
        Cookie cookieInfoP = new Cookie(Constants.COOKIE_NAME_PERSON, null);
        cookieInfoP.setPath("/");
        cookieInfoP.setMaxAge(0);
        httpServletResponse.addCookie(cookieInfoP);

        HttpSession session=httpServletRequest.getSession();
        session.removeAttribute(Constants.SESSION_ADMINID_NAME);
        session.removeAttribute(Constants.SESSION_PERSONID_NAME);
        return "redirect:/html/login.html";
    }
}
