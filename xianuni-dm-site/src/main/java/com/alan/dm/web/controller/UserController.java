package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.SessionUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.service.*;
import com.alan.dm.web.Constants;
import com.alan.dm.web.util.IPUtils;
import com.alan.dm.web.vo.Request;
import com.alibaba.fastjson.JSONArray;
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
import java.util.Date;
import java.util.List;

/**
 * 个人用户接口控制器
 * Created by zhangbinalan on 15/11/15.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    private static Logger LOG= LoggerFactory.getLogger(UserController.class);

    @Resource(name = "personService")
    private IPersonService personService;

    @Resource(name = "mailService")
    private MailService mailService;

    @Resource(name = "applierService")
    private IApplierService applierService;

    @Resource(name = "activitistService")
    private IActivitistService activitistService;

    @Resource(name = "intentionService")
    private IIntentionService intentionService;

    @Resource(name = "prepareService")
    private IPrepareService prepareService;

    @Resource(name = "normalService")
    private INormalService normalService;

    @Resource(name = "orgnizationService")
    private IOrgnizationService orgnizationService;
    /**
     * 获取当前用户信息
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/index/name.do")
    @ResponseBody
    public String userInfo(HttpServletRequest httpServletRequest) throws Exception {
        Integer personId = getOnlinePersonId(httpServletRequest);
        //personId一定不空
        Person personInfo = personService.getById(personId);
        if(personInfo!=null){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",true);
            jsonObject.put("name",personInfo.getName());
            return JsonUtils.fromObject(jsonObject);
        }else{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",false);
            return JsonUtils.fromObject(jsonObject);
        }
    }

    /**
     * 首页站内信列表
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/index/mail.do")
    @ResponseBody
    public String indexMailList(HttpServletRequest httpServletRequest) throws Exception {
        return null;
    }

    /**
     * 分页获取站内信
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/mail/list.do")
    @ResponseBody
    public String innnerMailList(HttpServletRequest httpServletRequest) throws Exception {
        Integer personId = getOnlinePersonId(httpServletRequest);
        Person person=new Person();
        person.setId(personId);

        Page page=new Page();
        page.setCurrent(0);
        page.setSize(15);

        List<MailInfo> mailList = mailService.getByPerson(person, page);
        JSONObject jsonObject=new JSONObject();
        JSONArray dataArray=new JSONArray();
        if(mailList!=null){
            for(MailInfo mailInfo:mailList){
                JSONObject itemObj=new JSONObject();
                itemObj.put("id",mailInfo.getId());
                itemObj.put("time", TimeUtils.convertToTimeString(mailInfo.getCreateTime()));
                itemObj.put("title",mailInfo.getTitle());
                itemObj.put("readed",mailInfo.isReaded()?"已读":"未读");
                dataArray.add(itemObj);
            }
        }
        jsonObject.put("list",dataArray);
        return JsonUtils.fromObject(jsonObject);
    }

    /**
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/innermail/info.do")
    @ResponseBody
    public String innnerMailInfo(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer id=request.getInt("mailId");
        MailInfo mailInfo=mailService.getById(id);
        JSONObject itemObj=new JSONObject();
        if(mailInfo!=null){
            itemObj.put("time", TimeUtils.convertToTimeString(mailInfo.getCreateTime()));
            itemObj.put("title",mailInfo.getTitle());
            itemObj.put("content",mailInfo.getContent());
        }
        return JsonUtils.fromObject(itemObj);
    }
    /**
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/passupdate.do")
    @ResponseBody
    public String updatePass(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        String oldPass=request.getString("oldPass");
        String newPass=request.getString("newPass");
        String newPassAgain=request.getString("newPassAgain");

        if(!newPassAgain.equals(newPass)){
            JSONObject itemObj=new JSONObject();
            itemObj.put("success", false);
            itemObj.put("msg","新密码两次不一致");
            return JsonUtils.fromObject(itemObj);
        }
        Integer personId = getOnlinePersonId(httpServletRequest);
        Person person=personService.getById(personId);
        if(person!=null){
            if(!oldPass.equals(person.getPassword())){
                JSONObject itemObj=new JSONObject();
                itemObj.put("success", false);
                itemObj.put("msg","旧密码错误");
                return JsonUtils.fromObject(itemObj);
            }else{
                person.setPassword(newPass);
                personService.updatePassword(person);
                JSONObject itemObj=new JSONObject();
                itemObj.put("success", true);
                itemObj.put("msg","success");
                return JsonUtils.fromObject(itemObj);
            }
        }else{
            JSONObject itemObj=new JSONObject();
            itemObj.put("success", false);
            itemObj.put("msg","请重新登录");
            return JsonUtils.fromObject(itemObj);
        }
    }
    /**
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/info/all.do")
    @ResponseBody
    public String userAllInfo(HttpServletRequest httpServletRequest) throws Exception {
        Integer personId = getOnlinePersonId(httpServletRequest);
        Person person=personService.getById(personId);
        if(person!=null){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",true);
            Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
            jsonObject.put("name",person.getName());
            jsonObject.put("number",person.getNumber());
            jsonObject.put("orgName",orgnization.getName());
            jsonObject.put("idNumber",person.getIdNumber());
            jsonObject.put("createTime",person.getCreateTime());
            jsonObject.put("status",PersonStatus.getInstance(person.getStatus()).getName());
            //todo
            ApplierInfo applierInfo=applierService.getById(person.getApplierInfoId());
            if(applierInfo!=null){
                jsonObject.put("submitApplyTime",TimeUtils.convertToDateString(applierInfo.getCreateTime()));
                Person talker=personService.getById(applierInfo.getTalkerId());
                jsonObject.put("talker",talker.getName());
                jsonObject.put("talkContent",applierInfo.getTalkContent());
                jsonObject.put("talkTime",TimeUtils.convertToDateString(applierInfo.getTalkTime()));
                jsonObject.put("beApplierTime",TimeUtils.convertToDateString(applierInfo.getCreateTime()));
                //todo
            }
            ActivitistInfo activitistInfo=activitistService.getById(person.getActivitistInfoId());
            if(activitistInfo!=null){
                jsonObject.put("beActivitistTime",TimeUtils.convertToDateString(activitistInfo.getCreateTime()));
                //todo
            }
            IntentionInfo intentionInfo=intentionService.getById(person.getIntentionInfoId());
            if(intentionInfo!=null){
                jsonObject.put("beIntentionTime",TimeUtils.convertToDateString(intentionInfo.getCreateTime()));
                //todo
            }
            PrepareInfo prepareInfo=prepareService.getById(person.getPrepareInfoId());
            if(prepareInfo!=null){
                jsonObject.put("bePrepareTime",TimeUtils.convertToDateString(prepareInfo.getCreateTime()));
                //todo
            }
            NormalInfo normalInfo=normalService.getById(person.getNormalInfoId());
            if(normalInfo!=null){
                jsonObject.put("beNormalTime",TimeUtils.convertToDateString(normalInfo.getCreateTime()));
                //todo
            }
            return JsonUtils.fromObject(jsonObject);
        }else{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",false);
            return JsonUtils.fromObject(jsonObject);
        }
    }
}
