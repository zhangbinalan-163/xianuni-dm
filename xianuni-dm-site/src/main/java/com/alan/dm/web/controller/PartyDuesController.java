package com.alan.dm.web.controller;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.exception.InvalidParamException;
import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.PartyDuesCondition;
import com.alan.dm.service.IAdminService;
import com.alan.dm.service.IOrgnizationService;
import com.alan.dm.service.IPartyDuesService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dues")
public class PartyDuesController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(PartyDuesController.class);

	@Resource(name = "partyDuesService")
	private IPartyDuesService partyDuesService;

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
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int id = request.getInt("id");

		PartyDuesPay partyDuesPay=new PartyDuesPay();
		partyDuesPay.setId(id);

		partyDuesService.deletePay(partyDuesPay);

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
	@RequestMapping("/add.do")
	@ResponseBody
	public String add(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Date startTime = getYM(request, "startTime");
		Date endTime = getYM(request, "endTime");
		Date payTime = request.getDate("payTime");
		int orgId=request.getInt("orgId");
		String number=request.getString("number");
		Float fee;
		try{
			fee=Float.parseFloat(request.getString("fee"));
		}catch (Exception e){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg", "费用格式错误");
			return JsonUtils.fromObject(jsonObject);
		}
		Person person=personService.getByNumber(number);

		PartyDuesPay partyDues=new PartyDuesPay();
		partyDues.setPerson(person);
		partyDues.setPayEndTime(endTime);
		partyDues.setPayStartTime(startTime);
		partyDues.setPayTime(payTime == null ? new Date() : payTime);
		partyDues.setFee(fee);
		partyDuesService.involvePay(partyDues);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		return JsonUtils.fromObject(jsonObject);
	}

	private Date getYM(Request request,String param) throws DMException {
		String value=request.getString(param);
		value=value+"-01";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		try {
			return dateFormat.parse(value);
		} catch (Exception e) {
			throw new InvalidParamException("参数缺失或格式错误:" + param);
		}
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public String dueList(HttpServletRequest httpServletRequest) throws Exception {
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
		PartyDuesCondition condition=new PartyDuesCondition();
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
		condition.setStatus(Arrays.asList(PersonStatus.PERPARE.getId(), PersonStatus.NORMAL.getId()));
		int subCount = partyDuesService.countByCondition(condition);

		List<PartyDuesPay> duesPayList = partyDuesService.getByCondition(condition, pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(duesPayList!=null){
			for(PartyDuesPay duesPay:duesPayList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", duesPay.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(duesPay.getId()));
				Person person=duesPay.getPerson();
				Orgnization org = person.getOrgnization();
				cellList.add(org.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(String.valueOf(duesPay.getFee()));
				cellList.add(TimeUtils.convertToDateString(duesPay.getPayTime()));
				cellList.add(TimeUtils.convertToDateString(duesPay.getPayStartTime(), "yyyy-MM"));
				cellList.add(TimeUtils.convertToDateString(duesPay.getPayEndTime(),"yyyy-MM"));
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
}
