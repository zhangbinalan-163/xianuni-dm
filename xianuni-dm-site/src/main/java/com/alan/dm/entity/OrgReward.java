package com.alan.dm.entity;

import java.util.Date;

/**
 * 党组织奖惩记录
 * Created by zhangbinalan on 15/11/16.
 */
public class OrgReward {

    private static final int LEVEL_SCHOOLE=0;
    private static final int LEVEL_CITY=1;
    private static final int LEVEL_PROVINCE=2;
    private static final int LEVEL_COUNTRY=3;

    private int id;
    private int orgId;
    private String name;
    private String desc;
    private int type;
    private int level;
    private Date rewardTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(Date rewardTime) {
        this.rewardTime = rewardTime;
    }
}
