package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.service.IOrgnizationService;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orgnization")
public class OrgnizationController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(OrgnizationController.class);

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	@RequestMapping("/index.do")
	public String index(){
		return "orgnization/index";
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");

		Orgnization orgnization=new Orgnization();
		orgnization.setId(orgId);
		//级联删除所有节点
		orgnizationService.deleteOrg(orgnization,true);
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
	public String orglist(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId", 0);

		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		if(orgnization!=null){
			JSONObject dataObject=new JSONObject();
			dataObject.put("name",orgnization.getName());
			dataObject.put("createTime", TimeUtils.convertToTimeString(orgnization.getCreateTime()));
			dataObject.put("updateTime", TimeUtils.convertToTimeString(orgnization.getUpdateTime()));
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
		boolean withAll = request.getBoolean("withAll",true);
		JSONArray orgArray=new JSONArray();
		if(parentId==-1){
			if(withAll){
				JSONObject allObject=new JSONObject();
				allObject.put("id",0);
				allObject.put("name","全部党组织");
				allObject.put("isParent",true);
				allObject.put("pId", -1);
				orgArray.add(allObject);
			}
			//最外层
			Orgnization parentOrg=new Orgnization();
			parentOrg.setId(-1);
			List<Orgnization> topLevelList = orgnizationService.getOrgByParent(parentOrg,null);
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
						List<Orgnization> subOrgList = orgnizationService.getOrgByParent(orgnization,null);
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
		}else{
			Orgnization orgnization=orgnizationService.getOrgById(parentId);
			if(orgnization!=null){
				List<Orgnization> subOrgList = orgnizationService.getOrgByParent(orgnization, null);
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
