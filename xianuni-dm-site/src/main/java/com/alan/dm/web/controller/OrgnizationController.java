package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.query.OrgnizationCondition;
import com.alan.dm.entity.query.PersonCondition;
import com.alan.dm.entity.query.PersonResult;
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
import java.util.*;

@Controller
@RequestMapping("/orgnization")
public class OrgnizationController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(OrgnizationController.class);

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	@Resource(name = "adminService")
	private IAdminService adminService;

	@Resource(name = "personService")
	private IPersonService personService;

	@RequestMapping("/allWithParent.do")
	@ResponseBody
	public String listWithParent(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String name=request.getString("name", null);

		OrgnizationCondition condition=new OrgnizationCondition();
		condition.setStatus(Arrays.asList(Orgnization.NORMAL));
		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}

		Page pageInfo=new Page();
		pageInfo.setCurrent(0);
		pageInfo.setSize(20);

		List<Orgnization> orgnizationList = orgnizationService.getByCondition(condition, pageInfo);
		JSONArray rowsArray=new JSONArray();
		if(orgnizationList!=null){
			for(Orgnization orgnization:orgnizationList){
				JSONObject keyObject=new JSONObject();
				keyObject.put("orgId",orgnization.getId());
				List<Orgnization> parentList = orgnizationService.getParentOrg(orgnization);
				StringBuffer sb=new StringBuffer();
				if(parentList!=null&&parentList.size()>0){
					List<Orgnization> seqParentList=new LinkedList<Orgnization>();
					for(int i=(parentList.size()-1);i>=0;i--){
						seqParentList.add(parentList.get(i));
					}
					for(Orgnization parentOrg:seqParentList){
						sb.append(parentOrg.getName()).append("-->");
					}
				}
				keyObject.put("orgName",sb.append(orgnization.getName()));
				keyObject.put("orginOrgName",orgnization.getName());
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
	 * 党组织
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listQuery.do")
	@ResponseBody
	public String listQuery(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String name=request.getString("name", null);

		OrgnizationCondition condition=new OrgnizationCondition();
		condition.setStatus(Arrays.asList(Orgnization.NORMAL));
		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}

		Page pageInfo=new Page();
		pageInfo.setCurrent(0);
		pageInfo.setSize(10);

		List<Orgnization> orgnizationList = orgnizationService.getByCondition(condition, pageInfo);
		JSONArray rowsArray=new JSONArray();
		if(orgnizationList!=null){
			for(Orgnization orgnization:orgnizationList){
				JSONObject keyObject=new JSONObject();
				keyObject.put("orgId",orgnization.getId());
				keyObject.put("orgName",orgnization.getName());
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
	 * 删除组织
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");

		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		//如果有子节点，不可以删除
		if(orgnizationService.countSubOrg(orgnization)>0){
			JSONObject orgObject=new JSONObject();
			orgObject.put("success", false);
			orgObject.put("msg", "有下属组织，不可删除");
			return JsonUtils.fromObject(orgObject);
		}
		//
		PersonCondition condition=new PersonCondition();
		condition.setOrgList(Arrays.asList(orgId));
		if(personService.countByCondition(condition)>0){
			JSONObject orgObject=new JSONObject();
			orgObject.put("success", false);
			orgObject.put("msg", "党组织下有党员信息，不可删除");
			return JsonUtils.fromObject(orgObject);
		}

		//级联删除所有节点
		orgnizationService.deleteOrg(orgnization, true);
		//设置父节点是否还为空
		Orgnization parentOrg=orgnizationService.getOrgById(orgnization.getParent());
		if(parentOrg!=null&&orgnizationService.countSubOrg(parentOrg)==0){
			parentOrg.setHasSon(false);
			orgnizationService.updateOrg(parentOrg);
		}
		JSONObject orgObject=new JSONObject();
		orgObject.put("success", 200);
		orgObject.put("msg", "success");
		return JsonUtils.fromObject(orgObject);
	}

	/**
	 * 撤销党组织
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chexiao.do")
	@ResponseBody
	public String chexiao(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");

		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		//级联删除所有节点
		orgnizationService.removeOrg(orgnization,true);
		//设置父节点是否还为空
		JSONObject orgObject=new JSONObject();
		orgObject.put("success", 200);
		orgObject.put("msg", "success");
		return JsonUtils.fromObject(orgObject);
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
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String orgName=request.getString("orgName");

		Orgnization orgnization = orgnizationService.getOrgById(orgId);
		JSONObject orgObject=new JSONObject();
		if(orgnization==null){
			orgObject.put("success",false);
			orgObject.put("msg","部门不存在");
			return JsonUtils.fromObject(orgObject);
		}else{
			String evaTime=request.getString("evaTime",null);
			if(!StringUtils.isEmpty(evaTime)){
				orgnization.setElectionTime(request.getDate("evaTime"));
			}
			orgnization.setName(orgName);
			orgnizationService.updateOrg(orgnization);
			orgObject.put("success", 200);
			orgObject.put("msg", "success");
			return JsonUtils.fromObject(orgObject);
		}
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
		Integer orgId = request.getInt("orgId");
		String orgName=request.getString("orgName");

		Orgnization parentOrg = orgnizationService.getOrgById(orgId);
		JSONObject orgObject=new JSONObject();
		if(parentOrg==null){
			orgObject.put("success",false);
			orgObject.put("msg","父部门不存在");
			return JsonUtils.fromObject(orgObject);
		}else{
			Orgnization orgnization=new Orgnization();
			orgnization.setParent(parentOrg.getId());
			orgnization.setCreateTime(new Date());
			orgnization.setHasSon(false);
			orgnization.setName(orgName);
			orgnizationService.createOrg(orgnization);

			//
			parentOrg.setHasSon(true);
			orgnizationService.updateOrg(parentOrg);

			orgObject.put("success", 200);
			orgObject.put("id",orgnization.getId());
			orgObject.put("msg", "success");
			return JsonUtils.fromObject(orgObject);
		}
	}

	@RequestMapping("/info.do")
	@ResponseBody
	public String info(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId", 0);

		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		if(orgnization!=null){
			JSONObject dataObject=new JSONObject();
			dataObject.put("name",orgnization.getName());
			dataObject.put("status", orgnization.getStatus()==Orgnization.CANCEL?"已撤销":"正常");
			dataObject.put("createTime", TimeUtils.convertToDateString(orgnization.getCreateTime()));
			dataObject.put("updateTime", TimeUtils.convertToDateString(orgnization.getUpdateTime()));
			dataObject.put("evaTime", TimeUtils.convertToDateString(orgnization.getElectionTime()));
			dataObject.put("removeTime", TimeUtils.convertToTimeString(orgnization.getCancelTime()));
			dataObject.put("isParent", orgnization.isHasSon()?"是":"否");
			dataObject.put("orgId", orgnization.getId());
			jsonObject.put("data",dataObject);
		}
		return JsonUtils.fromObject(jsonObject);
	}


	@RequestMapping("/orgtree.do")
	@ResponseBody
	public String orgtree(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer parentId=request.getInt("id", -1);
		JSONArray orgArray=new JSONArray();
		if(parentId==-1){
			Orgnization rootOrg=new Orgnization();
			//获取自己所能管理的所有一级部门
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin admin=adminService.getById(adminId);
			if(admin==null){
				return JsonUtils.fromObject(orgArray);
			} else if(admin.getType()==Admin.ORG_ADMIN){
				//部门管理员
				int rootOrgId = admin.getOrgId();
				if(rootOrgId==0){
					return JsonUtils.fromObject(orgArray);
				}
				rootOrg=orgnizationService.getOrgById(rootOrgId);
				if(rootOrg!=null){
					JSONObject orgObject=new JSONObject();
					orgObject.put("id",rootOrg.getId());
					orgObject.put("name",rootOrg.getName());
					orgObject.put("isParent",rootOrg.isHasSon());
					orgObject.put("open",true);
					orgObject.put("pId", -1);
					orgArray.add(orgObject);
				}
				if(rootOrg.isHasSon()){
					List<Orgnization> subOrgList = orgnizationService.getOrgByParent(rootOrg,false);
					if(subOrgList!=null){
						for(Orgnization subOrg:subOrgList){
							JSONObject subOrgObject=new JSONObject();
							subOrgObject.put("id",subOrg.getId());
							subOrgObject.put("name",subOrg.getName());
							subOrgObject.put("isParent",subOrg.isHasSon());
							subOrgObject.put("pId",rootOrg.getId());
							orgArray.add(subOrgObject);
						}
					}
				}
			}else if(admin.getType()==Admin.SYSTEM_ADMIN){
				//系统管理员
				rootOrg.setId(-1);
				List<Orgnization> topLevelList = orgnizationService.getOrgByParent(rootOrg,false);
				if(topLevelList!=null){
					for(Orgnization orgnization:topLevelList){
						JSONObject orgObject=new JSONObject();
						orgObject.put("id",orgnization.getId());
						orgObject.put("name",orgnization.getName());
						orgObject.put("isParent",orgnization.isHasSon());
						orgObject.put("open",true);
						orgObject.put("pId", -1);
						orgArray.add(orgObject);
						if(orgnization.isHasSon()){
							List<Orgnization> subOrgList = orgnizationService.getOrgByParent(orgnization,false);
							if(subOrgList!=null){
								for(Orgnization subOrg:subOrgList){
									JSONObject subOrgObject=new JSONObject();
									subOrgObject.put("id",subOrg.getId());
									subOrgObject.put("name",subOrg.getName());
									subOrgObject.put("isParent",subOrg.isHasSon());
									subOrgObject.put("pId",orgnization.getId());
									orgArray.add(subOrgObject);
								}
							}
						}
					}
				}
			}
		}else{
			//检查权限 TODO
			Orgnization orgnization=orgnizationService.getOrgById(parentId);
			if(orgnization!=null){
				List<Orgnization> subOrgList = orgnizationService.getOrgByParent(orgnization,false);
				if(subOrgList!=null){
					for(Orgnization subOrg:subOrgList){
						JSONObject subOrgObject=new JSONObject();
						subOrgObject.put("id",subOrg.getId());
						subOrgObject.put("name",subOrg.getName());
						subOrgObject.put("isParent",subOrg.isHasSon());
						subOrgObject.put("pId",orgnization.getId());
						orgArray.add(subOrgObject);
					}
				}
			}
		}
		return JsonUtils.fromObject(orgArray);
	}
}
