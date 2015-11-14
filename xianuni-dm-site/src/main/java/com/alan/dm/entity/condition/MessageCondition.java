package com.alan.dm.entity.condition;

import com.alan.dm.entity.DateRange;

/**
 * 通知查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class MessageCondition {
    private DateRange range; // 时间范围
    private String title; // 标题
    private int organizationId; // 党支部ID

    public DateRange getRange() {
        return range;
    }

    public void setRange(DateRange range) {
        this.range = range;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}
