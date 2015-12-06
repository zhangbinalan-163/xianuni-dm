package com.alan.dm.entity;

import java.util.Date;

/**
 * Created by zhangbinalan on 15/12/2.
 */
public class PersonMeeting {
    private int id;
    private int personId;
    private int meetingId;
    private boolean takeIn;//是否参加
    private boolean assure;

    public boolean isAssure() {
        return assure;
    }

    public void setAssure(boolean assure) {
        this.assure = assure;
    }

    private String harvest;//收获总结
    private Date harvestTime;
    private Person person;
    private OrgMeeting orgMeeting;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public boolean isTakeIn() {
        return takeIn;
    }

    public void setTakeIn(boolean takeIn) {
        this.takeIn = takeIn;
    }

    public String getHarvest() {
        return harvest;
    }

    public void setHarvest(String harvest) {
        this.harvest = harvest;
    }

    public Date getHarvestTime() {
        return harvestTime;
    }

    public void setHarvestTime(Date harvestTime) {
        this.harvestTime = harvestTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public OrgMeeting getOrgMeeting() {
        return orgMeeting;
    }

    public void setOrgMeeting(OrgMeeting orgMeeting) {
        this.orgMeeting = orgMeeting;
    }
}
