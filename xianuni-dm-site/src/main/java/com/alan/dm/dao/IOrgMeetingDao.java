package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrgMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgMeetingCondition;

import java.util.List;

/**
 * 组织活动管理DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IOrgMeetingDao {
    int insert(OrgMeeting orgMeeting) throws DMException;

    void delete(OrgMeeting orgMeeting) throws DMException;

    void update(OrgMeeting orgMeeting) throws DMException;

    OrgMeeting findOne(int id) throws DMException;

    List<OrgMeeting> getByCondition(OrgMeetingCondition condition, Page page) throws DMException;

    int countByCondition(OrgMeetingCondition condition) throws DMException;
}
