package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IOrgMeetingDao;
import com.alan.dm.dao.mapper.OrgMeetingMapper;
import com.alan.dm.entity.OrgMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgMeetingCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织活动DAO层实现
 * @Date: 2015-11-15
 * @author: fan
 */
@Repository(value = "orgMeetingDao")
public class OrgMeetingDaoImpl implements IOrgMeetingDao {

    @Autowired
    private OrgMeetingMapper orgMeetingMapper;

    @Override
    public int insert(OrgMeeting orgMeeting) throws DMException {
        return orgMeetingMapper.insert(orgMeeting);
    }

    @Override
    public void delete(OrgMeeting orgMeeting) throws DMException {
        orgMeetingMapper.delete(orgMeeting);
    }

    @Override
    public void update(OrgMeeting orgMeeting) throws DMException {
        orgMeetingMapper.update(orgMeeting);
    }

    @Override
    public OrgMeeting findOne(int id) throws DMException {
        return null;
    }

    @Override
    public List<OrgMeeting> getByCondition(OrgMeetingCondition condition, Page page) throws DMException {
        return orgMeetingMapper.getByCondition(condition, page);
    }

    @Override
    public int countByCondition(OrgMeetingCondition condition) throws DMException {
        return orgMeetingMapper.countByCondition(condition);
    }
}
