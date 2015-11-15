package com.alan.dm.entity;

import java.util.Date;

/**
 * 组织关系转入转出
 * @Date: 2015-11-14
 * @author: fan
 */
public class OrganizationalRelation {
    private int id;
    private int organizationId; // 组织关系ID
    private int partyMemberId; // 党员ID
    private PartyMember member; // 党员
    private int rollInType; // 转入类型 1-校内转入 2-校外转入
    private int rollOutType; // 转出类型 1-校内转出 2-校外转出
    private String rollInOrganization; // 转入组织
    private String rollOutOrganization; // 转出组织
    private Date rollDate; // 转入/转出日期
    private Date reviewDate; // 审核日期
    private String operator; // 经办人
    private String tel; // 联系电话

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

    public int getRollInType() {
        return rollInType;
    }

    public void setRollInType(int rollInType) {
        this.rollInType = rollInType;
    }

    public int getRollOutType() {
        return rollOutType;
    }

    public void setRollOutType(int rollOutType) {
        this.rollOutType = rollOutType;
    }

    public String getRollInOrganization() {
        return rollInOrganization;
    }

    public void setRollInOrganization(String rollInOrganization) {
        this.rollInOrganization = rollInOrganization;
    }

    public String getRollOutOrganization() {
        return rollOutOrganization;
    }

    public void setRollOutOrganization(String rollOutOrganization) {
        this.rollOutOrganization = rollOutOrganization;
    }

    public Date getRollDate() {
        return rollDate;
    }

    public void setRollDate(Date rollDate) {
        this.rollDate = rollDate;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
