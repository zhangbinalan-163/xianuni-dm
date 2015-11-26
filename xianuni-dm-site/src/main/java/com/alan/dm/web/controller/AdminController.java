package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import com.alan.dm.entity.condition.AdminCondition;
import com.alan.dm.entity.condition.ApplierInfoCondition;
import com.alan.dm.entity.condition.PersonCondition;
import com.alan.dm.service.IAdminService;
import com.alan.dm.service.IOrgnizationService;
import com.alan.dm.service.IPersonService;
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
@RequestMapping("/admin")
public class AdminController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(AdminController.class);

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	@Resource(name = "adminService")
	private IAdminService adminService;

	@Resource(name = "personService")
	private IPersonService personService;

	@RequestMapping("/index.do")
	public String index(){
		return "orgnization/index";
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
		//检查当前用户权限 TODO
		Request request = getRequest(httpServletRequest);
		String[] adminIds= request.getStringArray("id",",");
		for(String adminId:adminIds){
			Admin adminInfo =adminService.getById(Integer.parseInt(adminId));
			if(adminInfo!=null){
				adminService.deleteAdmin(adminInfo);
			}
		}
		JSONObject orgObject=new JSONObject();
		orgObject.put("status","success");
		orgObject.put("message", "success");
		return JsonUtils.fromObject(orgObject);
	}

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
		String number = request.getString("number");
		int orgId=request.getInt("orgId");
		String password=request.getString("password");

		JSONObject jsonObject=new JSONObject();

		Person person=personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.NORMAL.getId()){
			jsonObject.put("success", false);
			jsonObject.put("msg", "该账号不能设置为管理员");
			return JsonUtils.fromObject(jsonObject);
		}

		Admin admin=new Admin();
		admin.setType(Admin.ORG_ADMIN);
		admin.setOrgId(orgId);
		admin.setName(number);
		admin.setPassword(password);
		admin.setCreateTime(new Date());
		admin.setStatus(Admin.STATUS_NORMAL);
		admin.setSchoolNumber(number);
		adminService.createAdmin(admin);

		jsonObject.put("success", true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update.do")
	@ResponseBody
	public String update(HttpServletRequest httpServletRequest) throws Exception {
		//TODO
		return null;
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info.do")
	@ResponseBody
	public String adminInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int adminId = request.getInt("adminId");
		Admin adminInfo = adminService.getById(adminId);
		JSONObject jsonObject=new JSONObject();
		if(adminInfo!=null){
			jsonObject.put("success",true);
			jsonObject.put("name",adminInfo.getName());
			jsonObject.put("number",adminInfo.getSchoolNumber());
			jsonObject.put("status",adminInfo.getStatus()==Admin.STATUS_NORMAL?"正常":"禁用");
			jsonObject.put("createTime",TimeUtils.convertToDateString(adminInfo.getCreateTime()));
			jsonObject.put("updateTime",TimeUtils.convertToDateString(adminInfo.getUpdateTime()));
			jsonObject.put("adminType", adminInfo.getType() == Admin.ORG_ADMIN ? "部门管理员" : "系统管理员");
			int orgId=adminInfo.getOrgId();
			Orgnization orgnization;
			if(orgId!=0){
				orgnization = orgnizationService.getOrgById(orgId);
				if(orgnization!=null){
					jsonObject.put("orgName",orgnization.getName());
				}
			}
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", false);
		jsonObject.put("msg", "不存在");
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 管理员列表
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public String list(HttpServletRequest httpServletRequest) throws Exception {
		//加入权限控制，
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		boolean containSubOrg=request.getBoolean("containSub", true);

		Integer adminId = getOnlineAdminId(httpServletRequest);
		Admin adminInfo = adminService.getById(adminId);
		AdminCondition condition=new AdminCondition();
		if(adminInfo.getType()==Admin.SYSTEM_ADMIN){
			if(orgId==0){
				//不指定部门，全部查询
			}else{
				List<Integer> orgIdList=new ArrayList<Integer>();
				orgIdList.add(orgId);
				Orgnization orgnization= orgnizationService.getOrgById(orgId);
				List<Orgnization> subOrgList =orgnizationService.getOrgByParent(orgnization, true);
				if(subOrgList!=null){
					for (Orgnization subOrg:subOrgList){
						orgIdList.add(subOrg.getId());
					}
				}
				condition.setOrgList(orgIdList);
			}
		}else if(adminInfo.getType()==Admin.ORG_ADMIN){
			if(orgId==0){
				orgId=adminInfo.getOrgId();
			}
			List<Integer> orgIdList=new ArrayList<Integer>();
			if(orgId!=adminInfo.getOrgId()){
				orgIdList.add(orgId);
			}
			Orgnization orgnization= orgnizationService.getOrgById(orgId);
			List<Orgnization> subOrgList =orgnizationService.getOrgByParent(orgnization, true);
			if(subOrgList!=null){
				for (Orgnization subOrg:subOrgList){
					orgIdList.add(subOrg.getId());
				}
			}
			condition.setOrgList(orgIdList);
		}else{
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("page",1);
			jsonObject.put("total",0);
			jsonObject.put("records",0);
			return JsonUtils.fromObject(jsonObject);
		}
		condition.setType(Admin.ORG_ADMIN);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);
		int count=adminService.countByCondition(condition);
		List<Admin> adminList = adminService.getByCondition(condition, pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",count==0?0:count/limit+1);
		jsonObject.put("records",count);
		JSONArray rowsArray=new JSONArray();
		if(adminList!=null){
			for(Admin admin:adminList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", admin.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(admin.getId()));
				cellList.add(String.valueOf(admin.getName()));
				cellList.add(String.valueOf(admin.getSchoolNumber()));
				cellList.add(admin.getStatus() == Admin.STATUS_NORMAL ? "正常" : "禁用");
				cellList.add(TimeUtils.convertToDateString(admin.getCreateTime()));
				cellList.add(admin.getType() == Admin.ORG_ADMIN ? "部门管理员" : "系统管理员");
				if(admin.getType()==Admin.ORG_ADMIN){
					cellList.add(orgnizationService.getOrgById(admin.getOrgId()).getName());
				}
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

		List<Person> personList=adminService.getCandidatePerson(Arrays.asList(orgnization),number,page);

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
