package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrganizationMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MeetingCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface PartyMeetingMapper {
    int insert(OrganizationMeeting organizationMeeting);
    void delete(OrganizationMeeting organizationMeeting);

    void update(OrganizationMeeting organizationMeeting);

    OrganizationMeeting findOne(int id);

    List<OrganizationMeeting> getMeetings(@Param(value = "con") MeetingCondition condition,
                                          @Param(value = "page") Page page);
}
