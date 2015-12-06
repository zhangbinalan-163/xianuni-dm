package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.TimeUtils;
import com.alan.dm.dao.mapper.MailMapper;
import com.alan.dm.dao.mapper.OrgMeetingMapper;
import com.alan.dm.dao.mapper.PersonMeetingMapper;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.OrgMeetingCondition;
import com.alan.dm.service.IOrgMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 组织活动会议业务层
 * @Date: 2015-11-18
 * @author: fan
 */
@Service(value = "orgMeetingService")
public class OrgMeetingServiceImpl implements IOrgMeetingService {
    @Autowired
    private OrgMeetingMapper orgMeetingMapper;

    @Autowired
    private PersonMeetingMapper personMeetingMapper;

    @Autowired
    private MailMapper mailMapper;

    @Override
    public void addMeeting(OrgMeeting orgMeeting) throws DMException {
        //数据库新增会议
        orgMeetingMapper.insert(orgMeeting);
        //绑定参与人会议的参与关系
        List<Person> personList = orgMeeting.getInvitePersons();
        for(Person person :personList){
            PersonMeeting personMeeting=new PersonMeeting();
            personMeeting.setPerson(person);
            personMeeting.setOrgMeeting(orgMeeting);
            personMeetingMapper.insert(personMeeting);
            //发站内通知信
            MailInfo mailInfo=new MailInfo();
            mailInfo.setPersonId(person.getId());
            mailInfo.setCreateTime(new Date());
            mailInfo.setTitle("会议提醒:" + orgMeeting.getTheme());
            mailInfo.setContent("管理员邀请求参加会议，会议主题:"+orgMeeting.getTheme()+",会议地点："+orgMeeting.getLocation()+",会议开始时间:"+ TimeUtils.convertToTimeString(orgMeeting.getStartTime()));
            mailMapper.insert(mailInfo);
        }
        //
        //附件信息增加
        List<Resource> resourceList = orgMeeting.getResourceList();
        if(resourceList!=null){
            for(Resource resource:resourceList){
                resource.setMeetingId(orgMeeting.getId());
                orgMeetingMapper.insertResource(resource);
            }
        }
    }

    @Override
    public void delete(OrgMeeting orgMeeting) throws DMException {
        //附件信息删除
        orgMeetingMapper.deleteResource(orgMeeting.getId());
        //会议删除
        orgMeetingMapper.delete(orgMeeting);
    }

    @Override
    public void update(OrgMeeting orgMeeting) throws DMException {
        //附件信息删除
        orgMeetingMapper.deleteResource(orgMeeting.getId());
        //添加
        List<Resource> resourceList = orgMeeting.getResourceList();
        if(resourceList!=null){
            for(Resource resource:resourceList){
                resource.setMeetingId(orgMeeting.getId());
                orgMeetingMapper.insertResource(resource);
            }
        }
        //修改信息
        orgMeetingMapper.update(orgMeeting);
    }

    @Override
    public List<OrgMeeting> getByCondition(OrgMeetingCondition condition, Page page) throws DMException {
        return orgMeetingMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(OrgMeetingCondition condition) throws DMException {
        return orgMeetingMapper.countByCondition(condition);
    }

    @Override
    public OrgMeeting getById(int id) throws DMException {
        return orgMeetingMapper.getById(id);
    }
}
