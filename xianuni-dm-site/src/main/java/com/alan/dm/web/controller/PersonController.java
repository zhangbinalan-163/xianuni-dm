package com.alan.dm.web.controller;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.Csv;
import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.PrepareInfo;
import com.alan.dm.entity.query.PersonCondition;
import com.alan.dm.entity.query.PersonResult;
import com.alan.dm.service.*;
import com.alan.dm.web.vo.Request;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Controller
@RequestMapping("/person")
public class PersonController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(PersonController.class);

	@Resource(name = "personService")
	private IPersonService personService;

	@Resource(name = "applierService")
	private IApplierService applierService;

	@Resource(name = "activitistService")
	private IActivitistService activitistService;

	@Resource(name = "intentionService")
	private IIntentionService intentionService;

	@Resource(name = "prepareService")
	private IPrepareService prepareService;

	@Resource(name = "normalService")
	private INormalService normalService;

	@Resource(name = "adminService")
	private IAdminService adminService;

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	/**
	 * 获取基本信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicInfo.do")
	@ResponseBody
	public String basicInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("personId");

		Person person = personService.getById(personId);
		JSONObject jsonObject=new JSONObject();
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName",orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToDateString(person.getCreateTime()));
		jsonObject.put("updateTime",TimeUtils.convertToDateString(person.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("type",person.getType());
		jsonObject.put("idNumber",person.getIdNumber());
		jsonObject.put("sex",person.getSex());
		jsonObject.put("nation",person.getNation());
		jsonObject.put("degree",person.getDegree());
		jsonObject.put("hometown",person.getHometown());
		jsonObject.put("birth",TimeUtils.convertToDateString(person.getBirth()));
		jsonObject.put("profession",person.getProfession());
		jsonObject.put("desc",person.getPersonDesc());
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 获取在编党员的全部信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/formal/info.do")
	@ResponseBody
	public String formalInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("personId");

		Person person = personService.getById(personId);
		JSONObject jsonObject=new JSONObject();
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName",orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToTimeString(person.getCreateTime()));
		jsonObject.put("updateTime",TimeUtils.convertToDateString(person.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("type",person.getType());
		jsonObject.put("idNumber",person.getIdNumber());
		jsonObject.put("sex",person.getSex());
		jsonObject.put("nation",person.getNation());
		jsonObject.put("degree", person.getDegree());
		jsonObject.put("hometown",person.getHometown());
		jsonObject.put("birth",TimeUtils.convertToDateString(person.getBirth()));
		jsonObject.put("bePartyTime",TimeUtils.convertToDateString(person.getBePatyDate()));
		jsonObject.put("rollTime",TimeUtils.convertToDateString(person.getRollDate()));
		jsonObject.put("profession",person.getProfession());
		jsonObject.put("desc",person.getPersonDesc());
		//如果是本校发展而来的，获取其他信息
		if(person.getSource()!=Person.SOURCE_DEFAULT){
			return JsonUtils.fromObject(jsonObject);
		}
		//申请人信息
		ApplierInfo applierInfo = applierService.getById(person.getApplierInfoId());
		jsonObject.put("applier_applyTime", TimeUtils.convertToDateString(applierInfo.getApplyTime()));
		jsonObject.put("applier_talkContent", applierInfo.getTalkContent());
		jsonObject.put("applier_talkTime", TimeUtils.convertToDateString(applierInfo.getTalkTime()));
		jsonObject.put("applier_degree",DegreeType.getInstance(applierInfo.getDegree()).getName());
		jsonObject.put("applier_profession", applierInfo.getProfession());
		String talkerIds=applierInfo.getTalkerIds();
		if(!StringUtils.isEmpty(talkerIds)){
			StringBuffer sb=new StringBuffer();
			String[] talkerIdArray = talkerIds.split(",");
			for(String talkerId:talkerIdArray){
				Person director = personService.getById(Integer.parseInt(talkerId));
				if(director!=null){
					sb.append(director.getName()).append(", ");
				}
			}
			String talkerName=sb.toString();
			talkerName=(talkerName.length()==0?talkerName:talkerName.substring(0,talkerName.lastIndexOf(",")));
			jsonObject.put("applier_talker",talkerName);
		}
		//积极分子信息
		ActivitistInfo activitistInfo=activitistService.getById(person.getActivitistInfoId());
		jsonObject.put("activitist_evaTime", TimeUtils.convertToDateString(activitistInfo.getEvaluationTime()));
		jsonObject.put("activitist_evaContent", activitistInfo.getEvaluationContent());
		jsonObject.put("activitist_meetTime", TimeUtils.convertToDateString(activitistInfo.getMeetTime()));
		jsonObject.put("activitist_meetContent",activitistInfo.getMeetContent());
		jsonObject.put("activitist_degree", DegreeType.getInstance(activitistInfo.getDegree()).getName());
		jsonObject.put("activitist_profession", activitistInfo.getProfession());
		jsonObject.put("activitist_talked", activitistInfo.isTalked()?"是":"否");
		jsonObject.put("activitist_beian",activitistInfo.isRecorded()?"是":"否");
		jsonObject.put("activitist_quntuan", activitistInfo.isQuntuan()?"是":"否");
		String directorIds=activitistInfo.getDirectorIds();
		if(!StringUtils.isEmpty(directorIds)){
			StringBuffer sb=new StringBuffer();
			String[] talkerIdArray = directorIds.split(",");
			for(String talkerId:talkerIdArray){
				Person director = personService.getById(Integer.parseInt(talkerId));
				if(director!=null){
					sb.append(director.getName()).append(", ");
				}
			}
			String talkerName=sb.toString();
			talkerName=(talkerName.length()==0?talkerName:talkerName.substring(0,talkerName.lastIndexOf(",")));
			jsonObject.put("activitist_director",talkerName);
		}
		//发展对象信息
		IntentionInfo intentionInfo = intentionService.getById(person.getIntentionInfoId());
		jsonObject.put("intention_trainHour", intentionInfo.getTrainHour());
		jsonObject.put("intention_publiced", intentionInfo.isPubliced()?"是":"否");
		jsonObject.put("intention_meetTime", TimeUtils.convertToDateString(intentionInfo.getMeetTime()));
		jsonObject.put("intention_meetContent", intentionInfo.getMeetContent());
		jsonObject.put("intention_politicalCheck", intentionInfo.getPoliticalCheckContent());
		jsonObject.put("intention_schoolApproval", intentionInfo.getSchoolApproval());
		jsonObject.put("intention_yushen", intentionInfo.isYushen()?"是":"否");
		jsonObject.put("intention_beian",intentionInfo.isRecorded()?"是":"否");
		jsonObject.put("intention_profession",intentionInfo.getProfession());
		jsonObject.put("intention_degree",DegreeType.getInstance(intentionInfo.getDegree()).getName());
		jsonObject.put("intention_beAcTime", TimeUtils.convertToDateString(intentionInfo.getBeAcTime()));
		String introducerIds=intentionInfo.getIntroducerIds();
		if(!StringUtils.isEmpty(introducerIds)){
			StringBuffer sb=new StringBuffer();
			String[] talkerIdArray = introducerIds.split(",");
			for(String talkerId:talkerIdArray){
				Person director = personService.getById(Integer.parseInt(talkerId));
				if(director!=null){
					sb.append(director.getName()).append(", ");
				}
			}
			String talkerName=sb.toString();
			talkerName=(talkerName.length()==0?talkerName:talkerName.substring(0,talkerName.lastIndexOf(",")));
			jsonObject.put("intention_introducer",talkerName);
		}
		//预备党员信息
		PrepareInfo prepareInfo = prepareService.getById(person.getPrepareInfoId());
		jsonObject.put("prepare_application", prepareInfo.isApplication()?"是":"否");
		jsonObject.put("prepare_meetTime", TimeUtils.convertToDateString(prepareInfo.getMeetTime()));
		jsonObject.put("prepare_meetContent", prepareInfo.getMeetContent());
		jsonObject.put("prepare_schoolApproval", prepareInfo.getSchoolApproval());
		jsonObject.put("prepare_branchApproval", prepareInfo.getBranchApproval());
		jsonObject.put("prepare_profession", prepareInfo.getProfession());
		jsonObject.put("prepare_degree", DegreeType.getInstance(prepareInfo.getDegree()).getName());
		jsonObject.put("prepare_yushen", prepareInfo.isYushen()?"是":"否");
		jsonObject.put("prepare_publiced",prepareInfo.isPubliced()?"是":"否");
		//正式党员信息
		NormalInfo normalInfo = normalService.getById(person.getNormalInfoId());
		jsonObject.put("normal_schoolApproval", normalInfo.getSchoolApproval());
		jsonObject.put("normal_branchApproval", normalInfo.getBranchApproval());
		jsonObject.put("normal_profession", normalInfo.getProfession());
		jsonObject.put("normal_degree", DegreeType.getInstance(normalInfo.getDegree()).getName());
		jsonObject.put("normal_applyTime", TimeUtils.convertToDateString(normalInfo.getApplyTime()));
		jsonObject.put("normal_meetTime", TimeUtils.convertToDateString(normalInfo.getMeetTime()));
		jsonObject.put("normal_meetContent", normalInfo.getMeetContent());

		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 获取在编党员的全部信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/formal/infoV2.do")
	@ResponseBody
	public String formalInfoV2(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("personId");

		Person person = personService.getById(personId);
		JSONObject jsonObject=new JSONObject();
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName",orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToTimeString(person.getCreateTime()));
		jsonObject.put("updateTime",TimeUtils.convertToDateString(person.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("type",PersonType.getInstance(person.getType()).getName());
		jsonObject.put("idNumber",person.getIdNumber());
		jsonObject.put("sex",person.getSex()==0?"男":"女");
		jsonObject.put("nation",NationType.getInstance(person.getNation()).getName());
		jsonObject.put("degree", DegreeType.getInstance(person.getDegree()).getName());
		jsonObject.put("hometown",person.getHometown());
		jsonObject.put("birth",TimeUtils.convertToDateString(person.getBirth()));
		jsonObject.put("bePartyTime",TimeUtils.convertToDateString(person.getBePatyDate()));
		jsonObject.put("rollTime",TimeUtils.convertToDateString(person.getRollDate()));
		jsonObject.put("profession",person.getProfession());
		jsonObject.put("desc",person.getPersonDesc());
		//如果是本校发展而来的，获取其他信息
		if(person.getSource()!=Person.SOURCE_DEFAULT){
			return JsonUtils.fromObject(jsonObject);
		}
		//申请人信息
		ApplierInfo applierInfo = applierService.getById(person.getApplierInfoId());
		jsonObject.put("applier_applyTime", TimeUtils.convertToDateString(applierInfo.getApplyTime()));
		jsonObject.put("applier_talkContent", applierInfo.getTalkContent());
		jsonObject.put("applier_talkTime", TimeUtils.convertToDateString(applierInfo.getTalkTime()));
		jsonObject.put("applier_degree",DegreeType.getInstance(applierInfo.getDegree()).getName());
		jsonObject.put("applier_profession", applierInfo.getProfession());
		String talkerIds=applierInfo.getTalkerIds();
		if(!StringUtils.isEmpty(talkerIds)){
			StringBuffer sb=new StringBuffer();
			String[] talkerIdArray = talkerIds.split(",");
			for(String talkerId:talkerIdArray){
				Person director = personService.getById(Integer.parseInt(talkerId));
				if(director!=null){
					sb.append(director.getName()).append(", ");
				}
			}
			String talkerName=sb.toString();
			talkerName=(talkerName.length()==0?talkerName:talkerName.substring(0,talkerName.lastIndexOf(",")));
			jsonObject.put("applier_talker",talkerName);
		}
		//积极分子信息
		ActivitistInfo activitistInfo=activitistService.getById(person.getActivitistInfoId());
		jsonObject.put("activitist_evaTime", TimeUtils.convertToDateString(activitistInfo.getEvaluationTime()));
		jsonObject.put("activitist_evaContent", activitistInfo.getEvaluationContent());
		jsonObject.put("activitist_meetTime", TimeUtils.convertToDateString(activitistInfo.getMeetTime()));
		jsonObject.put("activitist_meetContent",activitistInfo.getMeetContent());
		jsonObject.put("activitist_degree", DegreeType.getInstance(activitistInfo.getDegree()).getName());
		jsonObject.put("activitist_profession", activitistInfo.getProfession());
		jsonObject.put("activitist_talked", activitistInfo.isTalked()?"是":"否");
		jsonObject.put("activitist_beian",activitistInfo.isRecorded()?"是":"否");
		jsonObject.put("activitist_quntuan", activitistInfo.isQuntuan()?"是":"否");
		String directorIds=activitistInfo.getDirectorIds();
		if(!StringUtils.isEmpty(directorIds)){
			StringBuffer sb=new StringBuffer();
			String[] talkerIdArray = directorIds.split(",");
			for(String talkerId:talkerIdArray){
				Person director = personService.getById(Integer.parseInt(talkerId));
				if(director!=null){
					sb.append(director.getName()).append(", ");
				}
			}
			String talkerName=sb.toString();
			talkerName=(talkerName.length()==0?talkerName:talkerName.substring(0,talkerName.lastIndexOf(",")));
			jsonObject.put("activitist_director",talkerName);
		}
		//发展对象信息
		IntentionInfo intentionInfo = intentionService.getById(person.getIntentionInfoId());
		jsonObject.put("intention_trainHour", intentionInfo.getTrainHour());
		jsonObject.put("intention_publiced", intentionInfo.isPubliced()?"是":"否");
		jsonObject.put("intention_meetTime", TimeUtils.convertToDateString(intentionInfo.getMeetTime()));
		jsonObject.put("intention_meetContent", intentionInfo.getMeetContent());
		jsonObject.put("intention_politicalCheck", intentionInfo.getPoliticalCheckContent());
		jsonObject.put("intention_schoolApproval", intentionInfo.getSchoolApproval());
		jsonObject.put("intention_yushen", intentionInfo.isYushen()?"是":"否");
		jsonObject.put("intention_beian",intentionInfo.isRecorded()?"是":"否");
		jsonObject.put("intention_profession",intentionInfo.getProfession());
		jsonObject.put("intention_degree",DegreeType.getInstance(intentionInfo.getDegree()).getName());
		jsonObject.put("intention_beAcTime", TimeUtils.convertToDateString(intentionInfo.getBeAcTime()));
		String introducerIds=intentionInfo.getIntroducerIds();
		if(!StringUtils.isEmpty(introducerIds)){
			StringBuffer sb=new StringBuffer();
			String[] talkerIdArray = introducerIds.split(",");
			for(String talkerId:talkerIdArray){
				Person director = personService.getById(Integer.parseInt(talkerId));
				if(director!=null){
					sb.append(director.getName()).append(", ");
				}
			}
			String talkerName=sb.toString();
			talkerName=(talkerName.length()==0?talkerName:talkerName.substring(0,talkerName.lastIndexOf(",")));
			jsonObject.put("intention_introducer",talkerName);
		}
		//预备党员信息
		PrepareInfo prepareInfo = prepareService.getById(person.getPrepareInfoId());
		jsonObject.put("prepare_application", prepareInfo.isApplication()?"是":"否");
		jsonObject.put("prepare_meetTime", TimeUtils.convertToDateString(prepareInfo.getMeetTime()));
		jsonObject.put("prepare_meetContent", prepareInfo.getMeetContent());
		jsonObject.put("prepare_schoolApproval", prepareInfo.getSchoolApproval());
		jsonObject.put("prepare_branchApproval", prepareInfo.getBranchApproval());
		jsonObject.put("prepare_profession", prepareInfo.getProfession());
		jsonObject.put("prepare_degree", DegreeType.getInstance(prepareInfo.getDegree()).getName());
		jsonObject.put("prepare_yushen", prepareInfo.isYushen()?"是":"否");
		jsonObject.put("prepare_publiced",prepareInfo.isPubliced()?"是":"否");
		//正式党员信息
		NormalInfo normalInfo = normalService.getById(person.getNormalInfoId());
		jsonObject.put("normal_schoolApproval", normalInfo.getSchoolApproval());
		jsonObject.put("normal_branchApproval", normalInfo.getBranchApproval());
		jsonObject.put("normal_profession", normalInfo.getProfession());
		jsonObject.put("normal_degree", DegreeType.getInstance(normalInfo.getDegree()).getName());
		jsonObject.put("normal_applyTime", TimeUtils.convertToDateString(normalInfo.getApplyTime()));
		jsonObject.put("normal_meetTime", TimeUtils.convertToDateString(normalInfo.getMeetTime()));
		jsonObject.put("normal_meetContent", normalInfo.getMeetContent());

		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activitist/info.do")
	@ResponseBody
	public String activitistInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("personId");

		Person person=personService.getById(personId);
		JSONObject jsonObject=new JSONObject();
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		ActivitistInfo activitistInfo=activitistService.getById(person.getActivitistInfoId());
		if(activitistInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToDateString(activitistInfo.getCreateTime()));
		jsonObject.put("updateTime", TimeUtils.convertToDateString(activitistInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("evaTime", TimeUtils.convertToDateString(activitistInfo.getEvaluationTime()));
		jsonObject.put("evaContent", activitistInfo.getEvaluationContent());
		jsonObject.put("meetTime", TimeUtils.convertToDateString(activitistInfo.getMeetTime()));
		jsonObject.put("meetContent",activitistInfo.getMeetContent());
		jsonObject.put("degree", activitistInfo.getDegree());
		jsonObject.put("profession", activitistInfo.getProfession());
		jsonObject.put("talked", activitistInfo.isTalked()?1:0);
		jsonObject.put("beian",activitistInfo.isRecorded()?1:0);
		jsonObject.put("quntuan", activitistInfo.isQuntuan()?1:0);
		String talkerIds=activitistInfo.getDirectorIds();
		JSONArray rowsArray=new JSONArray();
		//谈话人信息
		if(!StringUtils.isEmpty(talkerIds)){
			String[] talkerIdArray = talkerIds.split(",");
			for(String talkerId:talkerIdArray){
				Person director = personService.getById(Integer.parseInt(talkerId));
				if(director!=null){
					JSONObject keyObject=new JSONObject();
					keyObject.put("id",director.getId());
					keyObject.put("title","姓名:"+director.getName()+",学工号:"+director.getNumber());
					rowsArray.add(keyObject);
				}
			}
		}
		jsonObject.put("talkers",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applier/info.do")
	@ResponseBody
	public String applierInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("applierId");

		Person person=personService.getById(personId);
		JSONObject jsonObject=new JSONObject();
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		ApplierInfo applierInfo =applierService.getById(person.getApplierInfoId());
		if(applierInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToDateString(applierInfo.getCreateTime()));
		jsonObject.put("updateTime", TimeUtils.convertToDateString(applierInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("applyTime", TimeUtils.convertToDateString(applierInfo.getApplyTime()));
		jsonObject.put("talkContent", applierInfo.getTalkContent());
		jsonObject.put("talkTime", TimeUtils.convertToDateString(applierInfo.getTalkTime()));
		jsonObject.put("degree",applierInfo.getDegree());
		jsonObject.put("profession", applierInfo.getProfession());
		String talkerIds=applierInfo.getTalkerIds();
		//谈话人信息
		JSONArray rowsArray=new JSONArray();
		if(!StringUtils.isEmpty(talkerIds)){
			String[] talkerIdArray = talkerIds.split(",");
			for(String talkerId:talkerIdArray){
				Person director = personService.getById(Integer.parseInt(talkerId));
				if(director!=null){
					JSONObject keyObject=new JSONObject();
					keyObject.put("id",director.getId());
					keyObject.put("title","姓名:"+director.getName()+",学工号:"+director.getNumber());
					rowsArray.add(keyObject);
				}
			}
		}
		jsonObject.put("talkers",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/intention/info.do")
	@ResponseBody
	public String intentionInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("intentionId");

		JSONObject jsonObject=new JSONObject();
		Person person = personService.getById(personId);
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}

		IntentionInfo intentionInfo = intentionService.getById(person.getIntentionInfoId());
		if(intentionInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}

		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToTimeString(intentionInfo.getCreateTime()));
		jsonObject.put("updateTime",TimeUtils.convertToTimeString(intentionInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("trainHour", intentionInfo.getTrainHour());
		jsonObject.put("publiced", intentionInfo.isPubliced() ? 0 : 1);
		jsonObject.put("meetTime", TimeUtils.convertToDateString(intentionInfo.getMeetTime()));
		jsonObject.put("meetContent", intentionInfo.getMeetContent());
		jsonObject.put("politicalCheck", intentionInfo.getPoliticalCheckContent());
		jsonObject.put("schoolApproval", intentionInfo.getSchoolApproval());
		jsonObject.put("yushen", intentionInfo.isYushen()?1:0);
		jsonObject.put("beian",intentionInfo.isRecorded()?1:0);
		jsonObject.put("profession",intentionInfo.getProfession());
		jsonObject.put("degree",intentionInfo.getDegree());
		jsonObject.put("beAcTime", TimeUtils.convertToDateString(intentionInfo.getBeAcTime()));
		String talkerIds=intentionInfo.getIntroducerIds();
		//谈话人信息
		JSONArray rowsArray=new JSONArray();
		if(!StringUtils.isEmpty(talkerIds)){
			String[] talkerIdArray = talkerIds.split(",");
			for(String talkerId:talkerIdArray){
				Person director = personService.getById(Integer.parseInt(talkerId));
				if(director!=null){
					JSONObject keyObject=new JSONObject();
					keyObject.put("id",director.getId());
					keyObject.put("title","姓名:"+director.getName()+",学工号:"+director.getNumber());
					rowsArray.add(keyObject);
				}
			}
		}
		jsonObject.put("talkers",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepare/info.do")
	@ResponseBody
	public String prepareInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("prepareId");

		JSONObject jsonObject=new JSONObject();
		Person person=personService.getById(personId);
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		PrepareInfo prepareInfo = prepareService.getById(person.getPrepareInfoId());
		if(prepareInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToTimeString(prepareInfo.getCreateTime()));
		jsonObject.put("updateTime", TimeUtils.convertToTimeString(prepareInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("application", prepareInfo.isApplication()?1:0);
		jsonObject.put("meetTime", TimeUtils.convertToDateString(prepareInfo.getMeetTime()));
		jsonObject.put("meetContent", prepareInfo.getMeetContent());
		jsonObject.put("schoolApproval", prepareInfo.getSchoolApproval());
		jsonObject.put("branchApproval", prepareInfo.getBranchApproval());
		jsonObject.put("profession", prepareInfo.getProfession());
		jsonObject.put("degree", prepareInfo.getDegree());
		jsonObject.put("yushen", prepareInfo.isYushen()?1:0);
		jsonObject.put("publiced",prepareInfo.isPubliced()?1:0);

		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/normal/info.do")
	@ResponseBody
	public String normalInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("normalId");

		JSONObject jsonObject=new JSONObject();
		Person person=personService.getById(personId);
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		NormalInfo normalInfo = normalService.getById(person.getNormalInfoId());
		if(normalInfo==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		jsonObject.put("success", true);
		Orgnization orgnization=orgnizationService.getOrgById(person.getOrgId());
		jsonObject.put("orgName", orgnization.getName());
		jsonObject.put("createTime", TimeUtils.convertToTimeString(normalInfo.getCreateTime()));
		jsonObject.put("updateTime", TimeUtils.convertToTimeString(normalInfo.getUpdateTime()));
		jsonObject.put("number", person.getNumber());
		jsonObject.put("name", person.getName());
		jsonObject.put("schoolApproval", normalInfo.getSchoolApproval());
		jsonObject.put("branchApproval", normalInfo.getBranchApproval());
		jsonObject.put("profession", normalInfo.getProfession());
		jsonObject.put("degree", normalInfo.getDegree());
		jsonObject.put("applyTime", TimeUtils.convertToDateString(normalInfo.getApplyTime()));
		jsonObject.put("meetTime", TimeUtils.convertToDateString(normalInfo.getMeetTime()));
		jsonObject.put("meetContent", normalInfo.getMeetContent());
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicAdd.do")
	@ResponseBody
	public String basicAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String name=request.getString("name");
		String idnumber=request.getString("idNumber");
		String number=request.getString("number");
		String desc=request.getString("desc", null);
		int type=request.getInt("type", PersonType.STUDENT.getId());
		int nation=request.getInt("nation", NationType.QITA.getId());
		int degree=request.getInt("degree", 0);
		int sex=request.getInt("sex", 0);
		String profession=request.getString("profession", null);
		Date birth=request.getDate("birth");
		String hometown=request.getString("hometown", null);

		Orgnization orgnization = orgnizationService.getOrgById(orgId);
		if(orgnization==null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","党组织不存在");
			return JsonUtils.fromObject(jsonObject);
		}

		Person person=new Person();
		person.setOrgnization(orgnization);
		person.setName(name);
		person.setIdNumber(idnumber);
		person.setNumber(number);
		person.setType(type);
		person.setHometown(hometown);
		person.setBirth(birth);
		person.setDegree(degree);
		person.setStatus(PersonStatus.NO.getId());
		person.setNation(nation);
		person.setPersonDesc(desc);
		person.setSex(sex);
		person.setProfession(profession);
		person.setSource(Person.SOURCE_DEFAULT);
		person.setPassword(StringUtils.md5(number));//默认密码就是学工号
		Person oldPerson=personService.getByNumber(number);
		if(oldPerson!=null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","该学工号已经有记录");
			return JsonUtils.fromObject(jsonObject);
		}
		try{
			personService.createPerson(person);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",true);
			return JsonUtils.fromObject(jsonObject);
		}catch (Exception e){
			LOGGER.error("add person fail,name={}",name,e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","添加失败");
			return JsonUtils.fromObject(jsonObject);
		}
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/formalAddNew.do")
	@ResponseBody
	public String formalAddNew(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String name=request.getString("name");
		String idnumber=request.getString("idNumber");
		String number=request.getString("number");
		String desc=request.getString("desc", null);
		int type=request.getInt("type", PersonType.STUDENT.getId());
		int nation=request.getInt("nation", NationType.QITA.getId());
		int degree=request.getInt("degree", 0);
		int sex=request.getInt("sex", 0);
		String profession=request.getString("profession", null);
		Date birth=request.getDate("birth");
		String hometown=request.getString("hometown", null);

		Orgnization orgnization = orgnizationService.getOrgById(orgId);
		if(orgnization==null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","党组织不存在");
			return JsonUtils.fromObject(jsonObject);
		}
		Person person=new Person();
		person.setOrgnization(orgnization);
		person.setName(name);
		person.setIdNumber(idnumber);
		person.setNumber(number);
		person.setType(type);
		person.setHometown(hometown);
		person.setBirth(birth);
		person.setDegree(degree);
		person.setStatus(PersonStatus.FORMAL.getId());
		person.setNation(nation);
		person.setPersonDesc(desc);
		person.setSex(sex);
		person.setProfession(profession);
		person.setSource(Person.SOURCE_OUT);
		person.setPassword(StringUtils.md5(number));//默认密码就是学工号
		Person oldPerson=personService.getByNumber(number);
		if(oldPerson!=null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","该学工号已经有记录");
			return JsonUtils.fromObject(jsonObject);
		}
		try{
			personService.createPerson(person);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",true);
			return JsonUtils.fromObject(jsonObject);
		}catch (Exception e){
			LOGGER.error("add person fail,name={}",name,e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","添加失败");
			return JsonUtils.fromObject(jsonObject);
		}
	}
	/**
	 * 修改基础信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicUpdate.do")
	@ResponseBody
	public String basicUpdate(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer personId = request.getInt("personId");
		String name=request.getString("name");
		String idnumber=request.getString("idNumber");
		String desc=request.getString("desc", null);
		String hometown=request.getString("hometown", null);
		int type=request.getInt("type", PersonType.STUDENT.getId());
		int nation=request.getInt("nation", NationType.QITA.getId());
		int degree=request.getInt("degree", 0);
		int sex=request.getInt("sex", 0);
		String profession=request.getString("profession", null);
		Date birth=request.getDate("birth");
		Date bePartyTime=request.getDate("bePartyTime",true);
		Date rollTime=request.getDate("rollTime",true);

		Person person=personService.getById(personId);
		if(person!=null){
			person.setProfession(profession);
			person.setDegree(degree);
			person.setNation(nation);
			person.setBirth(birth);
			person.setSex(sex);
			person.setType(type);
			person.setPersonDesc(desc);
			person.setName(name);
			person.setIdNumber(idnumber);
			person.setUpdateTime(new Date());
			person.setHometown(hometown);
			person.setBePatyDate(bePartyTime);
			person.setRollDate(rollTime);

			personService.updatePerson(person);
		}

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg","修改成功");
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 修改申请人信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applierUpdate.do")
	@ResponseBody
	public String applierUpdate(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer personId = request.getInt("personId");
		int degree=request.getInt("degree", 0);
		String profession=request.getString("profession", null);
		Date applyTime=request.getDate("applyTime");
		Date talkTime=request.getDate("talkTime");
		String talkContent=request.getString("talkContent", null);
		String talkerIds=request.getString("talkerIds", null);

		Person person=personService.getById(personId);
		ApplierInfo applierInfo = applierService.getById(person.getApplierInfoId());
		if(applierInfo!=null){
			applierInfo.setApplyTime(applyTime);
			applierInfo.setUpdateTime(new Date());
			applierInfo.setTalkContent(talkContent);
			applierInfo.setTalkTime(talkTime);
			applierInfo.setDegree(degree);
			applierInfo.setProfession(profession);
			if(!StringUtils.isEmpty(talkerIds)){
				if(talkerIds.startsWith(",")){
					talkerIds=talkerIds.substring(1);
				}
				applierInfo.setTalkerIds(talkerIds);
			}else{
				applierInfo.setTalkerIds(null);
			}
			applierService.updateApplier(applierInfo);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg","修改成功");
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applierAdd.do")
	@ResponseBody
	public String applierAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		String talkContent=request.getString("talkContent", null);
		String talkerIds=request.getString("talkerIds",null);
		String profession=request.getString("profession",null);
		int degree=request.getInt("degree", 0);
		Date talkTime=request.getDate("talkTime", null);
		Date applyTime=request.getDate("applyTime", null);
		//检查是否学号正确，状态+部门
		//检查谈话人是否是本部门
		//添加申请人信息，修改基础人员状态
		ApplierInfo applierInfo=new ApplierInfo();
		applierInfo.setApplyTime(applyTime);
		applierInfo.setTalkContent(talkContent);
		applierInfo.setDegree(degree);
		applierInfo.setProfession(profession);
		if(!StringUtils.isEmpty(talkerIds)){
			if(talkerIds.startsWith(",")){
				talkerIds=talkerIds.substring(1);
			}
			applierInfo.setTalkerIds(talkerIds);
		}
		applierInfo.setTalkTime(talkTime);

		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.NO.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为申请人"+number);
			return JsonUtils.fromObject(jsonObject);
		}

		int age=StringUtils.convertBirthdayToAge(person.getBirth());
		if(age<18){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","年龄必须大于18岁");
			return JsonUtils.fromObject(jsonObject);
		}
		applierService.createApplier(applierInfo);
		//修改基础人员状态
		person.setStatus(PersonStatus.APPLIER.getId());
		person.setApplierInfoId(applierInfo.getId());
		personService.updatePerson(person);

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
	@RequestMapping("/activitistAdd.do")
	@ResponseBody
	public String activitistAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		String meetContent=request.getString("meetContent", null);
		String evaContent=request.getString("evaContent", null);
		String directorIds=request.getString("directorIds", null);
		Date meetTime=request.getDate("meetTime", null);
		Date evaTime=request.getDate("evaTime", null);
		String profession=request.getString("profession", null);
		int degree=request.getInt("degree", 0);
		int beian=request.getInt("beian", 0);
		int talked=request.getInt("talked", 0);
		int quntuan=request.getInt("quntuan", 0);

		ActivitistInfo activitistInfo=new ActivitistInfo();
		if(!StringUtils.isEmpty(directorIds)){
			if(directorIds.startsWith(",")){
				directorIds=directorIds.substring(1);
			}
			activitistInfo.setDirectorIds(directorIds);
		}

		activitistInfo.setRecorded(beian==1);
		activitistInfo.setTalked(talked==1);
		activitistInfo.setQuntuan(quntuan==1);
		activitistInfo.setEvaluationContent(evaContent);
		activitistInfo.setMeetContent(meetContent);
		activitistInfo.setMeetTime(meetTime);
		activitistInfo.setEvaluationTime(evaTime);
		activitistInfo.setRecorded(true);
		activitistInfo.setProfession(profession);
		activitistInfo.setDegree(degree);
		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.APPLIER.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为积极分子"+number);
			return JsonUtils.fromObject(jsonObject);
		}
		if(person.getOrgId()!=orgId){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为积极分子"+number);
			return JsonUtils.fromObject(jsonObject);
		}

		activitistService.createActivitist(activitistInfo);

		person.setStatus(PersonStatus.ACTIVISTS.getId());
		person.setActivitistInfoId(activitistInfo.getId());
		personService.updatePerson(person);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activitistUpdate.do")
	@ResponseBody
	public String activitistUpdate(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer personId = request.getInt("personId");
		String meetContent=request.getString("meetContent", null);
		String evaContent=request.getString("evaContent", null);
		Date meetTime=request.getDate("meetTime", null);
		Date evaTime=request.getDate("evaTime", null);
		String profession=request.getString("profession", null);
		int degree=request.getInt("degree", 0);
		int beian=request.getInt("beian", 0);
		int talked=request.getInt("talked", 0);
		int quntuan=request.getInt("quntuan", 0);
		String directorIds=request.getString("directorIds", null);

		Person person=personService.getById(personId);
		if(person!=null){
			ActivitistInfo activitistInfo =activitistService.getById(person.getActivitistInfoId());
			activitistInfo.setRecorded(beian==1);
			activitistInfo.setTalked(talked==1);
			activitistInfo.setQuntuan(quntuan==1);
			activitistInfo.setEvaluationContent(evaContent);
			activitistInfo.setMeetContent(meetContent);
			activitistInfo.setMeetTime(meetTime);
			activitistInfo.setEvaluationTime(evaTime);
			activitistInfo.setRecorded(true);
			activitistInfo.setProfession(profession);
			activitistInfo.setDegree(degree);
			if(!StringUtils.isEmpty(directorIds)){
				if(directorIds.startsWith(",")){
					directorIds=directorIds.substring(1);
				}
				activitistInfo.setDirectorIds(directorIds);
			}else{
				activitistInfo.setDirectorIds(null);
			}
			activitistService.updateActivitist(activitistInfo);
		}

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/intentionAdd.do")
	@ResponseBody
	public String intentionAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		int classHour=request.getInt("trainHour", 0);
		int publiced=request.getInt("publiced", 0);
		String directorIds=request.getString("directorIds", null);
		Date meetTime=request.getDate("meetTime", null);
		String meetContent=request.getString("meetContent", null);
		String politicalCheck=request.getString("politicalCheck", null);
		String profession=request.getString("profession", null);
		int degree=request.getInt("degree", 0);
		int beian=request.getInt("beian", 0);
		int yushen=request.getInt("yushen", 0);
		Date beActiveTime=request.getDate("beAcTime", null);

		IntentionInfo intentionInfo=new IntentionInfo();
		if(!StringUtils.isEmpty(directorIds)){
			if(directorIds.startsWith(",")){
				directorIds=directorIds.substring(1);
			}
			intentionInfo.setIntroducerIds(directorIds);
		}

		intentionInfo.setYushen(yushen==1);
		intentionInfo.setBeAcTime(beActiveTime);
		intentionInfo.setRecorded(beian == 1);
		intentionInfo.setMeetContent(meetContent);
		intentionInfo.setMeetTime(meetTime);
		intentionInfo.setPoliticalCheckContent(politicalCheck);
		intentionInfo.setSchoolApproval(null);
		intentionInfo.setPubliced(publiced == 0);
		intentionInfo.setTrainHour(classHour);
		intentionInfo.setProfession(profession);
		intentionInfo.setDegree(degree);

		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.ACTIVISTS.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为发展对象"+number);
			return JsonUtils.fromObject(jsonObject);
		}
		//检查
		ActivitistInfo activitistInfo = activitistService.getById(person.getActivitistInfoId());
		if(meetTime.after(new Date())){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","召开支部大会时间不能超过当前时间");
			return JsonUtils.fromObject(jsonObject);
		}
		Date activitistMeetTime = activitistInfo.getMeetTime();
		if(activitistMeetTime.after(meetTime)){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","召开支部大会时间不能超过上一阶段党支部大会时间");
			return JsonUtils.fromObject(jsonObject);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(meetTime);
		calendar.set(Calendar.YEAR, -1);
		Date targetTime = calendar.getTime();
		if(person==null||targetTime.after(activitistMeetTime)){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","确定积极分子时间不能短于1年");
			return JsonUtils.fromObject(jsonObject);
		}

		intentionService.createIntention(intentionInfo);

		person.setStatus(PersonStatus.INTENTION.getId());
		person.setIntentionInfoId(intentionInfo.getId());
		personService.updatePerson(person);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 发展对象信息修改
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/intentionUpdate.do")
	@ResponseBody
	public String intentionUpdate(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer personId = request.getInt("personId");
		int classHour=request.getInt("trainHour", 0);
		int publiced=request.getInt("publiced", 0);
		Date meetTime=request.getDate("meetTime", null);
		String meetContent=request.getString("meetContent", null);
		String politicalCheck=request.getString("politicalCheck", null);
		String profession=request.getString("profession", null);
		int degree=request.getInt("degree", 0);
		int beian=request.getInt("beian", 0);
		int yushen=request.getInt("yushen", 0);
		Date beActiveTime=request.getDate("beAcTime", null);
		String directorIds=request.getString("directorIds", null);

		Person person=personService.getById(personId);
		if(person!=null){
			IntentionInfo intentionInfo=intentionService.getById(person.getIntentionInfoId());
			intentionInfo.setYushen(yushen==1);
			intentionInfo.setBeAcTime(beActiveTime);
			intentionInfo.setRecorded(beian == 1);
			intentionInfo.setMeetContent(meetContent);
			intentionInfo.setMeetTime(meetTime);
			intentionInfo.setPoliticalCheckContent(politicalCheck);
			intentionInfo.setSchoolApproval(null);
			intentionInfo.setPubliced(publiced == 0);
			intentionInfo.setTrainHour(classHour);
			intentionInfo.setProfession(profession);
			intentionInfo.setDegree(degree);

			if(!StringUtils.isEmpty(directorIds)){
				if(directorIds.startsWith(",")){
					directorIds=directorIds.substring(1);
				}
				intentionInfo.setIntroducerIds(directorIds);
			}else{
				intentionInfo.setIntroducerIds(null);
			}
			intentionService.updateIntention(intentionInfo);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepareAdd.do")
	@ResponseBody
	public String prepareAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		Date meetTime=request.getDate("meetTime", null);
		String branchApproval=request.getString("branchApproval", null);
		String schoolApproval=request.getString("schoolApproval", null);
		String meetContent=request.getString("meetContent", null);
		String profession=request.getString("profession", null);
		int degree=request.getInt("degree", 0);
		int yushen=request.getInt("yushen", 0);
		int publiced=request.getInt("public", 0);
		int shenqing=request.getInt("shenqing", 0);

		PrepareInfo prepareInfo=new PrepareInfo();
		prepareInfo.setSchoolApproval(schoolApproval);
		prepareInfo.setApplication(true);
		prepareInfo.setBranchApproval(branchApproval);
		prepareInfo.setMeetContent(meetContent);
		prepareInfo.setMeetTime(meetTime);
		prepareInfo.setSchoolApproval(schoolApproval);
		prepareInfo.setProfession(profession);
		prepareInfo.setDegree(degree);
		prepareInfo.setYushen(yushen == 1);
		prepareInfo.setPubliced(publiced == 1);
		prepareInfo.setApplication(shenqing == 1);

		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.INTENTION.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为预备党员"+number);
			return JsonUtils.fromObject(jsonObject);
		}
		//检查
		IntentionInfo intentionInfo = intentionService.getById(person.getIntentionInfoId());
		if(meetTime.after(new Date())){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","召开支部大会时间不能超过当前时间");
			return JsonUtils.fromObject(jsonObject);
		}
		Date intentionMeettime = intentionInfo.getMeetTime();
		if(intentionMeettime.after(meetTime)){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","召开支部大会时间不能超过上一阶段党支部大会时间");
			return JsonUtils.fromObject(jsonObject);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(meetTime);
		calendar.set(Calendar.YEAR, -1);
		Date targetTime = calendar.getTime();
		if(person==null||targetTime.after(intentionMeettime)){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","召开支部大会时间不能短于1年");
			return JsonUtils.fromObject(jsonObject);
		}


		prepareService.createPrepare(prepareInfo);

		person.setStatus(PersonStatus.PERPARE.getId());
		person.setPrepareInfoId(prepareInfo.getId());
		personService.updatePerson(person);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepareUpdate.do")
	@ResponseBody
	public String prepareUpdate(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer personId = request.getInt("personId");
		Date meetTime=request.getDate("meetTime", null);
		String branchApproval=request.getString("branchApproval", null);
		String schoolApproval=request.getString("schoolApproval", null);
		String meetContent=request.getString("meetContent", null);
		String profession=request.getString("profession", null);
		int degree=request.getInt("degree", 0);
		int yushen=request.getInt("yushen", 0);
		int publiced=request.getInt("public", 0);
		int shenqing=request.getInt("shenqing", 0);


		if(meetTime.after(new Date())){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","召开支部大会时间不能超过当前时间");
			return JsonUtils.fromObject(jsonObject);
		}

		Person person = personService.getById(personId);
		if(person!=null){
			PrepareInfo prepareInfo = prepareService.getById(person.getPrepareInfoId());
			prepareInfo.setSchoolApproval(schoolApproval);
			prepareInfo.setApplication(true);
			prepareInfo.setBranchApproval(branchApproval);
			prepareInfo.setMeetContent(meetContent);
			prepareInfo.setMeetTime(meetTime);
			prepareInfo.setSchoolApproval(schoolApproval);
			prepareInfo.setProfession(profession);
			prepareInfo.setDegree(degree);
			prepareInfo.setYushen(yushen == 1);
			prepareInfo.setPubliced(publiced == 1);
			prepareInfo.setApplication(shenqing == 1);

			prepareService.updatePrepare(prepareInfo);
		}

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/normalAdd.do")
	@ResponseBody
	public String normalAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		String branchApproval=request.getString("branchApproval", null);
		String schoolApproval=request.getString("schoolApproval", null);
		String profession=request.getString("profession", null);
		int degree=request.getInt("degree", 0);
		Date meetTime=request.getDate("meetTime", null);
		String meetContent=request.getString("meetContent", null);
		Date applyTime=request.getDate("applyTime", null);

		NormalInfo normalInfo=new NormalInfo();
		normalInfo.setSchoolApproval(schoolApproval);
		normalInfo.setBranchApproval(branchApproval);
		normalInfo.setApplyTime(applyTime);
		normalInfo.setApproval(null);
		normalInfo.setDegree(degree);
		normalInfo.setProfession(profession);
		normalInfo.setMeetTime(meetTime);
		normalInfo.setMeetContent(meetContent);

		Person person = personService.getByNumber(number);
		if(person==null||person.getStatus()!=PersonStatus.PERPARE.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为正式党员"+number);
			return JsonUtils.fromObject(jsonObject);
		}

		normalService.createNormal(normalInfo);

		person.setStatus(PersonStatus.NORMAL.getId());
		person.setNormalInfoId(normalInfo.getId());
		personService.updatePerson(person);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 *
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/normalUpdate.do")
	@ResponseBody
	public String normalUpdate(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer personId = request.getInt("personId");
		String branchApproval=request.getString("branchApproval", null);
		String schoolApproval=request.getString("schoolApproval", null);
		String profession=request.getString("profession", null);
		int degree=request.getInt("degree", 0);
		Date meetTime=request.getDate("meetTime", null);
		String meetContent=request.getString("meetContent", null);
		Date applyTime=request.getDate("applyTime", null);

		Person person = personService.getById(personId);
		if(person!=null){
			NormalInfo normalInfo=normalService.getById(person.getNormalInfoId());
			normalInfo.setSchoolApproval(schoolApproval);
			normalInfo.setBranchApproval(branchApproval);
			normalInfo.setApplyTime(applyTime);
			normalInfo.setApproval(null);
			normalInfo.setDegree(degree);
			normalInfo.setProfession(profession);
			normalInfo.setMeetTime(meetTime);
			normalInfo.setMeetContent(meetContent);
			normalService.updateNormal(normalInfo);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 在编党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/formalAdd.do")
	@ResponseBody
	public String formalAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer personId = request.getInt("personId");

		Person person = personService.getById(personId);
		if(person==null||person.getStatus()!=PersonStatus.NORMAL.getId()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success",false);
			jsonObject.put("msg","该学号不能转为在编党员");
			return JsonUtils.fromObject(jsonObject);
		}
		person.setStatus(PersonStatus.FORMAL.getId());
		person.setSource(Person.SOURCE_DEFAULT);
		personService.updatePerson(person);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 查询接口
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/withType.do")
	@ResponseBody
	public String listQueryWithType(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId=request.getInt("orgId", 0);
		String statusParam=request.getString("status", null);
		String[] statusArray=null;
		if(!StringUtils.isEmpty(statusParam)){
			statusArray=statusParam.split(",");
		}
		boolean withSubOrg=request.getBoolean("withAllSubOrg",true);
		boolean withAllPersonByType=request.getBoolean("withAllPersonByType",true);

		String number=request.getString("number", null);
		Orgnization orgnization = orgnizationService.getOrgById(orgId);

		PersonCondition condition=new PersonCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);
		if(withSubOrg){
			List<Orgnization> subOrgs=orgnizationService.getOrgByParent(orgnization,true);
			if(subOrgs!=null){
				for(Orgnization subOrg:subOrgs){
					orgIdList.add(subOrg.getId());
				}
			}
		}
		condition.setOrgList(orgIdList);

		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		if(statusArray!=null){
			List<Integer> statuslist=new ArrayList<Integer>();
			for(String status:statusArray){
				statuslist.add(Integer.parseInt(status));
			}
			condition.setStatus(statuslist);
		}

		Page pageInfo=new Page();
		pageInfo.setCurrent(0);
		pageInfo.setSize(10);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		if(personList==null||personList.size()==0){
			condition.setName(number);
			condition.setNumber(null);
			personList=personService.getByCondition(condition,pageInfo,result);
		}

		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			if(statusArray!=null&&StringUtils.isEmpty(number)&&withAllPersonByType){
				for(String status:statusArray){
					JSONObject keyObject=new JSONObject();
					if(String.valueOf(PersonStatus.NO.getId()).equals(status)){
						keyObject.put("id","-1");
						keyObject.put("title", "全部基础人员(含下属组织)");
						rowsArray.add(keyObject);
						continue;
					}
					if(String.valueOf(PersonStatus.PERPARE.getId()).equals(status)){
						keyObject.put("id","-2");
						keyObject.put("title","全部预备党员(含下属组织)");
						rowsArray.add(keyObject);
						continue;
					}
					if(String.valueOf(PersonStatus.NORMAL.getId()).equals(status)){
						keyObject.put("id","-3");
						keyObject.put("title","全部正式党员(含下属组织)");
						rowsArray.add(keyObject);
						continue;
					}
					if(String.valueOf(PersonStatus.ACTIVISTS.getId()).equals(status)){
						keyObject.put("id","-4");
						keyObject.put("title","全部积极分子(含下属组织)");
						rowsArray.add(keyObject);
						continue;
					}
					if(String.valueOf(PersonStatus.APPLIER.getId()).equals(status)){
						keyObject.put("id","-5");
						keyObject.put("title","全部入党申请人(含下属组织)");
						rowsArray.add(keyObject);
						continue;
					}
					if(String.valueOf(PersonStatus.INTENTION.getId()).equals(status)){
						keyObject.put("id","-6");
						keyObject.put("title","全部发展对象(含下属组织)");
						rowsArray.add(keyObject);
						continue;
					}
					if(String.valueOf(PersonStatus.FORMAL.getId()).equals(status)){
						keyObject.put("id","-7");
						keyObject.put("title","全部在编对象(含下属组织)");
						rowsArray.add(keyObject);
						continue;
					}
				}
			}
			for(Person person:personList){
				JSONObject keyObject=new JSONObject();
				keyObject.put("id",person.getId());
				keyObject.put("title",PersonStatus.getInstance(person.getStatus()).getName()+",姓名:"+person.getName()+",学工号:"+person.getNumber());
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
	 * 查询接口
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listQuery.do")
	@ResponseBody
	public String listQuery(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId=request.getInt("orgId", 0);
		String statusParam=request.getString("status", null);
		String[] statusArray=null;
		if(!StringUtils.isEmpty(statusParam)){
			statusArray=statusParam.split(",");
		}
		boolean withSubOrg=request.getBoolean("withAllSubOrg", false);

		String number=request.getString("number", null);
		PersonCondition condition=new PersonCondition();
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);
		if(withSubOrg){
			Orgnization orgnization=new Orgnization();
			orgnization.setId(orgId);
			List<Orgnization> subOrgs=orgnizationService.getOrgByParent(orgnization,true);
			if(subOrgs!=null){
				for(Orgnization subOrg:subOrgs){
					orgIdList.add(subOrg.getId());
				}
			}
		}
		condition.setOrgList(orgIdList);

		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		if(statusArray!=null){
			List<Integer> statuslist=new ArrayList<Integer>();
			for(String status:statusArray){
				statuslist.add(Integer.parseInt(status));
			}
			condition.setStatus(statuslist);
		}

		Page pageInfo=new Page();
		pageInfo.setCurrent(0);
		pageInfo.setSize(10);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);
		if(personList==null||personList.size()==0){
			condition.setName(number);
			condition.setNumber(null);
			personList=personService.getByCondition(condition,pageInfo,result);
		}
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject keyObject=new JSONObject();
				keyObject.put("personId",person.getId());
				keyObject.put("personName",person.getName());
				keyObject.put("personNumber", person.getNumber());
				keyObject.put("personStatus",PersonStatus.getInstance(person.getStatus()).getName());
				keyObject.put("orgName",person.getOrgnization().getName());
				keyObject.put("personType",PersonType.getInstance(person.getType()).getName());
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
	 * 获取基本信息列表
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicList.do")
	@ResponseBody
	public String basicList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String name=request.getString("name", null);
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
		PersonCondition condition=new PersonCondition();
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
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);

		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		condition.setStatus(Arrays.asList(PersonStatus.NO.getId()));
		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getIdNumber());
				cellList.add(person.getSex()==0?"男":"女");
				cellList.add(NationType.getInstance(person.getNation()).getName());
				cellList.add(DegreeType.getInstance(person.getDegree()).getName());
				cellList.add(person.getHometown());
				cellList.add(TimeUtils.convertToDateString(person.getBirth()));
				cellList.add(person.getProfession());
				cellList.add(TimeUtils.convertToDateString(person.getCreateTime()));
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 获取申请人信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applierList.do")
	@ResponseBody
	public String applierList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
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
		PersonCondition condition=new PersonCondition();
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
		condition.setStatus(Arrays.asList(PersonStatus.APPLIER.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeApplierInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(String.valueOf(orgInfo.getId()));
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getIdNumber());
				cellList.add(person.getSex()==0?"男":"女");
				cellList.add(NationType.getInstance(person.getNation()).getName());
				cellList.add(DegreeType.getInstance(person.getApplierInfo().getDegree()).getName());
				cellList.add(person.getHometown());
				cellList.add(TimeUtils.convertToDateString(person.getBirth()));
				cellList.add(person.getApplierInfo().getProfession());
				String applyTime=TimeUtils.convertToDateString(person.getApplierInfo().getApplyTime());
				cellList.add(StringUtils.isEmpty(applyTime) ? "未提交申请书" : applyTime);
				String talkTime=TimeUtils.convertToDateString(person.getApplierInfo().getTalkTime());
				cellList.add(StringUtils.isEmpty(talkTime)?"未谈话":talkTime);
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 历史党员信息列表
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/historyList.do")
	@ResponseBody
	public String historyList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String number=request.getString("number", null);
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
		PersonCondition condition=new PersonCondition();
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
		condition.setStatus(Arrays.asList(PersonStatus.HISTORY_OUT.getId(), PersonStatus.HISTORY_PUNISH.getId(), PersonStatus.HISTORY_DEATH.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setNumber(name);
		}
		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeApplierInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonStatus.getInstance(person.getStatus()).getName());
				String updateTime=TimeUtils.convertToTimeString(person.getUpdateTime());
				cellList.add(updateTime);
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 获取可登陆系统分子信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/canLoginUserList.do")
	@ResponseBody
	public String canLoginUserList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
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
		PersonCondition condition=new PersonCondition();
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
		condition.setStatus(Arrays.asList(
				PersonStatus.PERPARE.getId(),
				PersonStatus.NO.getId(),
				PersonStatus.APPLIER.getId(),
				PersonStatus.ACTIVISTS.getId(),
				PersonStatus.INTENTION.getId(),
				PersonStatus.NORMAL.getId(),
				PersonStatus.FORMAL.getId()));

		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeActivitistInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(String.valueOf(orgInfo.getId()));
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getSex()==0?"男":"女");
				cellList.add(NationType.getInstance(person.getNation()).getName());
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 获取积极分子信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activitistList.do")
	@ResponseBody
	public String activitistList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
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
		PersonCondition condition=new PersonCondition();
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
		condition.setStatus(Arrays.asList(PersonStatus.ACTIVISTS.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeActivitistInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(String.valueOf(orgInfo.getId()));
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getIdNumber());
				cellList.add(person.getSex()==0?"男":"女");
				cellList.add(NationType.getInstance(person.getNation()).getName());
				cellList.add(DegreeType.getInstance(person.getActivitistInfo().getDegree()).getName());
				cellList.add(person.getHometown());
				cellList.add(TimeUtils.convertToDateString(person.getBirth()));
				cellList.add(person.getActivitistInfo().getProfession());
				String evaluaTime=TimeUtils.convertToDateString(person.getActivitistInfo().getEvaluationTime());
				cellList.add(StringUtils.isEmpty(evaluaTime)?"未测评":evaluaTime);
				String meetTime=TimeUtils.convertToDateString(person.getActivitistInfo().getMeetTime());
				cellList.add(StringUtils.isEmpty(meetTime) ? "未召开" : meetTime);
				cellList.add(StringUtils.isEmpty(person.getActivitistInfo().getDirectorIds())?"未确定":"已确定");
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 获取发展对象信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/intentionList.do")
	@ResponseBody
	public String intentionList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String number=request.getString("number", null);
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
		PersonCondition condition=new PersonCondition();
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
		condition.setStatus(Arrays.asList(PersonStatus.INTENTION.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setNumber(name);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeIntentionInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(String.valueOf(orgInfo.getId()));
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getIdNumber());
				cellList.add(person.getSex() == 0 ? "男" : "女");
				cellList.add(NationType.getInstance(person.getNation()).getName());
				cellList.add(DegreeType.getInstance(person.getIntentionInfo().getDegree()).getName());
				cellList.add(person.getHometown());
				cellList.add(TimeUtils.convertToDateString(person.getBirth()));
				cellList.add(person.getIntentionInfo().getProfession());
				cellList.add(person.getIntentionInfo().isPubliced()?"已公示":"已公示");
				cellList.add(person.getIntentionInfo().getTrainHour()==0?"未设置":person.getIntentionInfo().getTrainHour() +"课时");
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 获取预备党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepareList.do")
	@ResponseBody
	public String prepareList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String number=request.getString("number", null);
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
		PersonCondition condition=new PersonCondition();
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
		condition.setStatus(Arrays.asList(PersonStatus.PERPARE.getId()));

		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}
		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludePrepareInfo(true);
		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getIdNumber());
				cellList.add(person.getSex()==0?"男":"女");
				cellList.add(NationType.getInstance(person.getNation()).getName());
				cellList.add(DegreeType.getInstance(person.getPrepareInfo().getDegree()).getName());
				cellList.add(person.getHometown());
				cellList.add(TimeUtils.convertToDateString(person.getBirth()));
				cellList.add(person.getPrepareInfo().getProfession());
				cellList.add(person.getPrepareInfo().isApplication() ? "已提交":"未提交");
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 获取正式党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/normalList.do")
	@ResponseBody
	public String normalList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String name=request.getString("name", null);
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
		PersonCondition condition=new PersonCondition();
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
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);
		condition.setStatus(Arrays.asList(PersonStatus.NORMAL.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}

		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);
		result.setIncludeNormalInfo(true);

		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getIdNumber());
				cellList.add(person.getSex()==0?"男":"女");
				cellList.add(NationType.getInstance(person.getNation()).getName());
				cellList.add(DegreeType.getInstance(person.getNormalInfo().getDegree()).getName());
				cellList.add(person.getHometown());
				cellList.add(TimeUtils.convertToDateString(person.getBirth()));
				cellList.add(person.getNormalInfo().getProfession());
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 在编党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/formalList.do")
	@ResponseBody
	public String formalList(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		String name=request.getString("name", null);
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
		PersonCondition condition=new PersonCondition();
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
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);
		condition.setStatus(Arrays.asList(PersonStatus.FORMAL.getId()));
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		if(!StringUtils.isEmpty(name)){
			condition.setName(name);
		}
		int subCount = personService.countByCondition(condition);

		PersonResult result=new PersonResult();
		result.setIncludeOrgnization(true);

		List<Person> personList=personService.getByCondition(condition,pageInfo,result);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(personList!=null){
			for(Person person:personList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", person.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(person.getId()));
				Orgnization orgInfo = person.getOrgnization();
				cellList.add(orgInfo.getName());
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonType.getInstance(person.getType()).getName());
				cellList.add(person.getIdNumber());
				cellList.add(person.getSex()==0?"男":"女");
				cellList.add(NationType.getInstance(person.getNation()).getName());
				cellList.add(person.getHometown());
				cellList.add(TimeUtils.convertToDateString(person.getBirth()));
				cellList.add(person.getSource()==Person.SOURCE_OUT?"是":"否");
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 删除申请人信息，删除申请相关资料+变为基础人员
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applier/delete.do")
	@ResponseBody
	public String applierDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				ApplierInfo applierInfo=applierService.getById(person.getApplierInfoId());
				if(applierInfo!=null){
					applierService.deleteApplier(applierInfo);
				}
				person.setStatus(PersonStatus.NO.getId());
				person.setApplierInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 删除积极分子信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activitist/delete.do")
	@ResponseBody
	public String activitistDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				ActivitistInfo activitistInfo=activitistService.getById(person.getApplierInfoId());
				if(activitistInfo!=null){
					activitistService.deleteActivitist(activitistInfo);
				}
				person.setStatus(PersonStatus.APPLIER.getId());
				person.setActivitistInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除发展对象信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/intention/delete.do")
	@ResponseBody
	public String intentionDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				IntentionInfo intentionInfo=intentionService.getById(person.getApplierInfoId());
				if(intentionInfo!=null){
					intentionService.deleteIntention(intentionInfo);
				}
				person.setStatus(PersonStatus.ACTIVISTS.getId());
				person.setIntentionInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除预备党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepare/delete.do")
	@ResponseBody
	public String prepareDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				PrepareInfo prepareInfo=prepareService.getById(person.getApplierInfoId());
				if(prepareInfo!=null){
					prepareService.deletePrepare(prepareInfo);
				}
				person.setStatus(PersonStatus.INTENTION.getId());
				person.setPrepareInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basic/delete.do")
	@ResponseBody
	public String basicDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				personService.deletePerson(person);
				Admin admininfo = adminService.getBySchoolNumber(person.getNumber());
				if(admininfo!=null){
					adminService.deleteAdmin(admininfo);
				}
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/formal/delete.do")
	@ResponseBody
	public String formalDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				if(person.getSource()==Person.SOURCE_OUT){
					//设置状态
					personService.deletePerson(person);
					Admin adminInfo = adminService.getBySchoolNumber(person.getNumber());
					if(adminInfo!=null){
						adminService.deleteAdmin(adminInfo);
					}
				}else{
					person.setStatus(PersonStatus.NORMAL.getId());
					person.setUpdateTime(new Date());
					personService.updatePerson(person);
				}
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}
	/**
	 * 删除党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/normal/delete.do")
	@ResponseBody
	public String normalDelete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String[] ids=request.getStringArray("id", ",");
		for(String id:ids){
			Person person=personService.getById(Integer.parseInt(id));
			if(person!=null){
				NormalInfo normalInfo=normalService.getById(person.getApplierInfoId());
				if(normalInfo!=null){
					normalService.deleteNormal(normalInfo);
				}
				person.setStatus(PersonStatus.PERPARE.getId());
				person.setNormalInfoId(0);
				personService.updatePerson(person);
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("status",true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 党员统计图表信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/statis.do")
	@ResponseBody
	public String statisPersonInfo(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId=request.getInt("orgId", 0);
		String type=request.getString("type","byorg");
		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}else if(adminInfo.getType()==Admin.SYSTEM_ADMIN){
				orgId=1;//默认的设置为校党委
			}
		}
		if(type.equals("byorg")){
			return statisByOrg(orgId);
		}else if(type.equals("bysex")){
			return statisBySex(orgId);
		}else if(type.equals("bynation")){
			return statisByNation(orgId);
		}else if(type.equals("bydegree")){
			return statisByDegree(orgId);
		} else if(type.equals("byage")){
			return statisByAge(orgId);
		}else{
			return  statisByTime(orgId);
		}
	}

	private List<Integer> statisPersonStatus(){
		return Arrays.asList(
				PersonStatus.NO.getId(),
				PersonStatus.APPLIER.getId(),
				PersonStatus.ACTIVISTS.getId(),
				PersonStatus.INTENTION.getId(),
				PersonStatus.PERPARE.getId(),
				PersonStatus.NORMAL.getId(),
				PersonStatus.FORMAL.getId());
	}

	private String statisByAge(Integer orgId) throws DMException {
		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		//先统计本部门的人数
		//30以下、31-35、36-40、41-45、46-50、51-55、56-60、61-65、66-70、71以上）
		Calendar nowCalender=Calendar.getInstance();
		nowCalender.setTime(new Date());

		Calendar sanshiStartCalander=Calendar.getInstance();
		sanshiStartCalander.setTime(new Date());
		sanshiStartCalander.roll(Calendar.YEAR, -30);

		Calendar sanshiwuStartCalander=Calendar.getInstance();
		sanshiwuStartCalander.setTime(new Date());
		sanshiwuStartCalander.roll(Calendar.YEAR,-35);

		Calendar sishiStartCalander=Calendar.getInstance();
		sishiStartCalander.setTime(new Date());
		sishiStartCalander.roll(Calendar.YEAR,-40);

		Calendar sishiwuStartCalander=Calendar.getInstance();
		sishiwuStartCalander.setTime(new Date());
		sishiwuStartCalander.roll(Calendar.YEAR,-45);

		Calendar wushiStartCalander=Calendar.getInstance();
		wushiStartCalander.setTime(new Date());
		wushiStartCalander.roll(Calendar.YEAR,-50);

		Calendar wushiwuStartCalander=Calendar.getInstance();
		wushiwuStartCalander.setTime(new Date());
		wushiwuStartCalander.roll(Calendar.YEAR,-55);

		Calendar liushiStartCalander=Calendar.getInstance();
		liushiStartCalander.setTime(new Date());
		liushiStartCalander.roll(Calendar.YEAR,-60);

		Calendar liushiwuStartCalander=Calendar.getInstance();
		liushiwuStartCalander.setTime(new Date());
		liushiwuStartCalander.roll(Calendar.YEAR,-65);

		Calendar qishiStartCalander=Calendar.getInstance();
		qishiStartCalander.setTime(new Date());
		qishiStartCalander.roll(Calendar.YEAR,-70);

		int directSanshiCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				null,
				sanshiStartCalander.getTime(),nowCalender.getTime(),
				false);

		int directSanshiwuCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				null,
				sanshiwuStartCalander.getTime(),sanshiStartCalander.getTime(),
				false);

		int directSishiCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				null,
				sishiStartCalander.getTime(),sanshiwuStartCalander.getTime(),
				false);

		int directSishiwuCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				null,
				sishiwuStartCalander.getTime(),sishiStartCalander.getTime(),
				false);

		int directWushiCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				null,
				wushiStartCalander.getTime(),sishiwuStartCalander.getTime(),
				false);

		int directWushiwuCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				null,
				wushiwuStartCalander.getTime(),wushiStartCalander.getTime(),
				false);

		int directLiushiCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				null,
				liushiStartCalander.getTime(),wushiwuStartCalander.getTime(),
				false);

		int directLiushiwuCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0, 1),
				NationType.allType(),
				null,
				liushiwuStartCalander.getTime(), liushiStartCalander.getTime(),
				false);

		int directQishiCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				null,
				qishiStartCalander.getTime(),liushiwuStartCalander.getTime(),
				false);

		int directQishiMoreCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				null,
				null,qishiStartCalander.getTime(),
				false);

		int totalSanshi=directSanshiCount;
		int totalSanshiwu=directSanshiwuCount;
		int totalSishi=directSishiCount;
		int totalSishiwu=directSishiwuCount;
		int totalWushi=directWushiCount;
		int totalWushiwu=directWushiwuCount;
		int totalLiushi=directLiushiCount;
		int totalLiushiwu=directLiushiwuCount;
		int totalQishi=directQishiCount;
		int totalQishimore=directQishiMoreCount;

		//获得直属的子组织
		List<Orgnization> subOrgList=orgnizationService.getOrgByParent(orgnization, false);
		if(subOrgList!=null){
			for(Orgnization subOrg:subOrgList){
				int subSanshiCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						null,
						sanshiStartCalander.getTime(),nowCalender.getTime(),
						true);
				totalSanshi=totalSanshi+subSanshiCount;

				int subSanshiwuCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						null,
						sanshiwuStartCalander.getTime(),sanshiStartCalander.getTime(),
						true);
				totalSanshiwu=totalSanshiwu+subSanshiwuCount;

				int subSisiCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						null,
						sishiStartCalander.getTime(),sanshiwuStartCalander.getTime(),
						true);
				totalSishi=totalSishi+subSisiCount;

				int subSishiwuCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						null,
						sishiwuStartCalander.getTime(),sishiStartCalander.getTime(),
						true);
				totalSishiwu=totalSishiwu+subSishiwuCount;

				int subWushiCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						null,
						wushiStartCalander.getTime(),sishiwuStartCalander.getTime(),
						true);
				totalWushi=totalWushi+subWushiCount;

				int subWushiwuCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						null,
						wushiwuStartCalander.getTime(),wushiStartCalander.getTime(),
						true);
				totalWushiwu=totalWushiwu+subWushiwuCount;

				int subLiushiCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						null,
						liushiStartCalander.getTime(),wushiwuStartCalander.getTime(),
						true);
				totalLiushi=totalLiushi+subLiushiCount;

				int subLiushiwuCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0, 1),
						NationType.allType(),
						null,
						liushiwuStartCalander.getTime(), liushiStartCalander.getTime(),
						true);
				totalLiushiwu=totalLiushiwu+subLiushiwuCount;

				int subQishiCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						null,
						qishiStartCalander.getTime(),liushiwuStartCalander.getTime(),
						true);
				totalQishi=totalQishi+subQishiCount;

				int subQishiMoreCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						null,
						null,qishiStartCalander.getTime(),
						true);
				totalQishimore=totalQishimore+subQishiMoreCount;
			}
		}

		JSONObject resObje=new JSONObject();
		resObje.put("success",true);

		JSONArray dataArray=new JSONArray();

		JSONObject aDataObject=new JSONObject();
		aDataObject.put("name", "30以下");
		aDataObject.put("value", totalSanshi);
		dataArray.add(aDataObject);

		JSONObject gaozhongDataObject=new JSONObject();
		gaozhongDataObject.put("name", "31-35岁");
		gaozhongDataObject.put("value", totalSanshiwu);
		dataArray.add(gaozhongDataObject);

		JSONObject zhuankeDataObject=new JSONObject();
		zhuankeDataObject.put("name", "36-40岁");
		zhuankeDataObject.put("value", totalSishi);
		dataArray.add(zhuankeDataObject);

		JSONObject benkeDataObject=new JSONObject();
		benkeDataObject.put("name", "40-45岁");
		benkeDataObject.put("value", totalSishiwu);
		dataArray.add(benkeDataObject);

		JSONObject yanjiushengDataObject=new JSONObject();
		yanjiushengDataObject.put("name", "46-50岁");
		yanjiushengDataObject.put("value", totalWushi);
		dataArray.add(yanjiushengDataObject);

		JSONObject boshiDataObject=new JSONObject();
		boshiDataObject.put("name", "51-55岁");
		boshiDataObject.put("value", totalWushiwu);
		dataArray.add(boshiDataObject);

		JSONObject qitaDataObject=new JSONObject();
		qitaDataObject.put("name", "56-60岁");
		qitaDataObject.put("value", totalLiushi);
		dataArray.add(qitaDataObject);

		JSONObject liuwuDataObject=new JSONObject();
		liuwuDataObject.put("name", "60-65岁");
		liuwuDataObject.put("value", totalLiushiwu);
		dataArray.add(liuwuDataObject);

		JSONObject qishiDataObject=new JSONObject();
		qishiDataObject.put("name", "65-70岁");
		qishiDataObject.put("value", totalQishi);
		dataArray.add(qishiDataObject);

		JSONObject qishimoreDataObject=new JSONObject();
		qishimoreDataObject.put("name", "71岁以上");
		qishimoreDataObject.put("value", totalQishimore);
		dataArray.add(qishimoreDataObject);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("series", dataArray);

		resObje.put("data",jsonObject);
		return JsonUtils.fromObject(resObje);
	}

	private String statisByDegree(Integer orgId) throws DMException {
		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		//先统计本部门的人数
		int directChuzhongCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				Arrays.asList(DegreeType.CHUZHONG.getId()),
				null,null,
				false);
		int directGaozhongCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				Arrays.asList(DegreeType.GAOZHONG.getId()),
				null,null,
				false);
		int directZhuankeCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				Arrays.asList(DegreeType.ZHUANKE.getId()),
				null,null,
				false);
		int directBenKeCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				Arrays.asList(DegreeType.BENKE.getId()),
				null,null,
				false);
		int directYanjiushengCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				Arrays.asList(DegreeType.YANJIUSHENG.getId()),
				null,null,
				false);
		int directBoshiCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				Arrays.asList(DegreeType.BOSHI.getId()),
				null,null,
				false);
		int directQitaCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				NationType.allType(),
				Arrays.asList(DegreeType.QITA.getId()),
				null,null,
				false);

		int totalChuzhong=directChuzhongCount;
		int totalGozhong=directGaozhongCount;
		int totalZhuanke=directZhuankeCount;
		int totalBenke=directBenKeCount;
		int totalYanjiusheng=directYanjiushengCount;
		int totalBoshi=directBoshiCount;
		int totalQita=directQitaCount;

		//获得直属的子组织
		List<Orgnization> subOrgList=orgnizationService.getOrgByParent(orgnization, false);
		if(subOrgList!=null){
			for(Orgnization subOrg:subOrgList){
				int subChuzhongCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						Arrays.asList(DegreeType.CHUZHONG.getId()),
						null,null,
						true);
				totalChuzhong=totalChuzhong+subChuzhongCount;

				int subGaozhongCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						Arrays.asList(DegreeType.GAOZHONG.getId()),
						null,null,
						true);
				totalGozhong=totalGozhong+subGaozhongCount;

				int subZhuankeCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						Arrays.asList(DegreeType.ZHUANKE.getId()),
						null,null,
						true);
				totalZhuanke=totalZhuanke+subZhuankeCount;

				int subBenKeCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						Arrays.asList(DegreeType.BENKE.getId()),
						null,null,
						true);
				totalBenke=totalBenke+subBenKeCount;

				int subYanjiushengCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						Arrays.asList(DegreeType.YANJIUSHENG.getId()),
						null,null,
						true);
				totalYanjiusheng=totalYanjiusheng+subYanjiushengCount;

				int subBoshiCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						Arrays.asList(DegreeType.BOSHI.getId()),
						null,null,
						true);
				totalBoshi=subBoshiCount+totalBoshi;

				int subQitaCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						NationType.allType(),
						Arrays.asList(DegreeType.QITA.getId()),
						null,null,
						true);
				totalQita=totalQita+subQitaCount;
			}
		}

		JSONObject resObje=new JSONObject();
		resObje.put("success",true);

		JSONArray dataArray=new JSONArray();

		JSONObject aDataObject=new JSONObject();
		aDataObject.put("name", "初中及以下");
		aDataObject.put("value", totalChuzhong);
		dataArray.add(aDataObject);

		JSONObject gaozhongDataObject=new JSONObject();
		gaozhongDataObject.put("name", "高中");
		gaozhongDataObject.put("value", totalGozhong);
		dataArray.add(gaozhongDataObject);

		JSONObject zhuankeDataObject=new JSONObject();
		zhuankeDataObject.put("name", "专科");
		zhuankeDataObject.put("value", totalZhuanke);
		dataArray.add(zhuankeDataObject);

		JSONObject benkeDataObject=new JSONObject();
		benkeDataObject.put("name", "本科");
		benkeDataObject.put("value", totalBenke);
		dataArray.add(benkeDataObject);

		JSONObject yanjiushengDataObject=new JSONObject();
		yanjiushengDataObject.put("name", "研究生");
		yanjiushengDataObject.put("value", totalYanjiusheng);
		dataArray.add(yanjiushengDataObject);

		JSONObject boshiDataObject=new JSONObject();
		boshiDataObject.put("name", "博士");
		boshiDataObject.put("value", totalBoshi);
		dataArray.add(boshiDataObject);

		JSONObject qitaDataObject=new JSONObject();
		qitaDataObject.put("name", "其他");
		qitaDataObject.put("value", totalQita);
		dataArray.add(qitaDataObject);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("series", dataArray);

		resObje.put("data",jsonObject);
		return JsonUtils.fromObject(resObje);
	}

	private String statisByNation(Integer orgId) throws DMException {
		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		//先统计本部门的男性人数
		int directHanCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				Arrays.asList(NationType.HANZU.getId()),
				null,
				null,null,
				false);
		int directOtherCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				Arrays.asList(NationType.BUYIZU.getId(),
						NationType.CHAOXIANZU.getId(),NationType.DAIZU.getId(),NationType.HANSAKE.getId(),
						NationType.HUIZU.getId(),NationType.MANZU.getId(),NationType.MENGGUZU.getId(),
						NationType.MIAOZU.getId(),NationType.TUJIAZU.getId(),NationType.QITA.getId(),NationType.WEIWUER.getId(),
						NationType.YIZU.getId(),NationType.ZHUANGZU.getId(),NationType.ZANGZU.getId()),
				null,
				null,null,
				false);
		int totalHan=directHanCount;
		int totalOther=directOtherCount;

		//获得直属的子组织
		List<Orgnization> subOrgList=orgnizationService.getOrgByParent(orgnization, false);
		if(subOrgList!=null){
			for(Orgnization subOrg:subOrgList){
				//统计直属自组织的所有子组织的人数
				int subHanCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						Arrays.asList(NationType.HANZU.getId()),
						null,
						null,null,
						true);
				totalHan=totalHan+subHanCount;

				int subOtherCount = personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						Arrays.asList(NationType.BUYIZU.getId(),
								NationType.CHAOXIANZU.getId(),NationType.DAIZU.getId(),NationType.HANSAKE.getId(),
								NationType.HUIZU.getId(),NationType.MANZU.getId(),NationType.MENGGUZU.getId(),
								NationType.MIAOZU.getId(),NationType.TUJIAZU.getId(),NationType.QITA.getId(),NationType.WEIWUER.getId(),
								NationType.YIZU.getId(),NationType.ZHUANGZU.getId(),NationType.ZANGZU.getId()),
						null,
						null,null,
						true);
				totalOther=totalOther+subOtherCount;
			}
		}

		JSONObject resObje=new JSONObject();
		resObje.put("success",true);

		JSONArray dataArray=new JSONArray();

		JSONObject aDataObject=new JSONObject();
		aDataObject.put("name", "汉族");
		aDataObject.put("value", totalHan);
		dataArray.add(aDataObject);

		JSONObject bDataObject=new JSONObject();
		bDataObject.put("name", "其他");
		bDataObject.put("value", totalOther);
		dataArray.add(bDataObject);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("series", dataArray);

		resObje.put("data",jsonObject);
		return JsonUtils.fromObject(resObje);
	}

	private String statisBySex(Integer orgId) throws DMException {
		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		//先统计本部门的男性人数
		int directBoyCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0),
				null,
				null,
				null,null,
				false);
		int directGirlCount = personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(1),
				null,
				null,
				null,null,
				false);
		int totalBoy=directBoyCount;
		int totalGirl=directGirlCount;

		//获得直属的子组织
		List<Orgnization> subOrgList=orgnizationService.getOrgByParent(orgnization, false);
		if(subOrgList!=null){
			for(Orgnization subOrg:subOrgList){
				//统计直属自组织的所有子组织的人数
				int subBoyCount=personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0),
						null,
						null,
						null,null,
						true);
				totalBoy=totalBoy+subBoyCount;
				int subGirlCount=personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(1),
						null,
						null,
						null,null,
						true);
				totalGirl=totalGirl+subGirlCount;
			}
		}

		JSONObject resObje=new JSONObject();
		resObje.put("success",true);

		JSONArray dataArray=new JSONArray();

		JSONObject aDataObject=new JSONObject();
		aDataObject.put("name", "男");
		aDataObject.put("value", totalBoy);
		dataArray.add(aDataObject);

		JSONObject bDataObject=new JSONObject();
		bDataObject.put("name", "女");
		bDataObject.put("value", totalGirl);
		dataArray.add(bDataObject);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("series", dataArray);

		resObje.put("data",jsonObject);
		return JsonUtils.fromObject(resObje);
	}

	private String statisByOrg(Integer orgId) throws DMException {
		List<String> orgNames=new ArrayList<String>();
		List<Integer> normalCountArray=new ArrayList<Integer>();

		//先统计本党委党员的人数
		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		int directCount=personService.countByOrgWithStatus(orgnization,
				statisPersonStatus(),
				Arrays.asList(0,1),
				null,
				null,
				null,null,
				false);

		orgNames.add(orgnization.getName());//直属组织的名字
		normalCountArray.add(directCount);

		//获得直属的子组织
		List<Orgnization> subOrgList=orgnizationService.getOrgByParent(orgnization, false);
		if(subOrgList!=null){
			for(Orgnization subOrg:subOrgList){
				//统计直属自组织的所有子组织的人数
				int subCount=personService.countByOrgWithStatus(subOrg,
						statisPersonStatus(),
						Arrays.asList(0,1),
						null,
						null,
						null,null,
						true);
				orgNames.add(subOrg.getName());
				normalCountArray.add(subCount);
			}
		}
		JSONObject resObje=new JSONObject();
		resObje.put("success",true);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("x", orgNames);

		JSONArray dataArray=new JSONArray();
		JSONObject aDataObject=new JSONObject();
		aDataObject.put("name", "人员数量");
		aDataObject.put("data", normalCountArray);
		dataArray.add(aDataObject);

		jsonObject.put("series", dataArray);

		resObje.put("data",jsonObject);

		return JsonUtils.fromObject(resObje);
	}

	/**
	 * 统计最近十年成为党员的数量
	 * 外部来源的统计成为党员的时间
	 * 内部来源统计成为预备党员的时间
	 * @param orgId
	 * @return
	 * @throws DMException
	 */
	private String statisByTime(Integer orgId) throws DMException {
		List<String> yearList=new ArrayList<String>();
		List<Integer> normalCountArray=new ArrayList<Integer>();

		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		List<Orgnization> subOrgList = orgnizationService.getOrgByParent(orgnization, true);
		List<Integer> orgIdList=new ArrayList<Integer>();
		orgIdList.add(orgId);
		if(subOrgList!=null){
			for (Orgnization subOrg:subOrgList){
				orgIdList.add(subOrg.getId());
			}
		}

		//明年
		Calendar nowCalender=Calendar.getInstance();
		nowCalender.setTime(new Date());
		int endYear=nowCalender.get(Calendar.YEAR)+1;

		for(int i=endYear-5;i<endYear;i++){
			yearList.add(i+"年");
			Calendar startCalender=Calendar.getInstance();
			startCalender.set(i, 0, 1, 0, 0, 0);

			Calendar endCalender=Calendar.getInstance();
			endCalender.set(i+1, 0, 1, 0, 0, 0);

			//部门里外部来源的的党员，统计入党时间
			int totalOut=personService.countBySourceAndTime(orgnization, Person.SOURCE_OUT, statisPersonStatus(),startCalender.getTime(),endCalender.getTime(),true);

			int totalInner=personService.countBySourceAndTime(orgnization, Person.SOURCE_DEFAULT, statisPersonStatus(), startCalender.getTime(), endCalender.getTime(), true);

			normalCountArray.add(totalInner+totalOut);
		}

		JSONObject resObje=new JSONObject();
		resObje.put("success",true);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("x", yearList);

		JSONArray dataArray=new JSONArray();
		JSONObject aDataObject=new JSONObject();
		aDataObject.put("name", "预备党员+正式党员+在编党员");
		aDataObject.put("data", normalCountArray);
		dataArray.add(aDataObject);

		jsonObject.put("series", dataArray);

		resObje.put("data",jsonObject);

		return JsonUtils.fromObject(resObje);
	}
	/**
	 * 快速转换人员为下一个状态
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/fastChange.do")
	@ResponseBody
	public String fastChange(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int id=request.getInt("id");
		JSONObject jsonObject=new JSONObject();

		Person person=personService.getById(id);
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		if(person.getStatus()==PersonStatus.NO.getId()){
			//转换为申请人
			ApplierInfo applierInfo=new ApplierInfo();
			applierService.createApplier(applierInfo);
			//修改基础人员状态
			person.setStatus(PersonStatus.APPLIER.getId());
			person.setApplierInfoId(applierInfo.getId());
			personService.updatePerson(person);
		}else if(person.getStatus()==PersonStatus.APPLIER.getId()){
			ActivitistInfo activitistInfo=new ActivitistInfo();
			activitistService.createActivitist(activitistInfo);
			//修改基础人员状态
			person.setStatus(PersonStatus.ACTIVISTS.getId());
			person.setActivitistInfoId(activitistInfo.getId());
			personService.updatePerson(person);
		}else if(person.getStatus()==PersonStatus.ACTIVISTS.getId()){
			IntentionInfo intentionInfo=new IntentionInfo();
			intentionService.createIntention(intentionInfo);
			//修改基础人员状态
			person.setStatus(PersonStatus.INTENTION.getId());
			person.setIntentionInfoId(intentionInfo.getId());
			personService.updatePerson(person);
		}else if(person.getStatus()==PersonStatus.INTENTION.getId()){
			PrepareInfo prepareInfo=new PrepareInfo();
			prepareService.createPrepare(prepareInfo);
			//修改基础人员状态
			person.setStatus(PersonStatus.PERPARE.getId());
			person.setPrepareInfoId(prepareInfo.getId());
			personService.updatePerson(person);

		}else if(person.getStatus()==PersonStatus.PERPARE.getId()){
			NormalInfo normalInfo=new NormalInfo();
			normalService.createNormal(normalInfo);
			//修改基础人员状态
			person.setStatus(PersonStatus.NORMAL.getId());
			person.setNormalInfoId(normalInfo.getId());
			personService.updatePerson(person);
		}
		jsonObject.put("status", true);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 添加历史党员信息
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/historyAdd.do")
	@ResponseBody
	public String historyAdd(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		Integer orgId = request.getInt("orgId");
		String number=request.getString("number");
		int type=request.getInt("type", 2);

		JSONObject jsonObject=new JSONObject();

		Person person=personService.getByNumber(number);
		if(person==null){
			jsonObject.put("success", false);
			jsonObject.put("msg","不存在该记录");
			return JsonUtils.fromObject(jsonObject);
		}
		if(type==2){
			//开除党籍
			person.setStatus(PersonStatus.HISTORY_PUNISH.getId());
			person.setUpdateTime(new Date());
		}else if(type==1){
			//死亡
			person.setStatus(PersonStatus.HISTORY_DEATH.getId());
			person.setUpdateTime(new Date());
		}
		personService.updatePerson(person);

		jsonObject.put("success", true);
		jsonObject.put("msg", "success");
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 *
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/formal/import.do")
	@ResponseBody
	public String resourceFileUpload(@RequestParam(value = "file") MultipartFile file,
									 HttpServletRequest request) throws Exception {
		int orgId=getRequest(request).getInt("orgId");
		Orgnization orgnization=orgnizationService.getOrgById(orgId);

		List<Person> personList=null;
		try{
			personList=parsePersonListCsv(file,orgnization);
		}catch (Exception e){
			LOGGER.error("parse file fail",e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("msg","文件格式错误");
			return JsonUtils.fromObject(jsonObject);
		}

		if(personList.size()>50){
			throw new DMException("批量导入最多导入50人");
		}
		//
		int successCount=0,failCount=0;
		StringBuffer sb=new StringBuffer("失败人员学号:");
		StringBuffer sbsb=new StringBuffer(",已存在学号:");
		for(Person person:personList){
			if(personService.getByNumber(person.getNumber())==null){
				//已经存在
				try{
					personService.createPerson(person);
					successCount++;
				}catch (Exception e){
					LOGGER.error("create person fail",e);
					sb.append(person.getNumber()).append(",");
					failCount++;
				}
			}else{
				sbsb.append(person.getNumber()).append(",");
				failCount++;
			}
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", successCount>0?true:false);
		jsonObject.put("successCount", successCount);
		jsonObject.put("failCount", failCount);
		jsonObject.put("msg", sb.append(sbsb).toString());

		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 解析CSV格式文件
	 * @param file
	 * @param orgnization
	 * @return
	 * @throws DMException
	 * @throws IOException
	 */
	private List<Person> parsePersonListCsv(MultipartFile file,Orgnization orgnization) throws DMException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
		try {
			// 忽略第一行
			String line = reader.readLine();
			if (line == null)
				throw new DMException("数据不能为空");

			List<Person> list = new ArrayList<Person>();
			while ((line = reader.readLine()) != null) {
				// 忽略空行
				line = line.trim();
				if (line.equals("")) {
					continue;
				}
				List<String> result = Csv.parseLine(line);
				Person person = new Person();
				//
				if(StringUtils.isEmpty(result.get(0))){
					continue;
				}
				person.setNumber(result.get(0).trim());
				//类型
				String typeName=result.get(1);
				if(StringUtils.isEmpty(typeName)){
					typeName="学生";
				}
				if("学生".equals(typeName.trim())){
					person.setType(PersonType.STUDENT.getId());
				}else if("教工".equals(typeName)) {
					person.setType(PersonType.TEACHER.getId());
				}else{
					person.setType(PersonType.OTHER.getId());
				}
				//姓名
				person.setName(result.get(2));
				//性别
				String sexParam=result.get(3);
				if("男".equals(sexParam.trim())){
					person.setSex(0);
				}else{
					person.setSex(1);
				}
				//出生年月
				String birth=result.get(4);
				if(!StringUtils.isEmpty(birth)){
					if(birth.length()==7){
						birth=birth+"-01";
					}
					birth=birth.trim();
					person.setBirth(TimeUtils.convertStringToDate(birth)==null?TimeUtils.convertStringToDate(birth,"yyyy/MM/dd"):TimeUtils.convertStringToDate(birth));
				}
				//民族
				person.setNation(NationType.getInstanceByName(result.get(5)).getId());
				//籍贯
				String jgParam=result.get(6);
				person.setHometown(jgParam);
				//入党日期
				String rdrq=result.get(7);
				if(!StringUtils.isEmpty(rdrq)){
					if(rdrq.length()==7){
						rdrq=rdrq+"-01";
					}
					rdrq=rdrq.trim();
					person.setBePatyDate(TimeUtils.convertStringToDate(rdrq) == null ? TimeUtils.convertStringToDate(rdrq, "yyyy/MM/dd") : TimeUtils.convertStringToDate(rdrq));
				}
				//参加工作时间
				String cjg=result.get(8);
				if(!StringUtils.isEmpty(cjg)){
					if(cjg.length()==7){
						cjg=cjg+"-01";
					}
					cjg=cjg.trim();
					person.setRollDate(TimeUtils.convertStringToDate(cjg)==null?TimeUtils.convertStringToDate(cjg,"yyyy/MM/dd"):TimeUtils.convertStringToDate(cjg));
				}
				//学历
				person.setDegree(DegreeType.getInstanceByName(result.get(9)).getId());
				//
				person.setProfession(result.get(10));
				//专业班级
				person.setProfessionalClass(result.get(11));
				//设置在编党员信息
				person.setStatus(PersonStatus.FORMAL.getId());
				person.setSource(Person.SOURCE_OUT);
				person.setPassword(StringUtils.md5(person.getNumber()));
				person.setOrgnization(orgnization);

				list.add(person);
			}
			return list;
		} finally {
			reader.close();
		}
	}
	/**
	 *
	 * @param file
	 * @return
	 */
	private List<Person> parsePersonList(MultipartFile file,Orgnization orgnization) throws IOException ,DMException{
		Workbook workbook=null;
		try {
			workbook=new HSSFWorkbook(file.getInputStream());
		}catch (Exception e){
			workbook=new XSSFWorkbook(file.getInputStream());
		}
		List<Person> list = new ArrayList<Person>();

		Sheet sheet=workbook.getSheetAt(0);

		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row hssfRow = sheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			Person person = new Person();
			//学号
			Cell xh = hssfRow.getCell(0);
			if (StringUtils.isEmpty(xh.getStringCellValue())){
				continue;
			}
			person.setNumber(xh.getStringCellValue().trim());
			//类型
			Cell type = hssfRow.getCell(1);
			String typeName=type.getStringCellValue();
			if("学生".equals(typeName.trim())){
				person.setType(PersonType.STUDENT.getId());
			}else if("教工".equals(typeName)) {
				person.setType(PersonType.TEACHER.getId());
			}else{
				person.setType(PersonType.OTHER.getId());
			}
			//姓名
			Cell xm = hssfRow.getCell(2);
			person.setName(xm.getStringCellValue().trim());
			//性别
			Cell sfz = hssfRow.getCell(3);
			String sexParam=sfz.getStringCellValue();
			if("男".equals(sexParam.trim())){
				person.setSex(0);
			}else{
				person.setSex(1);
			}
			//出生年月
			Cell csny = hssfRow.getCell(4);
			person.setBirth(csny.getDateCellValue());
			//民族
			Cell mz = hssfRow.getCell(5);
			person.setNation(NationType.getInstanceByName(mz.getStringCellValue().trim()).getId());
			//籍贯
			Cell jg = hssfRow.getCell(6);
			String jgParam=jg.getStringCellValue();
			person.setHometown(jgParam);
			//入党日期
			Cell rdrq = hssfRow.getCell(7);
			person.setBePatyDate(rdrq.getDateCellValue());
			//参加工作时间
			Cell cjgz = hssfRow.getCell(8);
			person.setRollDate(cjgz.getDateCellValue());
			//学历
			Cell xueli = hssfRow.getCell(9);
			person.setDegree(DegreeType.getInstanceByName(xueli.getStringCellValue()).getId());
			//参加工作时间
			Cell zc = hssfRow.getCell(10);
			person.setProfession(zc.getStringCellValue());
			//专业班级
			Cell bc = hssfRow.getCell(11);
			person.setProfessionalClass(bc.getStringCellValue());
			//设置在编党员信息
			person.setStatus(PersonStatus.FORMAL.getId());
			person.setSource(Person.SOURCE_DEFAULT);
			person.setPassword(StringUtils.md5(person.getNumber()));
			person.setOrgnization(orgnization);
			list.add(person);
		}
		return list;
	}

	@RequestMapping("/resetPass.do")
	@ResponseBody
	public String resetPass(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int personId = request.getInt("personId");
		String password=request.getString("password");

		Person person=personService.getById(personId);
		if(person!=null){
			person.setPassword(StringUtils.md5(password));
			person.setPassUpdateTime(new Date());
			personService.updatePassword(person);
		}
		JSONObject orgObject=new JSONObject();
		orgObject.put("success",true);
		orgObject.put("message", "success");
		return JsonUtils.fromObject(orgObject);
	}

}
