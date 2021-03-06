package com.alan.dm.web.controller;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.CommitteeCondition;
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

/**
 * 委员会相关接口
 */
@Controller
@RequestMapping("/committee")
public class OrgCommitteeController extends BaseController{

	@Resource(name = "committeeService")
	private ICommitteeService committeeService;

	@Resource(name = "personService")
	private IPersonService personService;

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	@Resource(name = "adminService")
	private IAdminService adminService;

	/**
	 * 委员会信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info.do")
	@ResponseBody
	public String info(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int orgId=request.getInt("id");
		CommitteeInfo committeeInfo = committeeService.getById(orgId);
		JSONObject jsonObject=new JSONObject();
		if(committeeInfo!=null){
			jsonObject.put("success", true);
			Person person = personService.getById(committeeInfo.getPersonId());
			Orgnization orgnization = orgnizationService.getOrgById(person.getOrgId());
			jsonObject.put("orgName",orgnization.getName());
			jsonObject.put("personName",person.getName());
			jsonObject.put("number",person.getNumber());
			jsonObject.put("job",CommitteeJobType.getInstance(committeeInfo.getJob()).getName());
			jsonObject.put("desc",committeeInfo.getCdesc());
			jsonObject.put("tel", committeeInfo.getTel());
			jsonObject.put("level","第"+committeeInfo.getLevel()+"届");
			jsonObject.put("createTime", TimeUtils.convertToTimeString(committeeInfo.getCreateTime()));
			jsonObject.put("profession",committeeInfo.getProfession());
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		jsonObject.put("msg","success");
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 新增委员会信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public String add(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);

		int orgId=request.getInt("orgId");
		String number=request.getString("number");
		int job=request.getInt("job", CommitteeJobType.NORMAL.getId());
		String tel=request.getString("tel",null);
		String profession=request.getString("profession",null);
		int level=request.getInt("level", 1);
		String desc=request.getString("desc",null);

		Person person = personService.getByNumber(number);
		JSONObject jsonObject=new JSONObject();
		if(person==null||(person.getStatus()!=PersonStatus.PERPARE.getId()&&person.getStatus()!=PersonStatus.NORMAL.getId()&&person.getStatus()!=PersonStatus.FORMAL.getId())){
			throw new DMException("该学工号不存在或者不是正式党员");
		}
		//todo 对权限的检查，当前管理员只能管理有权限的组织和下属组织
		Orgnization orgnization = orgnizationService.getOrgById(orgId);
		if(orgnization==null){
			throw new DMException("部门不存在");
		}

		CommitteeInfo committeeInfo=new CommitteeInfo();
		committeeInfo.setOrgnization(orgnization);
		committeeInfo.setPerson(person);
		committeeInfo.setJob(job);
		committeeInfo.setCreateTime(new Date());
		committeeInfo.setCdesc(desc);
		committeeInfo.setLevel(level);
		committeeInfo.setProfession(profession);
		committeeInfo.setTel(tel);

		committeeService.createCommitteeInfo(committeeInfo);

		jsonObject.put("success", true);
		jsonObject.put("msg","success");
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 删除委员会任职信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id",",");
		for(String id:ids){
			CommitteeInfo committeeInfo=new CommitteeInfo();
			committeeInfo.setId(Integer.parseInt(id));
			if(committeeInfo!=null){
				committeeService.deleteCommitteeInfo(committeeInfo);
			}
		}
		//为了兼容jqgrid的框架，用status字段返回
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 查询委员会列表信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public String list(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId",0);
		String number=request.getString("number", null);//可以按照学号查询
		boolean containSubOrg=request.getBoolean("containSub", true);

		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		CommitteeCondition condition=new CommitteeCondition();
		if(orgId!=0){
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
		}

		Page pageInfo=new Page();
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		int subCount = committeeService.countByCondition(condition);

		List<CommitteeInfo> committeeInfos=committeeService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(committeeInfos!=null){
			for(CommitteeInfo committeeInfo:committeeInfos){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", committeeInfo.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(committeeInfo.getId()));
				cellList.add(committeeInfo.getOrgnization().getName());
				cellList.add(committeeInfo.getPerson().getName());
				cellList.add(committeeInfo.getPerson().getNumber());
				cellList.add(CommitteeJobType.getInstance(committeeInfo.getJob()).getName());
				cellList.add(TimeUtils.convertToDateString(committeeInfo.getCreateTime()));
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 查询某个部门和所有的子部门中可以成为委员会成员的党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/candidateList.do")
	@ResponseBody
	public String candidateList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId=request.getInt("orgId");
		String number=request.getString("number", null);

		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		Page page=new Page();
		page.setSize(20);//最多获取20个
		page.setCurrent(0);

		List<Orgnization> orgsList=new ArrayList<Orgnization>();
		orgsList.add(orgnization);

		List<Orgnization> subOrgs=orgnizationService.getOrgByParent(orgnization,true);
		orgsList.addAll(subOrgs);

		List<Person> personList=committeeService.getCandidatePerson(orgsList,number,page);

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
}
