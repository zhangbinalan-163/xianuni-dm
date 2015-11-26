package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.CommitteeCondition;
import com.alan.dm.entity.condition.OrgRewardCondition;
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
@RequestMapping("/committee")
public class OrgCommitteeController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(OrgCommitteeController.class);

	@Resource(name = "committeeService")
	private ICommitteeService committeeService;

	@Resource(name = "personService")
	private IPersonService personService;

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	@Resource(name = "adminService")
	private IAdminService adminService;
	/**
	 *
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
		int job=request.getInt("job",CommitteeJobType.NORMAL.getId());
		Person person = personService.getByNumber(number);
		JSONObject jsonObject=new JSONObject();
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","用户不存在");
			return JsonUtils.fromObject(jsonObject);
		}
		Orgnization orgnization = orgnizationService.getOrgById(orgId);
		if(orgnization==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","部门不存在");
			return JsonUtils.fromObject(jsonObject);
		}
		CommitteeInfo committeeInfo=new CommitteeInfo();
		committeeInfo.setOrgnization(orgnization);
		committeeInfo.setPerson(person);
		committeeInfo.setJob(job);
		committeeService.createCommitteeInfo(committeeInfo);

		jsonObject.put("success", true);
		jsonObject.put("msg","success");
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
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
			CommitteeInfo committeeInfo=committeeService.getById(Integer.parseInt(id));
			if(committeeInfo!=null){
				committeeService.deleteCommitteeInfo(committeeInfo);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);

	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public String rewardList(HttpServletRequest httpServletRequest) throws Exception {
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
		CommitteeCondition condition=new CommitteeCondition();
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
	 * 查询可以成为管理员的党员信息
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
		page.setSize(20);
		page.setCurrent(0);

		List<Person> personList=committeeService.getCandidatePerson(Arrays.asList(orgnization),number,page);

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
