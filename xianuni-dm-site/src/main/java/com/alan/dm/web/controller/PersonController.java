package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.PrepareInfo;
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

	@Resource(name = "adminService")
	private IAdminService adminService;

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	/**
	 * 获取基本信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicInfo.do")
	@ResponseBody
	public String basicInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("personId");

		Person person = personService.getById(personId);
		JSONObject jsonObject=new JSONObject();
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName",orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToDateString(person.getCreateTime()));
		jsonObject.put("updateTime",TimeUtils.convertToDateString(person.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("type",PersonType.getInstance(person.getType()).getName());
		jsonObject.put("idNumber",person.getIdNumber());
		jsonObject.put("sex",person.getSex()==0?"男":"女");
		//jsonObject.put("nation",person.getNation());
		//jsonObject.put("degree",orgnization.getName());
		jsonObject.put("hometown",person.getHometown());
		jsonObject.put("birth",TimeUtils.convertToDateString(person.getBirth()));
		//jsonObject.put("profession",person.getProfession());
		jsonObject.put("desc",person.getPersonDesc());
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activitist/info.do")
	@ResponseBody
	public String activitistInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int activitistId = request.getInt("activitistId");
		ActivitistInfo activitistInfo=activitistService.getById(activitistId);
		JSONObject jsonObject=new JSONObject();
		if(activitistInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}

		Person person = personService.getById(activitistInfo.getPersonId());
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToDateString(activitistInfo.getCreateTime()));
		//jsonObject.put("updateTime",TimeUtils.convertToDateString(activitistInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("evaTime", activitistInfo.getEvaluationTime());
		jsonObject.put("evaContent",activitistInfo.getEvaluationContent());
		jsonObject.put("meetTime",activitistInfo.getMeetTime());
		jsonObject.put("meetContent",activitistInfo.getMeetContent());
		int directorId = activitistInfo.getDirector();
		if(directorId!=0){
			Person director = personService.getById(directorId);
			if(director!=null){
				jsonObject.put("director",director.getName());
			}
		}
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applier/info.do")
	@ResponseBody
	public String applierInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("personId");

		Person person=personService.getById(personId);
		JSONObject jsonObject=new JSONObject();
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		ApplierInfo applierInfo =applierService.getById(person.getApplierInfoId());
		if(applierInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToDateString(applierInfo.getCreateTime()));
		//jsonObject.put("updateTime",TimeUtils.convertToDateString(activitistInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("applyTime", TimeUtils.convertToDateString(applierInfo.getApplyTime()));
		jsonObject.put("talkContent",applierInfo.getTalkContent());
		jsonObject.put("talkTime", TimeUtils.convertToDateString(applierInfo.getTalkTime()));
		int talkerId = applierInfo.getTalkerId();
		if(talkerId!=0){
			Person director = personService.getById(talkerId);
			if(director!=null){
				jsonObject.put("talker",director.getName());
			}
		}
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/intention/info.do")
	@ResponseBody
	public String intentionInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("intentionId");

		JSONObject jsonObject=new JSONObject();
		Person person = personService.getById(personId);
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}

		IntentionInfo intentionInfo = intentionService.getById(person.getIntentionInfoId());
		if(intentionInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}

		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToDateString(intentionInfo.getCreateTime()));
		//jsonObject.put("updateTime",TimeUtils.convertToDateString(activitistInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("trainHour", person.getName());
		jsonObject.put("publiced", person.getName());
		jsonObject.put("meetTime", TimeUtils.convertToDateString(intentionInfo.getMeetTime()));
		jsonObject.put("meetContent", intentionInfo.getMeetContent());
		jsonObject.put("politicalCheck", intentionInfo.getPoliticalChcekContent());
		jsonObject.put("schoolApproval", intentionInfo.getSchoolApproval());
		int talkerId = intentionInfo.getIntroducer();
		if(talkerId!=0){
			Person director = personService.getById(talkerId);
			if(director!=null){
				jsonObject.put("introducer",director.getName());
			}
		}
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepare/info.do")
	@ResponseBody
	public String prepareInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("prepareId");

		JSONObject jsonObject=new JSONObject();
		Person person=personService.getById(personId);
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		PrepareInfo prepareInfo = prepareService.getById(person.getPrepareInfoId());
		if(prepareInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToDateString(prepareInfo.getCreateTime()));
		//jsonObject.put("updateTime",TimeUtils.convertToDateString(activitistInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("application", prepareInfo.isApplication()?"已提交":"未提交");
		jsonObject.put("meetTime", TimeUtils.convertToDateString(prepareInfo.getMeetTime()));
		jsonObject.put("meetContent", prepareInfo.getMeetContent());
		jsonObject.put("schoolApproval", prepareInfo.getSchoolApproval());
		jsonObject.put("branchApproval", prepareInfo.getBranchApproval());
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/normal/info.do")
	@ResponseBody
	public String normalInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("normalId");

		JSONObject jsonObject=new JSONObject();
		Person person=personService.getById(personId);
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		NormalInfo normalInfo = normalService.getById(person.getNormalInfoId());
		if(normalInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToDateString(normalInfo.getCreateTime()));
		//jsonObject.put("updateTime",TimeUtils.convertToDateString(activitistInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("schoolApproval", normalInfo.getSchoolApproval());
		jsonObject.put("branchApproval", normalInfo.getBranchApproval());
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicAdd.do")
	@ResponseBody
	public String basicAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String name=request.getString("name");
		String idnumber=request.getString("idNumber");
		String number=request.getString("number");
		String desc=request.getString("desc", null);
		int type=request.getInt("type", PersonType.STUDENT.getId());
		int nation=request.getInt("nation", 0);
		int degree=request.getInt("degree", 0);
		int sex=request.getInt("sex", 0);
		int profession=request.getInt("profession", 0);
		Date birth=request.getDate("birth");

		Orgnization orgnization = orgnizationService.getOrgById(orgId);
		if(orgnization==null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","党组织不存在");
			return JsonUtils.fromObject(jsonObject);
		}
		Person person=new Person();
		person.setOrgnization(orgnization);
		person.setName(name);
		person.setIdNumber(idnumber);
		person.setNumber(number);
		person.setType(type);
		person.setBirth(birth);
		person.setDegree(degree);
		person.setStatus(PersonStatus.NO.getId());
		person.setNation(nation);
		person.setPersonDesc(desc);
		person.setSex(sex);
		person.setProfession(profession);
		Person oldPerson=personService.getByNumber(number);
		if(oldPerson!=null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","该学工号已经有记录");
			return JsonUtils.fromObject(jsonObject);
		}
		try{
			personService.createPerson(person);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",true);
			return JsonUtils.fromObject(jsonObject);
		}catch (Exception e){
			LOGGER.error("add person fail,name={}",name,e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","添加失败");
			return JsonUtils.fromObject(jsonObject);
		}
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applierAdd.do")
	@ResponseBody
	public String applierAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		String talkContent=request.getString("talkContent", null);
		String talkerNumber=request.getString("talkerNumber",null);
		Date talkTime=request.getDate("talkTime", null);
		Date applyTime=request.getDate("applyTime", null);
		//检查是否学号正确，状态+部门
		//检查谈话人是否是本部门
		//添加申请人信息，修改基础人员状态
		ApplierInfo applierInfo=new ApplierInfo();
		applierInfo.setApplyTime(applyTime);
		applierInfo.setTalkContent(talkContent);
		if(!StringUtils.isEmpty(talkerNumber)){
			Person talkerPerson = personService.getByNumber(talkerNumber);
			if(talkerPerson==null||talkerPerson.getStatus()!=PersonStatus.NORMAL.getId()){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success", false);
				jsonObject.put("msg", "谈话人无效" + talkerNumber);
				return JsonUtils.fromObject(jsonObject);
			}
			applierInfo.setTalker(talkerPerson);
		}
		applierInfo.setTalkTime(talkTime);

		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.NO.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为申请人"+number);
			return JsonUtils.fromObject(jsonObject);
		}
		applierService.createApplier(applierInfo);
		//修改基础人员状态
		person.setStatus(PersonStatus.APPLIER.getId());
		person.setApplierInfoId(applierInfo.getId());
		personService.updatePerson(person);

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
	@RequestMapping("/activitistAdd.do")
	@ResponseBody
	public String activitistAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		String meetContent=request.getString("meetContent", null);
		String evaContent=request.getString("evaContent", null);
		String directorNumber=request.getString("directorNumber",null);
		Date meetTime=request.getDate("meetTime", null);
		Date evaTime=request.getDate("evaTime",null);

		ActivitistInfo activitistInfo=new ActivitistInfo();
		if(!StringUtils.isEmpty(directorNumber)){
			Person talkerPerson = personService.getByNumber(directorNumber);
			if(talkerPerson==null||talkerPerson.getStatus()!=PersonStatus.NORMAL.getId()){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success", false);
				jsonObject.put("msg", "无效" + directorNumber);
				return JsonUtils.fromObject(jsonObject);
			}
			activitistInfo.setDirector(talkerPerson.getId());
		}

		activitistInfo.setEvaluationContent(evaContent);
		activitistInfo.setMeetContent(meetContent);
		activitistInfo.setMeetTime(meetTime);
		activitistInfo.setEvaluationTime(evaTime);
		activitistInfo.setRecorded(true);
		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.APPLIER.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为积极分子"+number);
			return JsonUtils.fromObject(jsonObject);
		}
		if(person.getOrgId()!=orgId){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为积极分子"+number);
			return JsonUtils.fromObject(jsonObject);
		}

		activitistService.createActivitist(activitistInfo);

		person.setStatus(PersonStatus.ACTIVISTS.getId());
		person.setActivitistInfoId(activitistInfo.getId());
		personService.updatePerson(person);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/intentionAdd.do")
	@ResponseBody
	public String intentionAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		int classHour=request.getInt("trainHour", 0);
		int publiced=request.getInt("publiced", 0);
		String directorNumber=request.getString("directorId", null);
		Date meetTime=request.getDate("meetTime", null);
		String meetContent=request.getString("meetContent", null);
		String politicalCheck=request.getString("politicalCheck",null);

		IntentionInfo intentionInfo=new IntentionInfo();
		if(!StringUtils.isEmpty(directorNumber)){
			Person talkerPerson = personService.getByNumber(directorNumber);
			if(talkerPerson==null||talkerPerson.getStatus()!=PersonStatus.NORMAL.getId()){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success", false);
				jsonObject.put("msg", "无效" + directorNumber);
				return JsonUtils.fromObject(jsonObject);
			}
			intentionInfo.setIntroducer(talkerPerson.getId());
		}

		intentionInfo.setRecorded(true);
		intentionInfo.setMeetContent(meetContent);
		intentionInfo.setMeetTime(meetTime);
		intentionInfo.setPoliticalChcekContent(politicalCheck);
		intentionInfo.setSchoolApproval(null);
		intentionInfo.setPubliced(publiced == 0);
		intentionInfo.setTrainHour(classHour);
		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.ACTIVISTS.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为发展对象"+number);
			return JsonUtils.fromObject(jsonObject);
		}
		intentionService.createIntention(intentionInfo);

		person.setStatus(PersonStatus.INTENTION.getId());
		person.setIntentionInfoId(intentionInfo.getId());
		personService.updatePerson(person);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepareAdd.do")
	@ResponseBody
	public String prepareAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		Date meetTime=request.getDate("meetTime", null);
		String branchApproval=request.getString("branchApproval", null);
		String schoolApproval=request.getString("schoolApproval", null);
		String meetContent=request.getString("meetContent", null);

		PrepareInfo prepareInfo=new PrepareInfo();
		prepareInfo.setSchoolApproval(schoolApproval);
		prepareInfo.setApplication(true);
		prepareInfo.setBranchApproval(branchApproval);
		prepareInfo.setMeetContent(meetContent);
		prepareInfo.setMeetTime(meetTime);
		prepareInfo.setSchoolApproval(schoolApproval);

		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.INTENTION.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为预备党员"+number);
			return JsonUtils.fromObject(jsonObject);
		}
		prepareService.createPrepare(prepareInfo);

		person.setStatus(PersonStatus.PERPARE.getId());
		person.setPrepareInfoId(prepareInfo.getId());
		personService.updatePerson(person);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/normalAdd.do")
	@ResponseBody
	public String normalAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		String branchApproval=request.getString("branchApproval", null);
		String schoolApproval=request.getString("schoolApproval", null);

		NormalInfo normalInfo=new NormalInfo();
		normalInfo.setSchoolApproval(schoolApproval);
		normalInfo.setBranchApproval(branchApproval);
		normalInfo.setApplyTime(new Date());
		normalInfo.setApproval(null);
		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.PERPARE.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为正式党员"+number);
			return JsonUtils.fromObject(jsonObject);
		}

		normalService.createNormal(normalInfo);

		person.setStatus(PersonStatus.NORMAL.getId());
		person.setNormalInfoId(normalInfo.getId());
		personService.updatePerson(person);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 正式党员查询接口
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listQuery.do")
	@ResponseBody
	public String listQuery(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId=request.getInt("orgId", 0);
		String[] statusArray=request.getStringArray("status", ",");

		String number=request.getString("number", null);
		PersonCondition condition=new PersonCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);

		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		List<Integer> statuslist=new ArrayList<Integer>();
		for(String statusParam:statusArray){
			statuslist.add(Integer.parseInt(statusParam));
		}
		condition.setOrgList(orgIdList);
		condition.setStatus(statuslist);

		Page pageInfo=new Page();
		pageInfo.setCurrent(0);
		pageInfo.setSize(10);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(false);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject keyObject=new JSONObject();
				keyObject.put("personId",person.getId());
				keyObject.put("personName",person.getName());
				keyObject.put("personNumber", person.getNumber());
				keyObject.put("personStatus",PersonStatus.getInstance(person.getStatus()).getName());
				rowsArray.add(keyObject);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code", 200);
		jsonObject.put("message", "success");
		jsonObject.put("value",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
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
		Integer orgId=request.getInt("orgId",0);
		String name=request.getString("name", null);
		String number=request.getString("number", null);
		boolean containSubOrg=request.getBoolean("containSub", true);
		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		PersonCondition condition=new PersonCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);

		if(containSubOrg){
			Orgnization orgnization=null;
			if(orgId==0){
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
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);

		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		condition.setStatus(Arrays.asList(PersonStatus.NO.getId()));
		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

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
				//cellList.add(PersonStatus.getInstance(person.getStatus()).getName());
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
		boolean containSubOrg=request.getBoolean("containSub", true);

		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		PersonCondition condition=new PersonCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);

		if(containSubOrg){
			Orgnization orgnization=null;
			if(orgId==0){
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
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		condition.setStatus(Arrays.asList(PersonStatus.APPLIER.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeApplierInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

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
				ApplierInfo applierInfo=person.getApplierInfo();
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				String applyTime=TimeUtils.convertToDateString(applierInfo.getApplyTime());
				cellList.add(StringUtils.isEmpty(applyTime)?"未提交申请书":applyTime);
				String talkTime=TimeUtils.convertToDateString(applierInfo.getTalkTime());
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
		boolean containSubOrg=request.getBoolean("containSub", true);

		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		PersonCondition condition=new PersonCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);

		if(containSubOrg){
			Orgnization orgnization=null;
			if(orgId==0){
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
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		condition.setStatus(Arrays.asList(PersonStatus.ACTIVISTS.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeActivitistInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

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
				cellList.add(person.getOrgnization().getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				String evaluaTime=TimeUtils.convertToDateString(person.getActivitistInfo().getEvaluationTime());
				cellList.add(StringUtils.isEmpty(evaluaTime)?"未测评":evaluaTime);
				String meetTime=TimeUtils.convertToDateString(person.getActivitistInfo().getMeetTime());
				cellList.add(StringUtils.isEmpty(meetTime)?"未召开":meetTime);
				cellList.add(person.getActivitistInfo().getDirector()==0?"未确定":"已确定");
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
		boolean containSubOrg=request.getBoolean("containSub", true);

		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		PersonCondition condition=new PersonCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);

		if(containSubOrg){
			Orgnization orgnization=null;
			if(orgId==0){
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
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		condition.setStatus(Arrays.asList(PersonStatus.INTENTION.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeIntentionInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

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
				IntentionInfo intentionInfo=person.getIntentionInfo();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				cellList.add(intentionInfo.isPubliced()?"已公示":"已公示");
				cellList.add(intentionInfo.getTrainHour()==0?"未设置":intentionInfo.getTrainHour()+"课时");
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
		boolean containSubOrg=request.getBoolean("containSub", true);

		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		PersonCondition condition=new PersonCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);

		if(containSubOrg){
			Orgnization orgnization=null;
			if(orgId==0){
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
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		condition.setStatus(Arrays.asList(PersonStatus.PERPARE.getId()));

		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludePrepareInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				PrepareInfo prepareInfo = person.getPrepareInfo();
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(prepareInfo.getId()));
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
		boolean containSubOrg=request.getBoolean("containSub", true);
		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		PersonCondition condition=new PersonCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);

		if(containSubOrg){
			Orgnization orgnization=null;
			if(orgId==0){
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
		condition.setStatus(Arrays.asList(PersonStatus.NORMAL.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeNormalInfo(true);

		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

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
				cellList.add(person.getSource()==Person.SOURCE_OUT?"是":"否");
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getNumber());
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 删除申请人信息，删除申请相关资料+变为基础人员
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applier/delete.do")
	@ResponseBody
	public String applierDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				ApplierInfo applierInfo=applierService.getById(person.getApplierInfoId());
				if(applierInfo!=null){
					applierService.deleteApplier(applierInfo);
				}
				person.setStatus(PersonStatus.NO.getId());
				person.setApplierInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除积极分子信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activitist/delete.do")
	@ResponseBody
	public String activitistDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				ActivitistInfo activitistInfo=activitistService.getById(person.getApplierInfoId());
				if(activitistInfo!=null){
					activitistService.deleteActivitist(activitistInfo);
				}
				person.setStatus(PersonStatus.APPLIER.getId());
				person.setActivitistInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除发展对象信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/intention/delete.do")
	@ResponseBody
	public String intentionDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				IntentionInfo intentionInfo=intentionService.getById(person.getApplierInfoId());
				if(intentionInfo!=null){
					intentionService.deleteIntention(intentionInfo);
				}
				person.setStatus(PersonStatus.ACTIVISTS.getId());
				person.setIntentionInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除预备党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepare/delete.do")
	@ResponseBody
	public String prepareDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				PrepareInfo prepareInfo=prepareService.getById(person.getApplierInfoId());
				if(prepareInfo!=null){
					prepareService.deletePrepare(prepareInfo);
				}
				person.setStatus(PersonStatus.INTENTION.getId());
				person.setPrepareInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/normal/delete.do")
	@ResponseBody
	public String normalDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				NormalInfo normalInfo=normalService.getById(person.getApplierInfoId());
				if(normalInfo!=null){
					normalService.deleteNormal(normalInfo);
				}
				person.setStatus(PersonStatus.PERPARE.getId());
				person.setNormalInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
//				NormalInfo normalInfo = normalService.getByPerson(person);
//				if(normalInfo!=null){
//					normalService.deleteNormal(normalInfo);
//				}
//				PrepareInfo prepareInfo = prepareService.getByPerson(person);
//				if(prepareInfo!=null){
//					prepareService.deletePrepare(prepareInfo);
//				}
//				IntentionInfo intentionInfo=intentionService.getByPerson(person);
//				if(intentionInfo!=null){
//					intentionService.deleteIntention(intentionInfo);
//				}
//				ActivitistInfo activitistInfo=activitistService.getByPerson(person);
//				if(activitistInfo!=null){
//					activitistService.deleteActivitist(activitistInfo);
//				}
				//ApplierInfo applierInfo=applierService.getByPerson(person);
				//if(applierInfo!=null){
				//	applierService.deleteApplier(applierInfo);
				//}
				//TODO 其他信息
				personService.deletePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
}
