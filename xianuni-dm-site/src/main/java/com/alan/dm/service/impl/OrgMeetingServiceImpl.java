package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IOrgMeetingDao;
import com.alan.dm.entity.OrgMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgMeetingCondition;
import com.alan.dm.service.IOrgMeetingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织活动会议业务层
 * @Date: 2015-11-18
 * @author: fan
 */
@Service(value = "orgMeetingService")
public class OrgMeetingServiceImpl implements IOrgMeetingService {
    @Resource(name = "orgMeetingDao")
    private IOrgMeetingDao orgMeetingDao;

    @Override
    public int addMeeting(OrgMeeting orgMeeting) throws DMException {
        return 0;
    }

    @Override
    public void delete(int id) throws DMException {

    }

    @Override
    public void update(OrgMeeting orgMeeting) throws DMException {

    }

    @Override
    public List<OrgMeeting> getByCondition(OrgMeetingCondition condition, Page page) throws DMException {
        return orgMeetingDao.getByCondition(condition, page);
    }

    @Override
    public int countByCondition(OrgMeetingCondition condition) throws DMException {
        return orgMeetingDao.countByCondition(condition);
    }

    @Override
    public OrgMeeting getMeeting(int id) throws DMException {
        return null;
    }
}
