package com.alan.dm.entity;

import java.util.Date;

/**
 * 党费缴纳信息
 * @Date: 2015-11-14
 * @author: fan
 */
public class PartyDuesPay {
    private int id;
    private Person person; // 党员ID
    private int personId;
    private Date payStartTime; // 缴费起始日期
    private Date payEndTime; // 缴费截止日期
    private Date payTime; // 缴费日期
    private float fee;

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
