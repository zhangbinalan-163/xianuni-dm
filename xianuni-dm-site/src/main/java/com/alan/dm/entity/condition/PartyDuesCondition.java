package com.alan.dm.entity.condition;

import com.alan.dm.entity.DateRange;

import java.util.Date;

/**
 * 党费管理查询
 * @Date: 2015-11-14
 * @author: fan
 */
public class PartyDuesCondition {
    private DateRange range; // 缴费日期
    private int[] partyMemberIds; // 党员ID列表
    private String name; // 党员姓名

    public DateRange getRange() {
        return range;
    }

    public void setRange(DateRange range) {
        this.range = range;
    }

    public int[] getPartyMemberIds() {
        return partyMemberIds;
    }

    public void setPartyMemberIds(int[] partyMemberIds) {
        this.partyMemberIds = partyMemberIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
