package com.alan.dm.dao.mapper;

import com.alan.dm.entity.OrgMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgMeetingCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织活动会议Mapper
 * @Date: 2015-11-16
 * @author: fan
 */
public interface OrgMeetingMapper {
    int insert(@Param(value = "orgMeeting") OrgMeeting orgMeeting);
    void delete(OrgMeeting orgMeeting);

    void update(@Param(value = "orgMeeting") OrgMeeting orgMeeting);

    OrgMeeting findOne(int id);

    List<OrgMeeting> getByCondition(@Param(value = "condition") OrgMeetingCondition condition,
                                    @Param(value = "page") Page page);

    int countByCondition(@Param(value = "condition") OrgMeetingCondition condition);
}
