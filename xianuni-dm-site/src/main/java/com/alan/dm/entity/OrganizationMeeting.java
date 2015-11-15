package com.alan.dm.entity;

import java.util.Date;

/**
 * 会议信息
 * @Date: 2015-11-14
 * @author: fan
 */
public class OrganizationMeeting {
    private int id;
    private int activity; // 组织活动类型 1-党政联席会议 2-民主评议 3-民主生活会 4-三会一课
    private int meetingType; // 会议类型
    private Date startTime; // 会议开始时间
    private Date endTime; // 会议结束时间
    private String location; // 会议地点
    private String theme; // 会议主题
    private String compere; // 主持人
    private int shouldNumberOfPeople; // 应到人数
    private int realNumberOfPeople; // 实到人数
    private String content; // 主要内容
    private String filePath; // 附件地址
    private String attendancePeople; // 出勤人员
    private String absencePeople; // 缺勤人员

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(int meetingType) {
        this.meetingType = meetingType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getCompere() {
        return compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public int getShouldNumberOfPeople() {
        return shouldNumberOfPeople;
    }

    public void setShouldNumberOfPeople(int shouldNumberOfPeople) {
        this.shouldNumberOfPeople = shouldNumberOfPeople;
    }

    public int getRealNumberOfPeople() {
        return realNumberOfPeople;
    }

    public void setRealNumberOfPeople(int realNumberOfPeople) {
        this.realNumberOfPeople = realNumberOfPeople;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAttendancePeople() {
        return attendancePeople;
    }

    public void setAttendancePeople(String attendancePeople) {
        this.attendancePeople = attendancePeople;
    }

    public String getAbsencePeople() {
        return absencePeople;
    }

    public void setAbsencePeople(String absencePeople) {
        this.absencePeople = absencePeople;
    }
}
