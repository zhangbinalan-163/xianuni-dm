package com.alan.dm.entity.condition;


/**
 * 会议查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class OrgMeetingCondition {
    private int organizationId; // 党支部ID
    private int activityType; // 组织活动类型 1-党政联席会议 2-民主评议 3-民主生活会 4-三会一课
    private String theme; // 主题

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}
