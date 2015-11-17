package com.alan.dm.entity;

import java.util.Date;

/**
 * 党费缴纳信息
 * @Date: 2015-11-14
 * @author: fan
 */
public class PartyDuesPay {
    private int id;
    private int personId; // 党员ID
    private Date payStartTime; // 缴费起始日期
    private Date payEndTime; // 缴费截止日期
    private Date payDate; // 缴费日期

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

    public Date getPayStartTime() {
        return payStartTime;
    }

    public void setPayStartTime(Date payStartTime) {
        this.payStartTime = payStartTime;
    }

    public Date getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(Date payEndTime) {
        this.payEndTime = payEndTime;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}
