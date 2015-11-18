package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.entity.DateRange;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyTraining;
import com.alan.dm.entity.condition.TrainingCondition;
import com.alan.dm.service.ITrainingService;
import com.alan.dm.web.vo.Request;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 教育培训
 * @Date: 2015-11-17
 * @author: fan
 */
@Controller
@RequestMapping("/training")
public class TrainingController extends BaseController {

    @Resource(name = "trainingService")
    private ITrainingService trainingService;

    /**
     * 新增或更新党员培训，学习记录，专题教育信息
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertOrUpdate.do")
    @ResponseBody
    public String insert(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);

        PartyTraining training = new PartyTraining();
        int id = request.getInt("id");
        training.setId(id);
        training.setType(request.getInt("type"));
        training.setTitle(request.getString("title"));
        training.setTrainingType(request.getInt("trainingType"));
        training.setOrganizationId(request.getInt("organizationId"));
        training.setStartTime(request.getDate("startTime"));
        training.setEndTime(request.getDate("endTime"));
        training.setPeriod(request.getInt("period"));
        training.setTrainingObject(request.getString("trainingObject"));
        training.setContent(request.getString("content"));
        training.setHarvest(request.getString("harvest"));
        training.setOpinion(request.getString("opinion"));

        if(id == 0) {
            id = trainingService.addTraining(training);
        } else {
            trainingService.modifyTraining(training);
        }


        JSONObject jsonObject=new JSONObject();
        jsonObject.put("statusCode", 200);
        jsonObject.put("message", "success");
        jsonObject.put("trainingId", id);
        return JsonUtils.fromObject(jsonObject);
    }

    /**
     * 删除培训信息
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public String delete(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);
        int trainingId = request.getInt("trainingId");
        if(trainingId != 0) {
            trainingService.deleteTraining(trainingId);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("statusCode", 200);
        jsonObject.put("message", "success");
        return JsonUtils.fromObject(jsonObject);
    }

    /**
     * 获取党员培训，学习记录，专题教育信息
     * 查询condition
     * @condition type trainingType startTime endTime organization size current
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getList.do")
    @ResponseBody
    public String getList(HttpServletRequest httpServletRequest) throws Exception {
        Request request = getRequest(httpServletRequest);

        TrainingCondition condition = new TrainingCondition();

        condition.setType(request.getInt("type"));
        condition.setTrainingType(request.getInt("trainingType", 0));

        Date startTime = request.getDate("startTime");
        Date endTime = request.getDate("endTime");
        if(startTime != null && endTime != null) {
            condition.setRange(new DateRange(startTime, endTime));
        }
        condition.setOrganization(request.getString("organization", null));

        Page page = new Page(request.getInt("size", 0), request.getInt("current", 0));
        List<PartyTraining> trainingList = trainingService.getTraining(condition, page);

        List<JSONObject> list = null;
        if(trainingList != null && !trainingList.isEmpty()) {
            list = new ArrayList<>();
            for(PartyTraining training : trainingList) {
                JSONObject object = new JSONObject();
                object.put("title", training.getTitle());
                object.put("trainingType", training.getType());
                object.put("organization", training.getOrganization().getName());
                object.put("startTime", training.getStartTime());
                object.put("endTime", training.getEndTime());
                object.put("period", training.getPeriod());
                object.put("trainingObject", training.getTrainingObject());
                object.put("content", training.getContent());
                object.put("harvest", training.getHarvest());
                object.put("opinion", training.getOpinion());
                list.add(object);
            }
        }

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("statusCode", 200);
        jsonObject.put("message", "success");
        jsonObject.put("data", list);
        return JsonUtils.fromObject(jsonObject);
    }
}
