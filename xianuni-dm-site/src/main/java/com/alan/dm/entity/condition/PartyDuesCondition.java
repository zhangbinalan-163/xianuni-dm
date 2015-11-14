package com.alan.dm.entity.condition;

import java.util.Date;

/**
 * 党费管理查询
 * @Date: 2015-11-14
 * @author: fan
 */
public class PartyDuesCondition {
    private Date payDate; // 缴费日期
    private String name; // 党员姓名

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
