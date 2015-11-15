package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrganizationMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MeetingCondition;

import java.util.List;

/**
 * 组织活动
 * @Date: 2015-11-14
 * @author: fan
 */
public interface IMeetingService {

    /**
     * 新增组织会议
     * @param organizationMeeting
     * @return
     * @throws DMException
     */
    int addMeeting(OrganizationMeeting organizationMeeting) throws DMException;

    /**
     * 删除组织会议
     * @param id
     * @throws DMException
     */
    void delete(int id) throws DMException;

    /**
     * 更新组织会议
     * @param organizationMeeting
     * @throws DMException
     */
    void update(OrganizationMeeting organizationMeeting) throws DMException;

    /**
     * 根据查询条件获取组织会议列表
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<OrganizationMeeting> getMeeting(MeetingCondition condition, Page page) throws DMException;

    /**
     * 根据会议ID获取组织会议
     * @param id
     * @return
     * @throws DMException
     */
    OrganizationMeeting getMeeting(int id) throws DMException;
}
