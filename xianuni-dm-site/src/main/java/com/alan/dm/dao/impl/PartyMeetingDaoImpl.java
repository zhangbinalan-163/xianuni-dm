package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPartyMeetingDao;
import com.alan.dm.entity.OrganizationMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MeetingCondition;

import java.util.List;

/**
 * 组织活动DAO层实现
 * @Date: 2015-11-15
 * @author: fan
 */
public class PartyMeetingDaoImpl implements IPartyMeetingDao {
    @Override
    public int insert(OrganizationMeeting organizationMeeting) throws DMException {
        return 0;
    }

    @Override
    public void delete(OrganizationMeeting organizationMeeting) throws DMException {

    }

    @Override
    public void update(OrganizationMeeting organizationMeeting) throws DMException {

    }

    @Override
    public OrganizationMeeting findOne(int id) throws DMException {
        return null;
    }

    @Override
    public List<OrganizationMeeting> getMeetings(MeetingCondition condition, Page page) throws DMException {
        return null;
    }
}
