package com.alan.dm.entity;

import java.util.Date;
import java.util.List;

/**
 * 组织活动会议信息
 * @Date: 2015-11-14
 * @author: fan
 */
public class OrgMeeting {
    public static int DZLX=1;
    public static int MZPY=2;
    public static int MZSHH=3;
    public static int ZBDH=4;
    public static int DZBWYH=5;
    public static int DXZH=6;
    public static int DK=7;

    private int id;
    private int meetingType; // 组织活动类型 1-党政联席会议 2-民主评议 3-民主生活会 4-支部大会  5-支部委员会 6-当小组会 7-党课
    private int orgId;
    private Orgnization orgnization; // 组织关系
    private Date startTime; // 会议开始时间
    private String location; // 会议地点
    private String theme; // 会议主题
    private String compere; // 主持人
    private String recorder; // 主持人
    private int shouldNumberOfPeople; // 应到人数
    private int realNumberOfPeople; // 实到人数
    private String content; // 主要内容
    private String attendancePeople; // 出勤人员
    private String absencePeople; // 缺勤人员
    private Date createTime; //
    private Date updateTime; //

    private List<Person> invitePersons;//会议邀请人
    private boolean sendMail;//是否发通知信
    private List<Resource> resourceList;//

    public List<Person> getInvitePersons() {
        return invitePersons;
    }

    public void setInvitePersons(List<Person> invitePersons) {
        this.invitePersons = invitePersons;
    }

    public boolean isSendMail() {
        return sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(int meetingType) {
        this.meetingType = meetingType;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public Orgnization getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(Orgnization orgnization) {
        this.orgnization = orgnization;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }
}
