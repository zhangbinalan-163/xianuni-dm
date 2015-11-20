package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrgMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgMeetingCondition;

import java.util.List;

/**
 * 组织活动
 * @Date: 2015-11-14
 * @author: fan
 */
public interface IOrgMeetingService {

    /**
     * 新增组织会议
     * @param orgMeeting
     * @return
     * @throws DMException
     */
    int addMeeting(OrgMeeting orgMeeting) throws DMException;

    /**
     * 删除组织会议
     * @param orgMeeting
     * @throws DMException
     */
    void delete(OrgMeeting orgMeeting) throws DMException;

    /**
     * 更新组织会议
     * @param orgMeeting
     * @throws DMException
     */
    void update(OrgMeeting orgMeeting) throws DMException;

    /**
     * 根据查询条件获取组织会议列表
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<OrgMeeting> getByCondition(OrgMeetingCondition condition, Page page) throws DMException;

    int countByCondition(OrgMeetingCondition condition) throws DMException;

    /**
     * 根据会议ID获取组织会议
     * @param id
     * @return
     * @throws DMException
     */
    OrgMeeting getMeeting(int id) throws DMException;
}
