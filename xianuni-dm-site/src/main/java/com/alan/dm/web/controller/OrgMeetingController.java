package com.alan.dm.web.controller;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.OrgMeetingCondition;
import com.alan.dm.entity.query.PersonCondition;
import com.alan.dm.entity.query.PersonResult;
import com.alan.dm.service.*;
import com.alan.dm.web.vo.Request;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 组织活动会议控制器
 * @Date: 2015-11-19
 * @author: fan
 */
@Controller
@RequestMapping(value = "/meeting")
public class OrgMeetingController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrgMeetingController.class);

    @Resource(name = "orgMeetingService")
    private IOrgMeetingService orgMeetingService;

    @Resource(name = "personService")
    private IPersonService personService;

    @Resource(name = "adminService")
    private IAdminService adminService;

    @Resource(name = "orgnizationService")
    private IOrgnizationService orgnizationService;
    /**
     * 修改会议
     */
    @RequestMapping("/update.do")
    @ResponseBody
    public String update(HttpServletRequest httpServletRequest) throws Exception {
        Request request=getRequest(httpServletRequest);
        Integer meetingId=request.getInt("meetingId");
        Date startTime=request.getDate("startTime", "YYYY-MM-DD hh:mm:ss");
        String location=request.getString("location");
        String theme=request.getString("theme");
        Integer shouldPersonCount=request.getInt("shouldPersonCount", 0);
        Integer realPersonCount=request.getInt("realPersonCount", 0);
        String comePerson=request.getString("comePerson", "");
        String noComePerson=request.getString("noComePerson", "");
        String zhuchiren=request.getString("zhuchiren","");
        String jiluren=request.getString("jiluren","");
        String meetContent=request.getString("meetContent", "");

        OrgMeeting orgMeeting = orgMeetingService.getById(meetingId);

        String filUrlList=request.getString("fileUrlList", null);
        String filNameList=request.getString("fileNameList",null);
        if(!StringUtils.isEmpty(filUrlList)&&!StringUtils.isEmpty(filNameList)){
            String[] fileArray=filUrlList.split(",");
            String[] fileNameArray=filNameList.split(",");
            List<com.alan.dm.entity.Resource> resources=new ArrayList<com.alan.dm.entity.Resource>();
            for(int i=0;i<fileArray.length;i++){
                if(!StringUtils.isEmpty(fileArray[i])){
                    com.alan.dm.entity.Resource resource=new com.alan.dm.entity.Resource();
                    resource.setRealName(fileNameArray[i]);
                    resource.setResourcePath(fileArray[i]);
                    resource.setCreateTime(new Date());
                    resources.add(resource);
                }
            }
            orgMeeting.setResourceList(resources);
        }

        orgMeeting.setAbsencePeople(noComePerson);
        orgMeeting.setAttendancePeople(comePerson);
        orgMeeting.setCompere(zhuchiren);
        orgMeeting.setContent(meetContent);
        orgMeeting.setCreateTime(new Date());
        orgMeeting.setStartTime(startTime);
        orgMeeting.setLocation(location);
        orgMeeting.setRealNumberOfPeople(realPersonCount);
        orgMeeting.setShouldNumberOfPeople(shouldPersonCount);
        orgMeeting.setTheme(theme);
        orgMeeting.setRecorder(jiluren);

        orgMeetingService.update(orgMeeting);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success", true);
        return JsonUtils.fromObject(jsonObject);
    }
    /**
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/info.do")
    @ResponseBody
    public String info(HttpServletRequest httpServletRequest) throws Exception {
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
        jsonObject.put("shouldNumber",orgMeeting.getShouldNumberOfPeople());
        jsonObject.put("realNumber",orgMeeting.getRealNumberOfPeople());
        jsonObject.put("content",orgMeeting.getContent());
        jsonObject.put("meetType",MeetingType.getInstance(orgMeeting.getMeetingType()).getName());
        //被邀请的人
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
     * 创建会议
     */
    @RequestMapping("/add.do")
    @ResponseBody
    public String add(HttpServletRequest httpServletRequest) throws Exception {
        Request request=getRequest(httpServletRequest);
        Integer orgId=request.getInt("orgId");
        Integer type=request.getInt("type");
        Date startTime=request.getDate("startTime", "YYYY-MM-DD hh:mm:ss");
        String location=request.getString("location");
        String theme=request.getString("theme");
        Integer shouldPersonCount=request.getInt("shouldPersonCount", 0);
        Integer realPersonCount=request.getInt("realPersonCount", 0);
        String comePerson=request.getString("comePerson", "");
        String noComePerson=request.getString("noComePerson", "");
        String zhuchiren=request.getString("zhuchiren","");
        String jiluren=request.getString("jiluren","");
        String meetContent=request.getString("meetContent", "");
        boolean sendMail=request.getInt("sendMail",1)==1;

        String[] personIdArray=request.getStringArray("personIds", ",");
        List<Person> personList=new ArrayList<Person>();

        if(personIdArray!=null){
            List<Integer> statusList=new ArrayList<Integer>();
            for(String personIdStr:personIdArray){
                if(StringUtils.isEmpty(personIdStr)){
                    continue;
                }
                int personId = Integer.parseInt(personIdStr);
                if(personId==-1){
                    //全部基础人员信息
                    statusList.add(PersonStatus.NO.getId());
                }else if(personId==-2){
                    //预备
                    statusList.add(PersonStatus.PERPARE.getId());
                }else if(personId==-3){
                    //正式
                    statusList.add(PersonStatus.NORMAL.getId());
                }else if(personId==-4){
                    //积极分子
                    statusList.add(PersonStatus.ACTIVISTS.getId());
                }else if(personId==-5){
                    //申请人
                    statusList.add(PersonStatus.APPLIER.getId());
                }else if(personId==-6){
                    //发展对象
                    statusList.add(PersonStatus.INTENTION.getId());
                }else{
                    Person person = personService.getById(personId);
                    if(person!=null&&!containPersonType(statusList,person.getStatus())){
                        personList.add(person);
                    }
                }
            }
            //再去获取全部信息
            personList.addAll(getAllPerson(orgId, true, statusList));
        }

        OrgMeeting orgMeeting=new OrgMeeting();

        String filUrlList=request.getString("fileUrlList", null);
        String filNameList=request.getString("fileNameList",null);
        if(!StringUtils.isEmpty(filUrlList)&&!StringUtils.isEmpty(filNameList)){
            String[] fileArray=filUrlList.split(",");
            String[] fileNameArray=filNameList.split(",");
            List<com.alan.dm.entity.Resource> resources=new ArrayList<com.alan.dm.entity.Resource>();
            for(int i=0;i<fileArray.length;i++){
                if(!StringUtils.isEmpty(fileArray[i])){
                    com.alan.dm.entity.Resource resource=new com.alan.dm.entity.Resource();
                    resource.setRealName(fileNameArray[i]);
                    resource.setResourcePath(fileArray[i]);
                    resource.setCreateTime(new Date());
                    resources.add(resource);
                }
            }
            orgMeeting.setResourceList(resources);
        }

        Orgnization orgnization = orgnizationService.getOrgById(orgId);
        orgMeeting.setOrgnization(orgnization);
        orgMeeting.setAbsencePeople(noComePerson);
        orgMeeting.setAttendancePeople(comePerson);
        orgMeeting.setCompere(zhuchiren);
        orgMeeting.setContent(meetContent);
        orgMeeting.setCreateTime(new Date());
        orgMeeting.setStartTime(startTime);
        orgMeeting.setLocation(location);
        orgMeeting.setMeetingType(type);
        orgMeeting.setRealNumberOfPeople(realPersonCount);
        orgMeeting.setShouldNumberOfPeople(shouldPersonCount);
        orgMeeting.setTheme(theme);
        orgMeeting.setSendMail(sendMail);
        orgMeeting.setInvitePersons(personList);
        orgMeeting.setRecorder(jiluren);
        orgMeetingService.addMeeting(orgMeeting);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success", true);
        return JsonUtils.fromObject(jsonObject);
    }
    private boolean containPersonType(List<Integer> typeList,Integer type){
        for(Integer tmp:typeList){
            if(tmp==type){
                return true;
            }
        }
        return false;
    }
    /**
     * 按照条件获取部门下的人员列表
     * @param rootOrgId
     * @param withSubOrg
     * @param statusList
     * @return
     * @throws DMException
     */
    private List<Person> getAllPerson(int rootOrgId,boolean withSubOrg,List<Integer> statusList) throws DMException {
        List<Person> personList=new ArrayList<Person>();

        Orgnization orgnization = orgnizationService.getOrgById(rootOrgId);
        PersonCondition condition=new PersonCondition();
        List<Integer> orgIdList=new ArrayList<Integer>();
        orgIdList.add(rootOrgId);
        if(withSubOrg){
            List<Orgnization> subOrgs=orgnizationService.getOrgByParent(orgnization,true);
            if(subOrgs!=null){
                for(Orgnization subOrg:subOrgs){
                    orgIdList.add(subOrg.getId());
                }
            }
        }
        condition.setOrgList(orgIdList);
        if(statusList!=null&&statusList.size()>0){
            condition.setStatus(statusList);
        }

        PersonResult result=new PersonResult();
        result.setIncludeOrgnization(false);

        personList=personService.getByCondition(condition,null,result);

        return personList;
    }
    /**
     * 党政联席会议列表
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public String list(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer orgId = request.getInt("orgId", 0);
        Integer limit = request.getInt("rows", 10);
        Integer page = request.getInt("page", 1);
        Integer type = request.getInt("type", 1);

        boolean containSubOrg=request.getBoolean("containSub", true);
        //如果没有传入orgId，设置为管理员所管理的ORG
        if(orgId==0){
            Integer adminId = getOnlineAdminId(httpServletRequest);
            Admin adminInfo = adminService.getById(adminId);
            if(adminInfo.getType()==Admin.ORG_ADMIN){
                orgId=adminInfo.getOrgId();
            }
        }
        OrgMeetingCondition condition=new OrgMeetingCondition();
        List<Integer> orgIdList=new ArrayList<Integer>();
        orgIdList.add(orgId);

        if(containSubOrg){
            Orgnization orgnization=null;
            if(orgId == 0) {
                orgnization =new Orgnization();
                orgnization.setId(-1);
            }else{
                orgnization= orgnizationService.getOrgById(orgId);
            }
            List<Orgnization> subOrgList = orgnizationService.getOrgByParent(orgnization, true);
            if(subOrgList!=null){
                for (Orgnization subOrg:subOrgList){
                    orgIdList.add(subOrg.getId());
                }
            }
            condition.setOrgList(orgIdList);
        }

        Page pageInfo=new Page();
        pageInfo.setCurrent((page - 1) * limit);
        pageInfo.setSize(limit);
        condition.setTypeList(Arrays.asList(type));

        int count = orgMeetingService.countByCondition(condition);
        List<OrgMeeting> meetingList=orgMeetingService.getByCondition(condition,pageInfo);

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
                Orgnization orgInfo = orgMeeting.getOrgnization();
                cellList.add(orgInfo.getName());
                cellList.add(orgMeeting.getTheme());
                cellList.add(TimeUtils.convertToTimeString(orgMeeting.getStartTime()));
                cellList.add(orgMeeting.getShouldNumberOfPeople()+"人");
                cellList.add(orgMeeting.getRealNumberOfPeople()+"人");
                subOrgObject.put("cell", cellList);
                rowsArray.add(subOrgObject);
            }
        }
        jsonObject.put("rows",rowsArray);
        return JsonUtils.fromObject(jsonObject);
    }

    /**
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete.do")
    @ResponseBody
    public String applierDelete(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        String[] ids=request.getStringArray("id", ",");
        for(String id:ids){
            OrgMeeting meeting=orgMeetingService.getById(Integer.parseInt(id));
            if(meeting!=null){
               orgMeetingService.delete(meeting);
            }
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status",true);
        return JsonUtils.fromObject(jsonObject);
    }
}
