package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.RelationTransferCondition;
import com.alan.dm.service.IAdminService;
import com.alan.dm.service.IOrgnizationService;
import com.alan.dm.service.IPersonService;
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
		String orgName=request.getString("orgName");
		String name=request.getString("name");
		String number=request.getString("number");
		Integer type=request.getInt("type", 0);
		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		if(type==1){
			//校外转入
			Person person = personService.getByNumber(number);
			if(person!=null){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success",false);
				jsonObject.put("msg","该学号已经存在");
				return JsonUtils.fromObject(jsonObject);
			}
			//先创建党员信息
			person=new Person();
			person.setNumber(number);
			person.setSource(type == 1 ? 1 : 0);
			person.setOrgnization(orgnization);
			person.setPersonDesc("校外转入");
			person.setName(name);
			person.setStatus(PersonStatus.NORMAL.getId());
			personService.createPerson(person);
			//创建转入记录
			RelationTransferInfo transferInfo=new RelationTransferInfo();
			transferInfo.setTransferType(RelationTransferType.IN_OTHER.getId());
			transferInfo.setPerson(person);
			transferInfo.setFromOrgName(orgName);
			transferInfo.setTransferTime(new Date());
			transferInfo.setToOrgId(orgnization.getId());
			transferInfo.setToOrgName(orgnization.getName());
			relationTransferService.createTransfer(transferInfo);
		}else{
			//

		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		jsonObject.put("msg","success");
		return JsonUtils.fromObject(jsonObject);
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
