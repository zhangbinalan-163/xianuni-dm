package com.alan.dm.web.controller;

import com.alan.dm.common.util.JsonUtils;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.EduTrainingCondition;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 教育培训，专题教育控制层
 * @Date: 2015-11-19
 * @author: fan
 */
@Controller
@RequestMapping(value = "/train")
public class EduTrainingController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgMeetingController.class);

    @javax.annotation.Resource(name = "eduTrainingService")
    private IEduTrainingService eduTrainingService;

    @javax.annotation.Resource(name = "personService")
    private IPersonService personService;

    @javax.annotation.Resource(name = "adminService")
    private IAdminService adminService;

    @javax.annotation.Resource(name = "orgnizationService")
    private IOrgnizationService orgnizationService;

    /**
     *
     */
    @RequestMapping("/add.do")
    @ResponseBody
    public String add(HttpServletRequest httpServletRequest) throws Exception {
        Request request=getRequest(httpServletRequest);

        Integer orgId=request.getInt("orgId");
        Date startTime=request.getDate("startTime", "YYYY-MM-DD hh:mm:ss");
        Date endTime=request.getDate("endTime", "YYYY-MM-DD hh:mm:ss");
        String theme=request.getString("theme");
        String content=request.getString("content", "");
        String filUrlList=request.getString("fileUrlList",null);
        String filNameList=request.getString("fileNameList",null);

        EduTraining eduTraining=new EduTraining();
        eduTraining.setEndTime(endTime);
        eduTraining.setStartTime(startTime);
        eduTraining.setCreateTime(new Date());
        eduTraining.setContent(content);
        Orgnization orgnization=orgnizationService.getOrgById(orgId);
        eduTraining.setOrganization(orgnization);
        eduTraining.setTitle(theme);

        if(!StringUtils.isEmpty(filUrlList)&&!StringUtils.isEmpty(filNameList)){
            String[] fileArray=filUrlList.split(",");
            String[] fileNameArray=filNameList.split(",");
            List<Resource> resources=new ArrayList<Resource>();
            for(int i=0;i<fileArray.length;i++){
                if(!StringUtils.isEmpty(fileArray[i])){
                    Resource resource=new Resource();
                    resource.setRealName(fileNameArray[i]);
                    resource.setResourcePath(fileArray[i]);
                    resource.setCreateTime(new Date());
                    resources.add(resource);
                }
            }
            eduTraining.setResourceList(resources);
        }

        eduTrainingService.addTraining(eduTraining);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success", true);
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
        Request request = getRequest(httpServletRequest);
        Integer orgId = request.getInt("orgId", 0);
        Integer limit = request.getInt("rows", 10);
        Integer page = request.getInt("page", 1);
        Integer type = request.getInt("type", 1);

        boolean containSubOrg=request.getBoolean("containSub", true);
        //如果没有传入orgId，设置为管理员所管理的ORG
        if(orgId==0){
            Integer adminId = getOnlineAdminId(httpServletRequest);
            Admin adminInfo = adminService.getById(adminId);
            if(adminInfo.getType()==Admin.ORG_ADMIN){
                orgId=adminInfo.getOrgId();
            }
        }
        EduTrainingCondition condition=new EduTrainingCondition();
        List<Integer> orgIdList=new ArrayList<Integer>();
        orgIdList.add(orgId);

        if(containSubOrg){
            Orgnization orgnization=null;
            if(orgId == 0) {
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

        int count = eduTrainingService.countTrainByCondtion(condition);
        List<EduTraining> trainingList=eduTrainingService.getTrainingByCondtion(condition, pageInfo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("page", page);
        jsonObject.put("total", count == 0 ? 0 : count / limit + 1);
        jsonObject.put("records", count);

        JSONArray rowsArray = new JSONArray();
        if (trainingList!=null){
            for (EduTraining eduTraining : trainingList) {
                JSONObject subOrgObject = new JSONObject();
                subOrgObject.put("id", eduTraining.getId());
                List<String> cellList=new ArrayList<String>();
                cellList.add(String.valueOf(eduTraining.getId()));
                cellList.add(eduTraining.getOrganization().getName());
                cellList.add(eduTraining.getTitle());
                cellList.add(TimeUtils.convertToTimeString(eduTraining.getStartTime()));
                cellList.add(TimeUtils.convertToTimeString(eduTraining.getEndTime()));
                subOrgObject.put("cell", cellList);
                rowsArray.add(subOrgObject);
            }
        }
        jsonObject.put("rows",rowsArray);
        return JsonUtils.fromObject(jsonObject);
    }

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
        String[] ids=request.getStringArray("id", ",");
        for(String id:ids){
            EduTraining eduTraining=eduTrainingService.getById(Integer.parseInt(id));
            if(eduTraining!=null){
                eduTrainingService.deleteTraining(eduTraining);
            }
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status",true);
        return JsonUtils.fromObject(jsonObject);
    }
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
        int trainId=request.getInt("id");
        EduTraining trainInfo = eduTrainingService.getById(trainId);
        JSONObject jsonObject=new JSONObject();
        if(trainInfo!=null){
            jsonObject.put("success",true);
            jsonObject.put("title",trainInfo.getTitle());
            Orgnization orgnization = orgnizationService.getOrgById(trainInfo.getId());
            jsonObject.put("orgName",orgnization.getName());
            jsonObject.put("content",trainInfo.getContent());
            jsonObject.put("createTime", TimeUtils.convertToTimeString(trainInfo.getCreateTime()));
            jsonObject.put("endTime",TimeUtils.convertToTimeString(trainInfo.getEndTime()));
            jsonObject.put("startTime",TimeUtils.convertToTimeString(trainInfo.getStartTime()));
            JSONArray jsonArray=new JSONArray();
            List<Resource> resourceList = trainInfo.getResourceList();
            if(resourceList!=null){
                for(Resource resource:resourceList){
                    JSONObject fileObj=new JSONObject();
                    fileObj.put("name",resource.getRealName());
                    fileObj.put("url",request.getHttpServletRequest().getContextPath()+resource.getResourcePath());
                    jsonArray.add(fileObj);
                }
            }
            jsonObject.put("fileUrlList",jsonArray);
            return JsonUtils.fromObject(jsonObject);
        }
        jsonObject.put("success",true);
        return JsonUtils.fromObject(jsonObject);
    }
    /**
     * 资源的上传
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/file/upload.do")
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
        jsonObject.put("url","/resource/"+today+"/"+newFileName);
        jsonObject.put("name",fileName.replace(",","_"));
        return JsonUtils.fromObject(jsonObject);
    }
}
