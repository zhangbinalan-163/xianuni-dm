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
			dataObject.put("hasSub", orgnization.getIsParent()!=0?"是":"否");
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
				List<Orgnization> subOrgList = orgnizationService.getOrgByParent(orgnization, null);
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
