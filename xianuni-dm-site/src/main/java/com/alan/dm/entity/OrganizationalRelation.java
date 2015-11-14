package com.alan.dm.entity;

import java.util.Date;

/**
 * 组织关系转入转出
 * @Date: 2015-11-14
 * @author: fan
 */
public class OrganizationalRelation {
    private int id;
    private PartyMemberInfo member; // 党员
    private String rollInType; // 转入类型
    private String rollOutType; // 转出类型
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

    public PartyMemberInfo getMember() {
        return member;
    }

    public void setMember(PartyMemberInfo member) {
        this.member = member;
    }

    public String getRollInType() {
        return rollInType;
    }

    public void setRollInType(String rollInType) {
        this.rollInType = rollInType;
    }

    public String getRollOutType() {
        return rollOutType;
    }

    public void setRollOutType(String rollOutType) {
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
