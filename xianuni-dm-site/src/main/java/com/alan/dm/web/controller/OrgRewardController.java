package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.OrgRewardCondition;
import com.alan.dm.service.IAdminService;
import com.alan.dm.service.IOrgRewardService;
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

/**
 * 组织奖惩信息接口
 */
@Controller
@RequestMapping("/reward")
public class OrgRewardController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(OrgRewardController.class);

	@Resource(name = "orgRewardService")
	private IOrgRewardService orgRewardService;

	@Resource(name = "adminService")
	private IAdminService adminService;

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info.do")
	@ResponseBody
	public String info(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int rewardId=request.getInt("id");
		OrgReward  reward=orgRewardService.getById(rewardId);
		if(reward!=null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", true);
			Orgnization orgnization = orgnizationService.getOrgById(reward.getOrgId());
			jsonObject.put("orgName",orgnization.getName());
			jsonObject.put("name",reward.getName());
			jsonObject.put("rewardTime", TimeUtils.convertToDateString(reward.getRewardTime()));
			jsonObject.put("rewardDesc",reward.getRewardDesc());
			return JsonUtils.fromObject(jsonObject);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", false);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 添加奖惩信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public String add(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String name=request.getString("rewardName");
		String desc=request.getString("rewardDesc", null);
		int type=request.getInt("rewardType", OrgRewardType.REWARD.getId());
		int level=request.getInt("rewardLevel", OrgRewardLevel.SCHOOLE.getId());
		Date time=request.getDate("rewardTime");
		int orgId=request.getInt("orgId");

		OrgReward orgReward=new OrgReward();
		orgReward.setRewardDesc(desc);
		orgReward.setLevel(level);
		orgReward.setName(name);
		orgReward.setRewardTime(time);
		orgReward.setType(type);
		Orgnization orgnization=new Orgnization();
		orgnization.setId(orgId);
		orgReward.setOrgnization(orgnization);
		orgRewardService.addReward(orgReward);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 删除奖惩信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id",",");
		List<Integer> idList = new ArrayList<Integer>();
		for(String id:ids){
			idList.add(Integer.parseInt(id));
		}

		orgRewardService.deleteBatch(idList);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);

	}

	/**
	 * 根据组织分页获取奖惩信息
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
		String name=request.getString("name", null);
		boolean containSubOrg=request.getBoolean("containSub", true);

		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		OrgRewardCondition condition=new OrgRewardCondition();
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
		int subCount = orgRewardService.countByCondition(condition);

		List<OrgReward> rewardList=orgRewardService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(rewardList!=null){
			for(OrgReward reward:rewardList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", reward.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(reward.getId()));
				cellList.add(reward.getOrgnization().getName());
				cellList.add(reward.getName());
				cellList.add(OrgRewardType.getInstance(reward.getType()).getName());
				cellList.add(TimeUtils.convertToDateString(reward.getRewardTime()));
				cellList.add(OrgRewardLevel.getInstance(reward.getLevel()).getName());
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
}
