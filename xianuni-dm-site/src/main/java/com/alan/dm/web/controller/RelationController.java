package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.RelationTransferCondition;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/relation")
public class RelationController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(RelationController.class);

	@Resource(name = "relationTransferService")
	private IRelationTransferService relationTransferService;

	@Resource(name = "adminService")
	private IAdminService adminService;

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

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

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transfer/delete.do")
	@ResponseBody
	public String inDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer id=request.getInt("id");
		RelationTransferInfo transferInfo = relationTransferService.getById(id);
		if(transferInfo==null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg", "记录不存在");
			return JsonUtils.fromObject(jsonObject);
		}
		relationTransferService.deleteTransfer(transferInfo);
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
	@RequestMapping("/transfer/in/add.do")
	@ResponseBody
	public String inAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId=request.getInt("orgId");
		Integer type=request.getInt("type", 0);
		String number=request.getString("number");
		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		if(type==1){
			String orgName=request.getString("orgName");
			String name=request.getString("name");
			//校外转入
			Person person = personService.getByNumber(number);
			if(person!=null){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success",false);
				jsonObject.put("msg","该学号已经存在");
				return JsonUtils.fromObject(jsonObject);
			}
			//创建空的相关新
			ApplierInfo applierInfo=new ApplierInfo();
			applierService.createApplier(applierInfo);
			ActivitistInfo activitistInfo=new ActivitistInfo();
			activitistService.createActivitist(activitistInfo);
			IntentionInfo intentionInfo=new IntentionInfo();
			intentionService.createIntention(intentionInfo);
			PrepareInfo prepareInfo=new PrepareInfo();
			prepareService.createPrepare(prepareInfo);
			NormalInfo normalInfo=new NormalInfo();
			normalService.createNormal(normalInfo);
			//创建党员信息
			person=new Person();
			person.setNumber(number);
			person.setSource(type == 1 ? 1 : 0);
			person.setOrgnization(orgnization);
			person.setPersonDesc("校外转入");
			person.setName(name);
			person.setStatus(PersonStatus.FORMAL.getId());
			person.setNormalInfoId(normalInfo.getId());
			person.setApplierInfoId(applierInfo.getId());
			person.setActivitistInfoId(activitistInfo.getId());
			person.setIntentionInfoId(intentionInfo.getId());
			person.setPrepareInfoId(prepareInfo.getId());

			personService.createPerson(person);

			//创建转入记录
			RelationTransferInfo transferInfo=new RelationTransferInfo();
			transferInfo.setTransferType(RelationTransferType.IN_OTHER.getId());
			transferInfo.setPerson(person);
			transferInfo.setFromOrgName(orgName);
			transferInfo.setTransferTime(new Date());
			transferInfo.setToOrgId(orgnization.getId());
			transferInfo.setToOrgName(orgnization.getName());
			transferInfo.setTransferDoneTime(new Date());
			relationTransferService.createTransfer(transferInfo);

		}else{
			//校内转入
			Person person = personService.getByNumber(number);
			if(person==null){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success",false);
				jsonObject.put("msg","该学号不存在");
				return JsonUtils.fromObject(jsonObject);
			}
			Integer transferId=request.getInt("transferId");
			RelationTransferInfo transferInfo = relationTransferService.getById(transferId);
			if(transferInfo==null||transferInfo.getPersonId()!=person.getId()){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success",false);
				jsonObject.put("msg","记录不存在");
				return JsonUtils.fromObject(jsonObject);
			}
			transferInfo.setStatus(RelationTransferInfo.DONE);
			transferInfo.setTransferDoneTime(new Date());
			relationTransferService.updateTransfer(transferInfo);
			//设置新部门
			person.setOrgId(transferInfo.getToOrgId());
			person.setUpdateTime(new Date());
			personService.updatePerson(person);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		jsonObject.put("msg","success");
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 转出
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transfer/out/add.do")
	@ResponseBody
	public String outAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer type=request.getInt("type", 2);
		if(type==2){
			String toOrgName=request.getString("orgName");
			String number=request.getString("number");
			Person person = personService.getByNumber(number);
			if(person==null){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success",false);
				jsonObject.put("msg","该学号不存在");
				return JsonUtils.fromObject(jsonObject);
			}
			//创建转入记录
			RelationTransferInfo transferInfo=new RelationTransferInfo();
			transferInfo.setTransferType(RelationTransferType.OUT_OTHER.getId());
			transferInfo.setPerson(person);
			Orgnization oldOrg = orgnizationService.getOrgById(person.getOrgId());
			transferInfo.setFromOrgId(oldOrg.getId());
			transferInfo.setFromOrgName(oldOrg.getName());
			transferInfo.setToOrgName(toOrgName);
			transferInfo.setTransferTime(new Date());
			transferInfo.setTransferDoneTime(new Date());
			relationTransferService.createTransfer(transferInfo);
			//设置为历史党员
			person.setStatus(PersonStatus.HISTORY_OUT.getId());
			person.setUpdateTime(new Date());
			personService.updatePerson(person);

			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",true);
			jsonObject.put("msg","success");
			return JsonUtils.fromObject(jsonObject);
		}else{
			//校内转出
			int toOrgId=request.getInt("toOrgId");
			String number=request.getString("number");

			Person person = personService.getByNumber(number);
			if(person==null){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success",false);
				jsonObject.put("msg","该学号不存在");
				return JsonUtils.fromObject(jsonObject);
			}
			//创建转入记录
			RelationTransferInfo transferInfo=new RelationTransferInfo();
			transferInfo.setTransferType(RelationTransferType.INNER.getId());
			transferInfo.setStatus(RelationTransferInfo.CHECKING);
			transferInfo.setPerson(person);

			Orgnization toOrg = orgnizationService.getOrgById(toOrgId);
			transferInfo.setToOrgId(toOrg.getId());
			transferInfo.setToOrgName(toOrg.getName());

			Orgnization oldOrg=orgnizationService.getOrgById(person.getOrgId());
			transferInfo.setFromOrgId(oldOrg.getId());
			transferInfo.setFromOrgName(oldOrg.getName());
			transferInfo.setTransferTime(new Date());

			relationTransferService.createTransfer(transferInfo);

			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",true);
			jsonObject.put("msg","success");
			return JsonUtils.fromObject(jsonObject);
		}
	}
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
		boolean containSubOrg=request.getBoolean("containSub", true);
		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		RelationTransferCondition condition=new RelationTransferCondition();
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
		}
		condition.setFromOrgId(orgIdList);
		Page pageInfo=new Page();
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);

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
				cellList.add(String.valueOf(relationTransferInfo.getId()));
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(relationTransferInfo.getFromOrgName());
				cellList.add(relationTransferInfo.getToOrgName());
				cellList.add(relationTransferInfo.getStatus()==RelationTransferInfo.CHECKING?"审核中":"完成");
				cellList.add(RelationTransferType.getInstance(relationTransferInfo.getTransferType()).getName());
				cellList.add(TimeUtils.convertToDateString(relationTransferInfo.getTransferTime()));
				cellList.add(TimeUtils.convertToDateString(relationTransferInfo.getTransferDoneTime()));
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
	@RequestMapping("transfer/in/listInchecking.do")
	@ResponseBody
	public String listInChecking(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId=request.getInt("orgId");
		String number=request.getString("number", null);

		RelationTransferCondition condition=new RelationTransferCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);
		condition.setToOrgId(orgIdList);

		condition.setTypes(Arrays.asList(0));
		condition.setNumber(number);
		condition.setStatus(RelationTransferInfo.CHECKING);

		Page pageInfo=new Page();
		pageInfo.setCurrent(0);
		pageInfo.setSize(10);

		List<RelationTransferInfo> relationTransferInfoList=relationTransferService.getByCondition(condition,pageInfo);
		JSONArray rowsArray=new JSONArray();
		if(relationTransferInfoList!=null){
			for(RelationTransferInfo transferInfo:relationTransferInfoList){
				JSONObject keyObject=new JSONObject();
				Person person = transferInfo.getPerson();
				keyObject.put("transferId",transferInfo.getId());
				keyObject.put("personName",person.getName());
				keyObject.put("personNumber", person.getNumber());
				Orgnization oldOrg = orgnizationService.getOrgById(transferInfo.getFromOrgId());
				keyObject.put("orgName", oldOrg.getName());
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
		RelationTransferCondition condition=new RelationTransferCondition();
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
		}
		condition.setToOrgId(orgIdList);
		condition.setTypes(Arrays.asList(1, 0));
		condition.setStatus(RelationTransferInfo.DONE);
		condition.setNumber(number);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);

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
				cellList.add(String.valueOf(relationTransferInfo.getId()));
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(relationTransferInfo.getFromOrgName());
				cellList.add(relationTransferInfo.getToOrgName());
				cellList.add(RelationTransferType.getInstance(relationTransferInfo.getTransferType()).getName());
				cellList.add(TimeUtils.convertToDateString(relationTransferInfo.getTransferTime()));
				cellList.add(TimeUtils.convertToTimeString(relationTransferInfo.getTransferDoneTime()));
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
}
