package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.MessageCondition;
import com.alan.dm.entity.query.MailCondition;
import com.alan.dm.entity.query.PersonResult;
import com.alan.dm.service.*;
import com.alan.dm.web.vo.Request;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/mail")
public class MailController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(MailController.class);

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;


	@Resource(name = "messageService")
	private IMessageService messageService;

	@Resource(name = "personService")
	private IPersonService personService;

	@Resource(name = "mailService")
	private MailService mailService;

	@Resource(name = "adminService")
	private IAdminService adminService;

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
		int mailId=request.getInt("id");
		MailInfo mailInfo=mailService.getById(mailId);
		if(mailInfo!=null){
			mailService.deleteMail(mailInfo);
		}
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
		String content = request.getString("content");
		String title = request.getString("title");
		String number = request.getString("number");
		int orgId=request.getInt("orgId");
		Integer adminId = getOnlineAdminId(httpServletRequest);
		Admin admin=adminService.getById(adminId);
		if(admin==null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			return JsonUtils.fromObject(jsonObject);
		}
		Person person = personService.getByNumber(number);
		if(person!=null){
			MailInfo mailInfo=new MailInfo();
			mailInfo.setContent(content);
			mailInfo.setTitle(title);
			mailInfo.setCreateTime(new Date());
			mailInfo.setPersonId(person.getId());
			mailInfo.setReaded(false);
			mailInfo.setAdminId(admin.getId());
			mailService.sendMail(mailInfo);
		}
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
	@RequestMapping("/update.do")
	@ResponseBody
	public String update(HttpServletRequest httpServletRequest) throws Exception {
		//TODO
		return null;
	}

	@RequestMapping("/info.do")
	@ResponseBody
	public String messageInfo(HttpServletRequest httpServletRequest) throws Exception {
		return null;
	}

	/**
	 * 展现给个人的信息列表
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/person/list.do")
	@ResponseBody
	public String personListMessage(HttpServletRequest httpServletRequest) throws Exception {
		return null;
	}
	/**
	 * 列表
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public String list(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		String number = request.getString("number", null);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		boolean containSubOrg=request.getBoolean("containSub", true);

		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}

		MailCondition condition=new MailCondition();
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

		Integer adminId = getOnlineAdminId(httpServletRequest);
		Admin admin=adminService.getById(adminId);
		if(admin==null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", false);
			return JsonUtils.fromObject(jsonObject);
		}
		condition.setAdminId(admin.getId());
		if(!StringUtils.isEmpty(number)){
			condition.setNumber(number);
		}
		Page pageInfo=new Page();
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);

		int subCount = mailService.countByCondition(condition);
		List<MailInfo> mailInfoList=mailService.getByCondition(condition,pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",subCount==0?0:subCount/limit+1);
		jsonObject.put("records",subCount);
		JSONArray rowsArray=new JSONArray();
		if(mailInfoList!=null){
			for(MailInfo mailInfo:mailInfoList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", mailInfo.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(mailInfo.getId()));
				Person person=mailInfo.getPerson();
				cellList.add(person.getName());
				cellList.add(person.getNumber());
				cellList.add(PersonType.getInstance(person.getType()).getName()+"_"+PersonStatus.getInstance(person.getStatus()).getName());
				cellList.add(mailInfo.getTitle());
				cellList.add(TimeUtils.convertToDateString(mailInfo.getCreateTime()));
				subOrgObject.put("cell", cellList);
				rowsArray.add(subOrgObject);
			}
		}
		jsonObject.put("rows",rowsArray);
		return JsonUtils.fromObject(jsonObject);
	}

	/**
	 * 消息资源的上传
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resource/file/upload.do")
	@ResponseBody
	public String resourceFileUpload(@RequestParam(value = "file") MultipartFile file,
									 HttpServletRequest request) throws Exception {
		String fileName = file.getOriginalFilename();
		String extType=fileName.substring(fileName.lastIndexOf("."));
		String newFileName=StringUtils.md5(fileName+System.currentTimeMillis())+extType;

		String path=request.getServletContext().getRealPath("/resource/");
		String today=TimeUtils.convertToDateString(new Date());
		path=path+today;

		File targetFile = new File(path, newFileName);
		targetFile.mkdirs();

		//保存
		file.transferTo(targetFile);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success",true);
		jsonObject.put("url",request.getContextPath()+"/resource/"+today+"/"+newFileName);
		return JsonUtils.fromObject(jsonObject);
	}
}
