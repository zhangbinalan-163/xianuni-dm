package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.OrgRewardCondition;
import com.alan.dm.entity.condition.PersonCondition;
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
import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(PersonController.class);

	@Resource(name = "personService")
	private IPersonService personService;

	@Resource(name = "applicationService")
	private IApplicationService applicationService;

	@Resource(name = "activitistService")
	private IActivitistService activitistService;

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	/**
	 * 获取基本信息列表
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicList.do")
	@ResponseBody
	public String orglist(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String name=request.getString("name", null);
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent(page);
		pageInfo.setSize(limit);
		PersonCondition condition=new PersonCondition();;
		if(orgId!=0){
			condition.setOrgId(orgId);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		int subCount = personService.countByCondition(condition);

		List<Person> personList=personService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = orgnizationService.getOrgById(person.getOrgId());
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				cellList.add(TimeUtils.convertToDateString(person.getCreateTime()));
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 获取申请人信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applierList.do")
	@ResponseBody
	public String applierList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String name=request.getString("name", null);
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent(page);
		pageInfo.setSize(limit);
		PersonCondition condition=new PersonCondition();
		condition.setStatus(PersonStatus.APPLIER.getId());
		if(orgId!=0){
			condition.setOrgId(orgId);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		List<Person> personList=personService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = orgnizationService.getOrgById(person.getOrgId());
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				ApplicationInfo applyInfo = applicationService.getByPerson(person);
				String applyTime=TimeUtils.convertToDateString(applyInfo.getApplyTime());
				cellList.add(StringUtils.isEmpty(applyTime)?"未提交申请书":applyTime);
				String talkTime=TimeUtils.convertToDateString(applyInfo.getTalkTime());
				cellList.add(StringUtils.isEmpty(talkTime)?"未谈话":talkTime);
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 获取积极分子信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activitistList.do")
	@ResponseBody
	public String activitistList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String name=request.getString("name", null);
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent(page);
		pageInfo.setSize(limit);
		PersonCondition condition=new PersonCondition();
		condition.setStatus(PersonStatus.ACTIVISTS.getId());
		if(orgId!=0){
			condition.setOrgId(orgId);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		List<Person> personList=personService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = orgnizationService.getOrgById(person.getOrgId());
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				ActivitistInfo activitistInfo = activitistService.getByPerson(person);
				String evaluaTime=TimeUtils.convertToDateString(activitistInfo.getEvaluationTime());
				cellList.add(StringUtils.isEmpty(evaluaTime)?"未测评":evaluaTime);
				String meetTime=TimeUtils.convertToDateString(activitistInfo.getMeetTime());
				cellList.add(StringUtils.isEmpty(meetTime)?"未召开":meetTime);
				String director=activitistInfo.getDirector();
				cellList.add(StringUtils.isEmpty(director)?"未确定":"已确定");
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
}
