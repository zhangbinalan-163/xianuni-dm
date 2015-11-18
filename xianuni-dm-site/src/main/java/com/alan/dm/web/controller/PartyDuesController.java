package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.OrgRewardCondition;
import com.alan.dm.entity.condition.PartyDuesCondition;
import com.alan.dm.service.IOrgRewardService;
import com.alan.dm.service.IOrgnizationService;
import com.alan.dm.service.IPartyDuesService;
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
@RequestMapping("/dues")
public class PartyDuesController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(PartyDuesController.class);

	@Resource(name = "partyDuesService")
	private IPartyDuesService partyDuesService;

	@RequestMapping("/list.do")
	@ResponseBody
	public String dueList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId",0);
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		PartyDuesCondition condition=new PartyDuesCondition();
		if(orgId!=0){
			condition.setOrgId(orgId);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		int subCount = partyDuesService.countByCondition(condition);

		List<PartyDuesPay> duesPayList = partyDuesService.getByCondition(condition, pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(duesPayList!=null){
			for(PartyDuesPay duesPay:duesPayList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", duesPay.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(duesPay.getId()));
				Person person=duesPay.getPerson();
				Orgnization org = person.getOrgnization();
				cellList.add(org.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonStatus.getInstance(person.getStatus()).getName());
				cellList.add(TimeUtils.convertToDateString(duesPay.getPayTime()));
				cellList.add(TimeUtils.convertToDateString(duesPay.getPayStartTime()));
				cellList.add(TimeUtils.convertToDateString(duesPay.getPayEndTime()));
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
}
