package com.alan.dm.entity.condition;


import java.util.Date;


/**
 * 党费管理查询
 * @Date: 2015-11-14
 * @author: fan
 */
public class PartyDuesCondition {
    private Date startDate; // 开始日期
    private Date endDate; // 结束日期
    private Integer orgId;
    private String number;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
