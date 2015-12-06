package com.alan.dm.dao.mapper;

import com.alan.dm.entity.Page;
import com.alan.dm.entity.PersonMeeting;
import com.alan.dm.entity.query.PersonMeetingCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织活动会议Mapper
 * @Date: 2015-11-16
 * @author: fan
 */
public interface PersonMeetingMapper {
    /**
     *
     * @param personMeeting
     * @return
     */
    void insert(@Param(value = "personMeeting") PersonMeeting personMeeting);

    /**
     *
     * @param personMeeting
     */
    void delete(@Param(value = "personMeeting") PersonMeeting personMeeting);

    /**
     *
     * @param personMeeting
     */
    void update(@Param(value = "personMeeting") PersonMeeting personMeeting);

    /**
     *
     * @param id
     * @return
     */
    PersonMeeting getById(@Param(value = "id") int id);

    /**
     *
     * @param condition
     * @param page
     * @return
     */
    List<PersonMeeting> getByCondition(@Param(value = "condition") PersonMeetingCondition condition,
                                    @Param(value = "page") Page page);

    /**
     *
     * @param condition
     * @return
     */
    int countByCondition(@Param(value = "condition") PersonMeetingCondition condition);
}
