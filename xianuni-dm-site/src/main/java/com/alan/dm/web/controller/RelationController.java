package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.RelationTransferCondition;
import com.alan.dm.service.IRelationTransferService;
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
import java.util.List;

@Controller
@RequestMapping("/relation")
public class RelationController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(RelationController.class);

	@Resource(name = "relationTransferService")
	private IRelationTransferService relationTransferService;

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transfer/outList.do")
	@ResponseBody
	public String outList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId",0);
		String name=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);
		RelationTransferCondition condition=new RelationTransferCondition();
		if(orgId!=0){
			condition.setFromOrgId(orgId);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setNumber(name);
		}
		condition.setTypes(Arrays.asList(2,0));
		int subCount = relationTransferService.countByCondition(condition);

		List<RelationTransferInfo> relationTransferInfoList=relationTransferService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(relationTransferInfoList!=null){
			for(RelationTransferInfo relationTransferInfo:relationTransferInfoList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", relationTransferInfo.getId());
				List<String> cellList=new ArrayList<String>();
				Person person=relationTransferInfo.getPerson();
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(relationTransferInfo.getFromOrgName());
				cellList.add(relationTransferInfo.getToOrgName());
				cellList.add(TimeUtils.convertToDateString(relationTransferInfo.getTransferTime()));
				cellList.add(RelationTransferType.getInstance(relationTransferInfo.getTransferType()).getName());
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
	@RequestMapping("/transfer/inList.do")
	@ResponseBody
	public String inList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId",0);
		String name=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);
		RelationTransferCondition condition=new RelationTransferCondition();
		if(orgId!=0){
			condition.setToOrgId(orgId);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setNumber(name);
		}
		condition.setTypes(Arrays.asList(1, 0));

		int subCount = relationTransferService.countByCondition(condition);

		List<RelationTransferInfo> relationTransferInfoList=relationTransferService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(relationTransferInfoList!=null){
			for(RelationTransferInfo relationTransferInfo:relationTransferInfoList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", relationTransferInfo.getId());
				List<String> cellList=new ArrayList<String>();
				Person person=relationTransferInfo.getPerson();
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(relationTransferInfo.getFromOrgName());
				cellList.add(relationTransferInfo.getToOrgName());
				cellList.add(TimeUtils.convertToDateString(relationTransferInfo.getTransferTime()));
				cellList.add(RelationTransferType.getInstance(relationTransferInfo.getTransferType()).getName());
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
}
