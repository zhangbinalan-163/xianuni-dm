package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrgMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.Resource;
import com.alan.dm.entity.condition.OrgMeetingCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织活动会议Mapper
 * @Date: 2015-11-16
 * @author: fan
 */
public interface OrgMeetingMapper {
    /**
     *
     * @param meetingId
     * @throws DMException
     */
    void deleteResource(@Param(value = "meetingId") Integer meetingId) throws DMException;
    /**
     *
     * @param resource
     * @throws DMException
     */
    void insertResource(@Param(value = "resource") Resource resource) throws DMException;
    /**
     * 插入一条人员参见与会的记录
     * @param person
     * @param orgMeeting
     */
    void insertPeronMeeting(@Param(value = "person") Person person,@Param(value = "orgMeeting") OrgMeeting orgMeeting);
    /**
     *
     * @param orgMeeting
     * @return
     */
    void insert(@Param(value = "orgMeeting") OrgMeeting orgMeeting);

    /**
     *
     * @param orgMeeting
     */
    void delete(@Param(value = "orgMeeting")OrgMeeting orgMeeting);

    /**
     *
     * @param orgMeeting
     */
    void update(@Param(value = "orgMeeting") OrgMeeting orgMeeting);

    /**
     *
     * @param id
     * @return
     */
    OrgMeeting getById(@Param(value = "id")int id);

    /**
     *
     * @param condition
     * @param page
     * @return
     */
    List<OrgMeeting> getByCondition(@Param(value = "condition") OrgMeetingCondition condition,
                                    @Param(value = "page") Page page);

    /**
     *
     * @param condition
     * @return
     */
    int countByCondition(@Param(value = "condition") OrgMeetingCondition condition);
}
