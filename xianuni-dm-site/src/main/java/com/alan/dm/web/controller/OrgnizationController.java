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
import java.util.ArrayList;
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
		String orgIds = request.getString("orgIds");
		String[] orgIdArray = orgIds.split(",");
		for(String orgId:orgIdArray){
			Orgnization orgnization = orgnizationService.getOrgById(Integer.parseInt(orgId));
			if(orgnization!=null){
				//orgnizationService.deleteOrg(orgnization,true);
			}
		}
		JSONObject orgObject=new JSONObject();
		orgObject.put("statusCode",200);
		orgObject.put("message","success");
		return JsonUtils.fromObject(orgObject);
	}

	@RequestMapping("/orglist.do")
	@ResponseBody
	public String orglist(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Orgnization parentOrg=new Orgnization();
		parentOrg.setId(1);
		Page pageInfo=new Page();
		pageInfo.setCurrent((page-1)*limit);
		pageInfo.setSize(limit);
		int subCount = orgnizationService.countSubOrg(parentOrg);
		List<Orgnization> subOrgList=orgnizationService.getOrgByParent(parentOrg,pageInfo);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(subOrgList!=null){
			for(Orgnization subOrg:subOrgList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", subOrg.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(subOrg.getId()));
				cellList.add(subOrg.getName());
				cellList.add(TimeUtils.convertToDateString(subOrg.getCreateTime()));
				cellList.add(TimeUtils.convertToDateString(subOrg.getUpdateTime()));
				cellList.add(TimeUtils.convertToDateString(subOrg.getElectionTime()));
				cellList.add(subOrg.getDesc());
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}


	@RequestMapping("/orgtree.do")
	@ResponseBody
	public String orgtree(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer parentId=request.getInt("id", -1);
		JSONArray orgArray=new JSONArray();
		if(parentId==-1){
			JSONObject allObject=new JSONObject();
			allObject.put("id",0);
			allObject.put("name","全部党组织");
			allObject.put("isParent",false);
			allObject.put("pId", -1);
			orgArray.add(allObject);
			//最外层
			Orgnization parentOrg=new Orgnization();
			parentOrg.setId(-1);
			List<Orgnization> topLevelList = orgnizationService.getOrgByParent(parentOrg,null);
			if(topLevelList!=null){
				for(Orgnization orgnization:topLevelList){
					JSONObject orgObject=new JSONObject();
					orgObject.put("id",orgnization.getId());
					orgObject.put("name",orgnization.getName());
					orgObject.put("isParent",orgnization.getIsParent()==1);
					orgObject.put("open",true);
					orgObject.put("pId", -1);
					orgArray.add(orgObject);
					if(orgnization.getIsParent()==1){
						List<Orgnization> subOrgList = orgnizationService.getOrgByParent(orgnization,null);
						if(subOrgList!=null){
							for(Orgnization subOrg:subOrgList){
								JSONObject subOrgObject=new JSONObject();
								subOrgObject.put("id",subOrg.getId());
								subOrgObject.put("name",subOrg.getName());
								subOrgObject.put("isParent",subOrg.getIsParent()==1);
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
				List<Orgnization> subOrgList = orgnizationService.getOrgByParent(orgnization,null);
				if(subOrgList!=null){
					for(Orgnization subOrg:subOrgList){
						JSONObject subOrgObject=new JSONObject();
						subOrgObject.put("id",subOrg.getId());
						subOrgObject.put("name",subOrg.getName());
						subOrgObject.put("isParent",subOrg.getIsParent()==1);
						subOrgObject.put("pId",orgnization.getId());
						orgArray.add(subOrgObject);
					}
				}
			}
		}
		return JsonUtils.fromObject(orgArray);
	}
}
