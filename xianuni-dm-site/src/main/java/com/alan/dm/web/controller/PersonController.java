package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.*;
import com.alan.dm.entity.PrepareInfo;
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
	 * 获取基本信息列表
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicList.do")
	@ResponseBody
	public String basicList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String name=request.getString("name", null);
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		PersonCondition condition=new PersonCondition();
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
				Orgnization orgInfo = person.getOrgnization();
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
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		ApplierInfoCondition condition=new ApplierInfoCondition();
		condition.setStatus(PersonStatus.APPLIER.getId());
		if(orgId!=0){
			condition.setOrgId(orgId);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = applierService.countByCondition(condition);

		List<ApplierInfo> applierInfoList=applierService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(applierInfoList!=null){
			for(ApplierInfo applier:applierInfoList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", applier.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(applier.getId()));
				Person person=applier.getPerson();
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				String applyTime=TimeUtils.convertToDateString(applier.getApplyTime());
				cellList.add(StringUtils.isEmpty(applyTime)?"未提交申请书":applyTime);
				String talkTime=TimeUtils.convertToDateString(applier.getTalkTime());
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
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		ActivitistInfoCondition condition=new ActivitistInfoCondition();
		condition.setStatus(PersonStatus.ACTIVISTS.getId());
		if(orgId!=0){
			condition.setOrgId(orgId);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = activitistService.countByCondition(condition);

		List<ActivitistInfo> activitistInfoList=activitistService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(activitistInfoList!=null){
			for(ActivitistInfo activitistInfo:activitistInfoList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", activitistInfo.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(activitistInfo.getId()));
				cellList.add(activitistInfo.getPerson().getOrgnization().getName());
				cellList.add(activitistInfo.getPerson().getName());
				cellList.add(PersonType.getInstance(activitistInfo.getPerson().getType()).getName());
				cellList.add(activitistInfo.getPerson().getNumber());
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
	/**
	 * 获取发展对象信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/intentionList.do")
	@ResponseBody
	public String intentionList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		IntentionInfoCondition condition=new IntentionInfoCondition();
		condition.setStatus(PersonStatus.INTENTION.getId());
		if(orgId!=0){
			condition.setOrgId(orgId);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = intentionService.countByCondition(condition);

		List<IntentionInfo> intentionInfoList=intentionService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(intentionInfoList!=null){
			System.out.println("intention size=" + intentionInfoList.size());
			for(IntentionInfo intentionInfo:intentionInfoList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", intentionInfo.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(intentionInfo.getId()));
				Person person=intentionInfo.getPerson();
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				cellList.add(intentionInfo.isPubliced()?"已公示":"已公示");
				cellList.add(intentionInfo.getTrainHour()==0?"未设置":intentionInfo.getTrainHour()+"课时");
				cellList.add(intentionInfo.isRecorded()?"未备案":"已备案");
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 获取预备党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepareList.do")
	@ResponseBody
	public String prepareList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		PrepareInfoCondition condition=new PrepareInfoCondition();
		condition.setStatus(PersonStatus.PERPARE.getId());
		if(orgId!=0){
			condition.setOrgId(orgId);
		}

		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = prepareService.countByCondition(condition);

		List<PrepareInfo> prepareInfoList=prepareService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(prepareInfoList!=null){
			for(PrepareInfo prepareInfo:prepareInfoList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", prepareInfo.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(prepareInfo.getId()));
				Person person=prepareInfo.getPerson();
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				cellList.add(prepareInfo.isApplication()?"已提交":"未提交");
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 获取正式党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/normalList.do")
	@ResponseBody
	public String normalList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String number=request.getString("number", null);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);
		NormalInfoCondition condition=new NormalInfoCondition();
		condition.setStatus(PersonStatus.NORMAL.getId());
		if(orgId!=0){
			condition.setOrgId(orgId);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = normalService.countByCondition(condition);

		List<NormalInfo> normalInfoList=normalService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(normalInfoList!=null){
			for(NormalInfo normalInfo:normalInfoList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", normalInfo.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(normalInfo.getId()));
				Person person=normalInfo.getPerson();
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
}
