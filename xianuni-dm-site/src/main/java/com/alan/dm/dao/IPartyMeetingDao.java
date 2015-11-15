package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrganizationMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MeetingCondition;

import java.util.List;

/**
 * 组织活动管理DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IPartyMeetingDao {
    int insert(OrganizationMeeting organizationMeeting) throws DMException;

    void delete(OrganizationMeeting organizationMeeting) throws DMException;

    void update(OrganizationMeeting organizationMeeting) throws DMException;

    OrganizationMeeting findOne(int id) throws DMException;

    List<OrganizationMeeting> getMeetings(MeetingCondition condition, Page page) throws DMException;
}
