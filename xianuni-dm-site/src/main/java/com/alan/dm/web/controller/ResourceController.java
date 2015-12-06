package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.MessageCondition;
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
 *学习资源管理接口
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController{
	private static Logger LOGGER= LoggerFactory.getLogger(ResourceController.class);

	@Resource(name = "orgnizationService")
	private IOrgnizationService orgnizationService;

	@Resource(name = "adminService")
	private IAdminService adminService;

	@Resource(name = "resourceService")
	private IResourceService resourceService;

	@Resource(name = "personService")
	private IPersonService personService;

	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(HttpServletRequest httpServletRequest) throws Exception {
		Request request = getRequest(httpServletRequest);
		int messageId=request.getInt("id");
		MediaResource resource=resourceService.getById(messageId);
		if(resource!=null){
			resourceService.delete(resource);
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
	public String personListResource(HttpServletRequest httpServletRequest) throws Exception {
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
		return null;
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
