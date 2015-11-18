package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.dao.IEduTrainingDao;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.EduTrainingCondition;
import com.alan.dm.entity.condition.MediaCondition;
import com.alan.dm.service.IEduTrainingService;
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
import java.util.List;

/**
 * 教育培训，专题教育控制层
 * @Date: 2015-11-19
 * @author: fan
 */
@Controller
@RequestMapping(value = "/edu")
public class EduTrainingController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EduTrainingController.class);

    @Resource(name = "eduTrainingService")
    private IEduTrainingService eduTrainingService;

    @RequestMapping("/deleteTraining.do")
    @ResponseBody
    public String deleteTraining(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        int id = request.getInt("id");

        try{
            if(id != 0) {
                EduTraining eduTraining = new EduTraining();
                eduTraining.setId(id);
                eduTrainingService.deleteTraining(eduTraining);
            }
        }catch (Exception e){
            LOGGER.error("delete edu training fail,id={}", id);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("status", false);
            jsonObject.put("msg","删除失败");
            return JsonUtils.fromObject(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status",true);
        return JsonUtils.fromObject(jsonObject);
    }

    @RequestMapping("/deleteMedia.do")
    @ResponseBody
    public String deleteMedia(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        int id = request.getInt("id");

        try{
            if(id != 0) {
                MediaResource mediaResource = new MediaResource();
                mediaResource.setId(id);
                eduTrainingService.deleteMediaResource(mediaResource);
            }
        }catch (Exception e){
            LOGGER.error("delete media resource fail,id={}", id);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("status", false);
            jsonObject.put("msg","删除失败");
            return JsonUtils.fromObject(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status",true);
        return JsonUtils.fromObject(jsonObject);
    }

    @RequestMapping("/trainingList.do")
    @ResponseBody
    public String trainingList(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer limit = request.getInt("rows", 10);
        Integer page = request.getInt("page", 1);
        Integer orgId=request.getInt("orgId", 0);
        Integer type = request.getInt("type", 1);
        String orgName = request.getString("orgName", null);
        Integer trainingType = request.getInt("trainingType", 0);

        Page pageInfo=new Page();
        pageInfo.setCurrent((page-1)*limit);
        pageInfo.setSize(limit);
        EduTrainingCondition condition=new EduTrainingCondition();
        if(orgId!=0){
            condition.setOrgId(orgId);
        }
        if(!StringUtils.isEmpty(orgName)){
            condition.setOrgName(orgName);
        }
        condition.setType(type);
        condition.setTrainingType(trainingType);
        int subCount = eduTrainingService.countTrain(condition);

        List<EduTraining> meetingList=eduTrainingService.getTraining(condition, pageInfo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("page",page);
        jsonObject.put("total",subCount==0?0:subCount/limit+1);
        jsonObject.put("trainings",subCount);
        JSONArray rowsArray=new JSONArray();
        if(meetingList!=null){
            for(EduTraining training:meetingList){
                JSONObject subOrgObject=new JSONObject();
                subOrgObject.put("id", training.getId());
                subOrgObject.put("title", training.getTitle());
                subOrgObject.put("startTime", TimeUtils.convertToDateString(training.getStartTime()));
                subOrgObject.put("endTime", TimeUtils.convertToDateString(training.getEndTime()));
                subOrgObject.put("trainingType", training.getTrainingType());
                subOrgObject.put("orgName", training.getOrganization().getName());
                subOrgObject.put("period", training.getPeriod());
                subOrgObject.put("trainingObject", training.getTrainingObject());
                subOrgObject.put("content", training.getContent());
                subOrgObject.put("personName", training.getPerson().getName());
                subOrgObject.put("harvest", training.getHarvest());
                subOrgObject.put("opinion", training.getOpinion());
                rowsArray.add(subOrgObject);
            }
        }
        jsonObject.put("rows",rowsArray);
        return JsonUtils.fromObject(jsonObject);
    }

    @RequestMapping("/mediaList.do")
    @ResponseBody
    public String mediaList(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        Integer limit = request.getInt("rows", 10);
        Integer page = request.getInt("page", 1);
        Integer mediaType = request.getInt("mediaType", 0);
        String mediaName = request.getString("mediaName", null);
        Boolean forbidden = request.getBoolean("forbidden");
        // 上传时间


        Page pageInfo=new Page();
        pageInfo.setCurrent((page-1)*limit);
        pageInfo.setSize(limit);
        MediaCondition condition=new MediaCondition();

        condition.setMediaType(mediaType);
        if(!StringUtils.isEmpty(mediaName)){
            condition.setMediaName(mediaName);
        }
        if(forbidden != null) {
            condition.setForbidden(forbidden);
        }
        int subCount = eduTrainingService.countMediaResource(condition);

        List<MediaResource> mediaResourceList=eduTrainingService.getMediaResource(condition, pageInfo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("page",page);
        jsonObject.put("total",subCount==0?0:subCount/limit+1);
        jsonObject.put("medias",subCount);
        JSONArray rowsArray=new JSONArray();
        if(mediaResourceList!=null){
            for(MediaResource mediaResource:mediaResourceList){
                JSONObject subOrgObject=new JSONObject();
                subOrgObject.put("id", mediaResource.getId());
                subOrgObject.put("mediaName", mediaResource.getName());
                subOrgObject.put("updateTime", TimeUtils.convertToDateString(mediaResource.getUploadDate()));
                subOrgObject.put("mediaType", mediaResource.getType());
                subOrgObject.put("description", mediaResource.getDescription());
                subOrgObject.put("forbidden", mediaResource.isForbidden());
                rowsArray.add(subOrgObject);
            }
        }
        jsonObject.put("rows",rowsArray);
        return JsonUtils.fromObject(jsonObject);
    }
}
