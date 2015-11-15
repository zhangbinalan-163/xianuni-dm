package com.alan.dm.entity;

import java.util.Date;

/**
 * 党费缴纳信息
 * @Date: 2015-11-14
 * @author: fan
 */
public class PartyDuesPay {
    private int id;
    private int organizationId; // 组织关系ID
    private int partyMemberId; // 党员ID
    private PartyMember member; // 党员
    private Date payStartDate; // 缴费起始日期
    private Date payEndDate; // 缴费截止日期
    private Date payDate; // 缴费日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getPartyMemberId() {
        return partyMemberId;
    }

    public void setPartyMemberId(int partyMemberId) {
        this.partyMemberId = partyMemberId;
    }

    public PartyMember getMember() {
        return member;
    }

    public void setMember(PartyMember member) {
        this.member = member;
    }

    public Date getPayStartDate() {
        return payStartDate;
    }

    public void setPayStartDate(Date payStartDate) {
        this.payStartDate = payStartDate;
    }

    public Date getPayEndDate() {
        return payEndDate;
    }

    public void setPayEndDate(Date payEndDate) {
        this.payEndDate = payEndDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}
