package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.OrgMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgMeetingCondition;
import com.alan.dm.service.IOrgMeetingService;
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

    @RequestMapping("/addOrUpdate.do")
    @ResponseBody
    public String addOrUpdate(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);

        // todo request parser
        int orgMeetingId = request.getInt("id", 0);
        try {
            OrgMeeting orgMeeting = new OrgMeeting();
            //  todo set
            if(orgMeetingId == 0) {
                orgMeetingId = orgMeetingService.addMeeting(orgMeeting);
            } else {
                orgMeetingService.update(orgMeeting);
            }
        } catch (Exception e) {
            LOGGER.error("insert org meeting fail");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("status", false);
            jsonObject.put("msg","新增或更新失败");
            return JsonUtils.fromObject(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", true);
        jsonObject.put("orgMeetingId", orgMeetingId);
        return JsonUtils.fromObject(jsonObject);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public String delete(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        int id = request.getInt("id");
        try{
            if(id != 0) {
                OrgMeeting orgMeeting = new OrgMeeting();
                orgMeeting.setId(id);
                orgMeetingService.delete(orgMeeting);
            }
        }catch (Exception e){
            LOGGER.error("delete meeting fail,id={}", id);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("status", false);
            jsonObject.put("msg","删除失败");
            return JsonUtils.fromObject(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status",true);
        return JsonUtils.fromObject(jsonObject);
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public String meetingList(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer limit = request.getInt("rows", 10);
        Integer page = request.getInt("page", 1);
        Integer orgId=request.getInt("orgId",0);
        String theme=request.getString("theme", null);
        Integer activity = request.getInt("activity", 1);

        Page pageInfo=new Page();
        pageInfo.setCurrent((page-1)*limit);
        pageInfo.setSize(limit);
        OrgMeetingCondition condition=new OrgMeetingCondition();
        if(orgId!=0){
            condition.setOrganizationId(orgId);
        }
        if(!StringUtils.isEmpty(theme)){
            condition.setTheme(theme);
        }
        condition.setActivityType(activity);
        int subCount = orgMeetingService.countByCondition(condition);

        List<OrgMeeting> meetingList=orgMeetingService.getByCondition(condition,pageInfo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("page",page);
        jsonObject.put("total",subCount==0?0:subCount/limit+1);
        jsonObject.put("meetings",subCount);
        JSONArray rowsArray=new JSONArray();
        if(meetingList!=null){
            for(OrgMeeting meeting:meetingList){
                JSONObject subOrgObject=new JSONObject();
                subOrgObject.put("id", meeting.getId());
                subOrgObject.put("meetingType", meeting.getMeetingType());
                subOrgObject.put("startTime", TimeUtils.convertToDateString(meeting.getStartTime()));
                subOrgObject.put("endTime", TimeUtils.convertToDateString(meeting.getEndTime()));
                subOrgObject.put("location", meeting.getLocation());
                subOrgObject.put("theme", meeting.getTheme());
                subOrgObject.put("compere", meeting.getCompere().getName());
                subOrgObject.put("shouldNumberOfPeople", meeting.getShouldNumberOfPeople());
                subOrgObject.put("realNumberOfPeople", meeting.getRealNumberOfPeople());
                subOrgObject.put("content", meeting.getContent());
                subOrgObject.put("filePath", meeting.getFilePath());
                subOrgObject.put("attendancePeople", meeting.getAttendancePeople());
                subOrgObject.put("absencePeople", meeting.getAbsencePeople());
                rowsArray.add(subOrgObject);
            }
        }
        jsonObject.put("rows",rowsArray);
        return JsonUtils.fromObject(jsonObject);
    }
}
