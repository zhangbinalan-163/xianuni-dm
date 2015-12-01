package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.dao.IMessageDao;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.AdminCondition;
import com.alan.dm.entity.condition.MessageCondition;
import com.alan.dm.entity.condition.OrgRewardCondition;
import com.alan.dm.service.IAdminService;
import com.alan.dm.service.IMessageService;
import com.alan.dm.service.IOrgnizationService;
import com.alan.dm.service.IPersonService;
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
@RequestMapping("/message")
public class MessageController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(MessageController.class);

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	@Resource(name = "adminService")
	private IAdminService adminService;

	@Resource(name = "messageService")
	private IMessageService messageService;

	@Resource(name = "personService")
	private IPersonService personService;

	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int messageId=request.getInt("id");
		Message message=messageService.getById(messageId);
		if(message!=null){
			messageService.deleteMessage(message);
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
		int orgId=request.getInt("orgId");
		String toSubParam=request.getString("toSub", "1");
		boolean toSub=toSubParam.equals("1");
		String urlList=request.getString("fileUrlList",",");

		Message message=new Message();
		message.setContent(content);
		Orgnization orgnization=orgnizationService.getOrgById(orgId);
		message.setOrgnization(orgnization);
		message.setToSub(toSub);
		message.setUrlList(urlList);

		JSONObject jsonObject=new JSONObject();

		messageService.createMessage(message);

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
		//获取当前登录账号、或者管理员的ID
		int adminId=getOnlineAdminId(httpServletRequest);
		Admin adminInfo = adminService.getById(adminId);
		Person person=personService.getByNumber(adminInfo.getSchoolNumber());
		Orgnization orgnization = orgnizationService.getOrgById(person.getOrgId());
		List<Orgnization> parentOrgList=orgnizationService.getParentOrg(orgnization);

		MessageCondition condition=new MessageCondition();
		if(parentOrgList!=null&&parentOrgList.size()>0){
			List<Integer> orgIdList=new ArrayList<Integer>();
			for(Orgnization parentOrg:parentOrgList){
				orgIdList.add(parentOrg.getId());
			}
			condition.setOrgList(orgIdList);
		}
		condition.setOrgId(orgnization.getId());
		condition.setPersonQuery(true);
		Page pageInfo=new Page();
		pageInfo.setCurrent(0);
		pageInfo.setSize(10);

		List<Message> messageList=messageService.getMessageByCondtition(condition,pageInfo);
		JSONObject jsonObject=new JSONObject();
		JSONArray dataArray=new JSONArray();
		if(messageList!=null){
			for(Message message:messageList){
				JSONObject itemObj=new JSONObject();
				itemObj.put("id",message.getId());
				itemObj.put("time",TimeUtils.convertToTimeString(message.getCreateTime()));
				itemObj.put("content",message.getContent());
				itemObj.put("publisher","#管理员#");
				dataArray.add(itemObj);
			}
		}
		jsonObject.put("list",dataArray);
		return JsonUtils.fromObject(jsonObject);
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
		//加入权限控制，
		Request request = getRequest(httpServletRequest);
		Integer limit = request.getInt("rows", 10);
		Integer page = request.getInt("page", 1);
		Integer orgId=request.getInt("orgId", 0);
		boolean containSubOrg=request.getBoolean("containSub", true);
		boolean toSub=request.getBoolean("toSub", true);

		//如果没有传入orgId，设置为管理员所管理的ORG
		if(orgId==0){
			Integer adminId = getOnlineAdminId(httpServletRequest);
			Admin adminInfo = adminService.getById(adminId);
			if(adminInfo.getType()==Admin.ORG_ADMIN){
				orgId=adminInfo.getOrgId();
			}
		}
		MessageCondition condition=new MessageCondition();
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
		condition.setToSub(toSub);

		Page pageInfo=new Page();
		pageInfo.setCurrent((page - 1) * limit);
		pageInfo.setSize(limit);

		int count=messageService.countMessageByCondtition(condition);
		List<Message> messageList = messageService.getMessageByCondtition(condition, pageInfo);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("page",page);
		jsonObject.put("total",count==0?0:count/limit+1);
		jsonObject.put("records",count);
		JSONArray rowsArray=new JSONArray();

		if(messageList!=null){
			for(Message message:messageList){
				JSONObject subOrgObject=new JSONObject();
				subOrgObject.put("id", message.getId());
				List<String> cellList=new ArrayList<String>();
				cellList.add(String.valueOf(message.getId()));
				cellList.add(message.getOrgnization().getName());
				cellList.add(message.getContent());
				cellList.add(message.isToSub()?"是":"否");
				cellList.add(TimeUtils.convertToDateString(message.getCreateTime()));
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
