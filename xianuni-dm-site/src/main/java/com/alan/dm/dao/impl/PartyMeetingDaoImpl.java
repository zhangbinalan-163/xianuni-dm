package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IPartyMeetingDao;
import com.alan.dm.entity.OrganizationMeeting;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MeetingCondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织活动DAO层实现
 * @Date: 2015-11-15
 * @author: fan
 */
public class PartyMeetingDaoImpl extends BaseDao implements IPartyMeetingDao {
    private static final String TABLE_NAME = "organization_meeting";

    @Override
    public int insert(OrganizationMeeting organizationMeeting) throws DMException {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (activity, meetingType, startTime, endTime, location, theme, compere," +
                " shouldNumberOfPeople, realNumberOfPeople, content, attendancePeople," +
                " absencePeople, filePath, createTime) " +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return update(sql, organizationMeeting.getActivity(), organizationMeeting.getMeetingType(), organizationMeeting.getStartTime(),
                organizationMeeting.getEndTime(), organizationMeeting.getLocation(), organizationMeeting.getTheme(),
                organizationMeeting.getCompere(), organizationMeeting.getShouldNumberOfPeople(), organizationMeeting.getRealNumberOfPeople(),
                organizationMeeting.getContent(), organizationMeeting.getAttendancePeople(), organizationMeeting.getAbsencePeople(),
                organizationMeeting.getFilePath(), new Date());
    }

    @Override
    public void delete(OrganizationMeeting organizationMeeting) throws DMException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        update(sql, organizationMeeting.getId());
    }

    @Override
    public void update(OrganizationMeeting organizationMeeting) throws DMException {
        String sql = "UPDATE " + TABLE_NAME + " SET activity=?, meetingType=?, startTime=?, endTime=?," +
                " location=?, theme=?, compere=?, shouldNumberOfPeople, realNumberOfPeople=?, content=?," +
                " attendancePeople=?, absencePeople=?, filePath=?, updateTime=? " +
                "WHERE id=?";
        update(sql, organizationMeeting.getActivity(), organizationMeeting.getMeetingType(), organizationMeeting.getStartTime(),
                organizationMeeting.getEndTime(), organizationMeeting.getLocation(), organizationMeeting.getTheme(),
                organizationMeeting.getCompere(), organizationMeeting.getShouldNumberOfPeople(), organizationMeeting.getRealNumberOfPeople(),
                organizationMeeting.getContent(), organizationMeeting.getAttendancePeople(), organizationMeeting.getAbsencePeople(),
                organizationMeeting.getFilePath(), new Date(), organizationMeeting.getId());
    }

    @Override
    public OrganizationMeeting findOne(int id) throws DMException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        return getBean(sql, OrganizationMeeting.class, id);
    }

    @Override
    public List<OrganizationMeeting> getMeetings(MeetingCondition condition, Page page) throws DMException {
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + "WHERE ");
        if(condition.getOrganizationId() != 0) {
            sql.append("organizationId=? ");
            objects.add(condition.getOrganizationId());
        }
        if(condition.getActivityType() != 0) {
            sql.append("AND activity=? ");
            objects.add(condition.getActivityType());
        }
        if(!StringUtils.isEmpty(condition.getTheme())) {
            sql.append("AND theme=? ");
            objects.add(condition.getTheme());
        }
        if(condition.getRange() != null) {
            sql.append("AND startTime BETWEEN ? AND ?");
            objects.add(condition.getRange().getStartDate());
            objects.add(condition.getRange().getEndDate());
        }
        return getBeanList(sql.toString(), OrganizationMeeting.class, objects.toArray());
    }
}
