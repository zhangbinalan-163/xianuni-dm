package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.Resource;
import com.alan.dm.entity.condition.EduTrainingCondition;
import com.alan.dm.entity.condition.MessageCondition;
import com.alan.dm.entity.condition.OrgMeetingCondition;
import com.alan.dm.service.*;
import com.alan.dm.web.vo.Request;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
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

    @javax.annotation.Resource(name = "personService")
    private IPersonService personService;

    @javax.annotation.Resource(name = "mailService")
    private MailService mailService;

    @Autowired
    private EduTrainingLearnService eduTrainingLearnService;

    @Autowired
    private IOrgMeetingService orgMeetingService;

    @javax.annotation.Resource(name = "eduTrainingService")
    private IEduTrainingService eduTrainingService;

    @javax.annotation.Resource(name = "applierService")
    private IApplierService applierService;

    @javax.annotation.Resource(name = "activitistService")
    private IActivitistService activitistService;

    @javax.annotation.Resource(name = "intentionService")
    private IIntentionService intentionService;

    @javax.annotation.Resource(name = "prepareService")
    private IPrepareService prepareService;

    @javax.annotation.Resource(name = "normalService")
    private INormalService normalService;

    @javax.annotation.Resource(name = "orgnizationService")
    private IOrgnizationService orgnizationService;

    @javax.annotation.Resource(name = "messageService")
    private IMessageService messageService;

    @javax.annotation.Resource(name = "adminService")
    private IAdminService adminService;

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
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/meeting/info.do")
    @ResponseBody
    public String meetingInfo(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        int meetingId = request.getInt("id");

        OrgMeeting orgMeeting=orgMeetingService.getById(meetingId);
        JSONObject jsonObject=new JSONObject();
        if(orgMeeting==null){
            jsonObject.put("success", false);
            jsonObject.put("msg","不存在该记录");
            return JsonUtils.fromObject(jsonObject);
        }

        jsonObject.put("success", true);
        Orgnization orgnization=orgnizationService.getOrgById(orgMeeting.getOrgId());
        jsonObject.put("orgName",orgnization.getName());
        jsonObject.put("location",orgMeeting.getLocation());
        jsonObject.put("theme",orgMeeting.getTheme());
        jsonObject.put("compere", orgMeeting.getCompere());
        jsonObject.put("recorder", orgMeeting.getRecorder());
        jsonObject.put("content",orgMeeting.getContent());
        jsonObject.put("meetType",MeetingType.getInstance(orgMeeting.getMeetingType()).getName());
        jsonObject.put("absencePerson",orgMeeting.getAbsencePeople());
        jsonObject.put("attendPerson",orgMeeting.getAttendancePeople());
        jsonObject.put("starttime",TimeUtils.convertToTimeString(orgMeeting.getStartTime()));

        List<com.alan.dm.entity.Resource> resourceList = orgMeeting.getResourceList();
        JSONArray jsonArray=new JSONArray();
        if(resourceList!=null){
            for(com.alan.dm.entity.Resource resource:resourceList){
                JSONObject fileObj=new JSONObject();
                fileObj.put("name",resource.getRealName());
                fileObj.put("url",request.getHttpServletRequest().getContextPath()+resource.getResourcePath());
                jsonArray.add(fileObj);
            }
        }
        jsonObject.put("fileUrlList",jsonArray);
        return JsonUtils.fromObject(jsonObject);
    }
    /**
     * 获得培训信息
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/train/info.do")
    @ResponseBody
    public String trainInfo(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        int trainId=request.getInt("id");
        EduTraining trainInfo = eduTrainingService.getById(trainId);
        JSONObject jsonObject=new JSONObject();
        if(trainInfo!=null){
            jsonObject.put("success",true);
            jsonObject.put("title",trainInfo.getTitle());
            Orgnization orgnization = orgnizationService.getOrgById(trainInfo.getOrgId());
            jsonObject.put("orgName",orgnization.getName());
            jsonObject.put("content",trainInfo.getContent());
            jsonObject.put("createTime", TimeUtils.convertToTimeString(trainInfo.getCreateTime()));
            jsonObject.put("endTime",TimeUtils.convertToTimeString(trainInfo.getEndTime()));
            jsonObject.put("startTime", TimeUtils.convertToTimeString(trainInfo.getStartTime()));
            JSONArray jsonArray=new JSONArray();
            List<Resource> resourceList = trainInfo.getResourceList();
            if(resourceList!=null){
                for(Resource resource:resourceList){
                    JSONObject fileObj=new JSONObject();
                    fileObj.put("name",resource.getRealName());
                    fileObj.put("url",request.getHttpServletRequest().getContextPath()+resource.getResourcePath());
                    jsonArray.add(fileObj);
                }
            }
            jsonObject.put("fileUrlList", jsonArray);
            Person person=new Person();
            person.setId(getOnlinePersonId(httpServletRequest));
            TrainLearn trainLearn = eduTrainingLearnService.getByPersonWithTrain(person, trainInfo);
            if(trainLearn!=null){
                jsonObject.put("myLearn", trainLearn.getLearn());
            }
            return JsonUtils.fromObject(jsonObject);
        }
        jsonObject.put("success",true);
        return JsonUtils.fromObject(jsonObject);
    }
    /**
     * 新增培训心得
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/train/addLearn.do")
    @ResponseBody
    public String trainAddLearn(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer trainId = request.getInt("trainId");
        String learnByTrain=request.getString("learnByTrain");
        Integer personId = getOnlinePersonId(httpServletRequest);

        Person person = personService.getById(personId);
        EduTraining trainInfo = eduTrainingService.getById(trainId);
        TrainLearn trainLearnInfo = eduTrainingLearnService.getByPersonWithTrain(person, trainInfo);
        if(trainLearnInfo!=null){
            trainLearnInfo.setLearn(learnByTrain);
            trainLearnInfo.setUpdateTime(new Date());
            eduTrainingLearnService.updateLearn(trainLearnInfo);

        }else{
            TrainLearn trainLearn=new TrainLearn();
            trainLearn.setPerson(person);
            trainLearn.setTraining(trainInfo);
            trainLearn.setLearn(learnByTrain);
            trainLearn.setCreateTime(new Date());

            eduTrainingLearnService.createLearn(trainLearn);
        }
        //
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success",true);
        return JsonUtils.fromObject(jsonObject);
    }
    /**
     * 分页获取培训信息
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/train/list.do")
    @ResponseBody
    public String trainList(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer limit = request.getInt("rows", 10);
        Integer page = request.getInt("page", 1);

        Integer personId = getOnlinePersonId(httpServletRequest);
        Person person=personService.getById(personId);
        Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());

        List<Integer> orgIdList=new ArrayList<Integer>();
        orgIdList.add(person.getOrgId());
        List<Orgnization> parentOrgList=orgnizationService.getParentOrg(orgnization);
        if(parentOrgList!=null){
            for(Orgnization parentOrg:parentOrgList){
                orgIdList.add(parentOrg.getId());
            }
        }

        Page pageInfo=new Page();
        pageInfo.setCurrent((page - 1) * limit);
        pageInfo.setSize(limit);

        EduTrainingCondition condition=new EduTrainingCondition();
        condition.setOrgList(orgIdList);

        int count = eduTrainingService.countTrainByCondtion(condition);
        List<EduTraining> trainingList=eduTrainingService.getTrainingByCondtion(condition, pageInfo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("page", page);
        jsonObject.put("total", count == 0 ? 0 : count / limit + 1);
        jsonObject.put("records", count);

        JSONArray rowsArray = new JSONArray();
        if (trainingList!=null){
            for (EduTraining eduTraining : trainingList) {
                JSONObject subOrgObject = new JSONObject();
                subOrgObject.put("id", eduTraining.getId());
                List<String> cellList=new ArrayList<String>();
                cellList.add(String.valueOf(eduTraining.getId()));
                cellList.add(eduTraining.getOrganization().getName());
                cellList.add(eduTraining.getTitle());
                cellList.add(TimeUtils.convertToTimeString(eduTraining.getStartTime()));
                cellList.add(TimeUtils.convertToTimeString(eduTraining.getEndTime()));
                subOrgObject.put("cell", cellList);
                rowsArray.add(subOrgObject);
            }
        }
        jsonObject.put("rows",rowsArray);
        return JsonUtils.fromObject(jsonObject);
    }
    /**
     * 分页获取会议信息
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/meeting/list.do")
    @ResponseBody
    public String meetingList(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer limit = request.getInt("rows", 10);
        Integer page = request.getInt("page", 1);

        Integer personId = getOnlinePersonId(httpServletRequest);
        Person person=personService.getById(personId);
        Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());

        List<Integer> orgIdList=new ArrayList<Integer>();
        orgIdList.add(person.getOrgId());
        List<Orgnization> parentOrgList=orgnizationService.getParentOrg(orgnization);
        if(parentOrgList!=null){
            for(Orgnization parentOrg:parentOrgList){
                orgIdList.add(parentOrg.getId());
            }
        }

        Page pageInfo=new Page();
        pageInfo.setCurrent((page - 1) * limit);
        pageInfo.setSize(limit);

        OrgMeetingCondition condition=new OrgMeetingCondition();
        condition.setOrgList(orgIdList);
        condition.setTypeList(
                Arrays.asList(MeetingType.MZPY.getId(), MeetingType.DK.getId(), MeetingType.DXZH.getId(), MeetingType.ZBDH.getId(), MeetingType.DZBWYH.getId())
        );
        int count = orgMeetingService.countByCondition(condition);
        List<OrgMeeting> meetingList=orgMeetingService.getByCondition(condition, pageInfo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("page", page);
        jsonObject.put("total", count == 0 ? 0 : count / limit + 1);
        jsonObject.put("records", count);

        JSONArray rowsArray = new JSONArray();
        if (meetingList!=null){
            for (OrgMeeting orgMeeting : meetingList) {
                JSONObject subOrgObject = new JSONObject();
                subOrgObject.put("id", orgMeeting.getId());
                List<String> cellList=new ArrayList<String>();
                cellList.add(String.valueOf(orgMeeting.getId()));
                cellList.add(orgMeeting.getOrgnization().getName());
                cellList.add(orgMeeting.getTheme());
                cellList.add(TimeUtils.convertToTimeString(orgMeeting.getStartTime()));
                subOrgObject.put("cell", cellList);
                rowsArray.add(subOrgObject);
            }
        }
        jsonObject.put("rows",rowsArray);
        return JsonUtils.fromObject(jsonObject);
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
    @RequestMapping("/message/info.do")
    @ResponseBody
    public String messageInfo(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer id = request.getInt("id");
        Message mailInfo = messageService.getById(id);
        if(mailInfo!=null){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success", true);
            jsonObject.put("time", TimeUtils.convertToTimeString(mailInfo.getCreateTime()));
            jsonObject.put("title", mailInfo.getTitle());
            jsonObject.put("content", mailInfo.getContent());
            jsonObject.put("subShow",mailInfo.isToSub() ? "是" : "否");
            return JsonUtils.fromObject(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success", false);
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
            jsonObject.put("createTime",TimeUtils.convertToDateString(person.getCreateTime()));
            jsonObject.put("status",PersonStatus.getInstance(person.getStatus()).getName());
            //todo
            ApplierInfo applierInfo=applierService.getById(person.getApplierInfoId());
            if(applierInfo!=null){
                jsonObject.put("submitApplyTime",TimeUtils.convertToDateString(applierInfo.getCreateTime()));
                String talkerIds=applierInfo.getTalkerIds();
                if(!StringUtils.isEmpty(talkerIds)){
                    StringBuffer sb=new StringBuffer();
                    String[] talkerIdArray = talkerIds.split(",");
                    for(String talkerId:talkerIdArray){
                        Person director = personService.getById(Integer.parseInt(talkerId));
                        if(director!=null){
                            sb.append(director.getName()).append(", ");
                        }
                    }
                    String talkerName=sb.toString();
                    talkerName=talkerName.substring(0,talkerName.lastIndexOf(","));
                    jsonObject.put("talker",talkerName);
                }
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
    /**
     * 展现给个人的信息列表
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/message/all.do")
    @ResponseBody
    public String personListMessageAll(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer limit = request.getInt("rows", 10);
        Integer page = request.getInt("page", 1);
        //获取当前登录账号、或者管理员的ID
        int personId=getOnlinePersonId(httpServletRequest);
        Person person=personService.getById(personId);
        Orgnization orgnization = orgnizationService.getOrgById(person.getOrgId());
        List<Orgnization> parentOrgList=orgnizationService.getParentOrg(orgnization);

        MessageCondition condition=new MessageCondition();
        if(parentOrgList!=null&&parentOrgList.size()>0){
            List<Integer> orgIdList=new ArrayList<Integer>();
            for(Orgnization parentOrg:parentOrgList){
                orgIdList.add(parentOrg.getId());
            }
            condition.setOrgList(orgIdList);
        }
        condition.setOrgId(orgnization.getId());
        condition.setPersonQuery(true);

        Page pageInfo=new Page();
        pageInfo.setCurrent((page - 1) * limit);
        pageInfo.setSize(limit);

        int count=messageService.countMessageByCondtition(condition);
        List<Message> messageList = messageService.getMessageByCondtition(condition, pageInfo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("page",page);
        jsonObject.put("total",count==0?0:count/limit+1);
        jsonObject.put("records",count);
        JSONArray rowsArray=new JSONArray();

        if(messageList!=null){
            for(Message message:messageList){
                JSONObject subOrgObject=new JSONObject();
                subOrgObject.put("id", message.getId());
                List<String> cellList=new ArrayList<String>();
                cellList.add(String.valueOf(message.getId()));
                cellList.add(message.getOrgnization().getName());
                cellList.add(message.getTitle());
                cellList.add(TimeUtils.convertToDateString(message.getCreateTime()));
                subOrgObject.put("cell", cellList);
                rowsArray.add(subOrgObject);
            }
        }
        jsonObject.put("rows",rowsArray);
        return JsonUtils.fromObject(jsonObject);
    }
    /**
     * 展现给个人的信息列表
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/mail/all.do")
    @ResponseBody
    public String personListMailAll(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer limit = request.getInt("rows", 10);
        Integer page = request.getInt("page", 1);
        Page pageInfo=new Page();
        pageInfo.setCurrent((page - 1) * limit);
        pageInfo.setSize(limit);

        //获取当前登录账号、或者管理员的ID
        int personId=getOnlinePersonId(httpServletRequest);
        Person person=personService.getById(personId);

        int count=mailService.countByPerson(person);
        List<MailInfo> mailList = mailService.getByPerson(person, pageInfo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("page", page);
        jsonObject.put("total", count == 0 ? 0 : count / limit + 1);
        jsonObject.put("records",count);
        JSONArray rowsArray=new JSONArray();

        if(mailList!=null){
            for(MailInfo message:mailList){
                JSONObject subOrgObject=new JSONObject();
                subOrgObject.put("id", message.getId());
                List<String> cellList=new ArrayList<String>();
                cellList.add(String.valueOf(message.getId()));
                cellList.add(message.getTitle());
                cellList.add(TimeUtils.convertToDateString(message.getCreateTime()));
                subOrgObject.put("cell", cellList);
                rowsArray.add(subOrgObject);
            }
        }
        jsonObject.put("rows",rowsArray);
        return JsonUtils.fromObject(jsonObject);
    }
    /**
     * 展现给个人的信息列表
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/message/list.do")
    @ResponseBody
    public String personListMail(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);

        Page pageInfo=new Page();
        pageInfo.setCurrent(0);
        pageInfo.setSize(20);

        int personId=getOnlinePersonId(httpServletRequest);
        if(personId==0){
            int adminId=getOnlineAdminId(httpServletRequest);
            Admin adminInfo = adminService.getById(adminId);
            String adminNumber = adminInfo.getSchoolNumber();
            Person personInfo = personService.getByNumber(adminNumber);
            personId=personInfo.getId();
        }
        Person person=personService.getById(personId);
        Orgnization orgnization = orgnizationService.getOrgById(person.getOrgId());
        List<Orgnization> parentOrgList=orgnizationService.getParentOrg(orgnization);

        MessageCondition condition=new MessageCondition();
        if(parentOrgList!=null&&parentOrgList.size()>0){
            List<Integer> orgIdList=new ArrayList<Integer>();
            for(Orgnization parentOrg:parentOrgList){
                orgIdList.add(parentOrg.getId());
            }
            condition.setOrgList(orgIdList);
        }
        condition.setOrgId(orgnization.getId());
        condition.setPersonQuery(true);

        List<Message> messageList = messageService.getMessageByCondtition(condition, pageInfo);

        JSONObject jsonObject=new JSONObject();
        JSONArray dataArray=new JSONArray();
        if(messageList!=null){
            for(Message mailInfo:messageList){
                JSONObject itemObj=new JSONObject();
                itemObj.put("id",mailInfo.getId());
                itemObj.put("time", TimeUtils.convertToTimeString(mailInfo.getCreateTime()));
                itemObj.put("title",mailInfo.getTitle());
                dataArray.add(itemObj);
            }
        }
        jsonObject.put("list",dataArray);
        return JsonUtils.fromObject(jsonObject);
    }
}
