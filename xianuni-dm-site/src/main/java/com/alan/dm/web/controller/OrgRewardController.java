package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.OrgRewardCondition;
import com.alan.dm.service.IOrgRewardService;
import com.alan.dm.service.IOrgnizationService;
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

@Controller
@RequestMapping("/reward")
public class OrgRewardController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(OrgRewardController.class);

	@Resource(name = "orgRewardService")
	private IOrgRewardService orgRewardService;

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	@RequestMapping("/list.do")
	@ResponseBody
	public String rewardList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId",0);
		String name=request.getString("name", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		OrgRewardCondition condition=new OrgRewardCondition();
		if(orgId!=0){
			condition.setOrgId(orgId);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}
		int subCount = orgRewardService.countByCondition(condition);

		List<OrgReward> rewardList=orgRewardService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(rewardList!=null){
			for(OrgReward reward:rewardList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", reward.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(reward.getId()));
				cellList.add(reward.getOrgnization().getName());
				cellList.add(reward.getName());
				cellList.add(OrgRewardType.getInstance(reward.getType()).getName());
				cellList.add(TimeUtils.convertToDateString(reward.getRewardTime()));
				cellList.add(OrgRewardLevel.getInstance(reward.getLevel()).getName());
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
}