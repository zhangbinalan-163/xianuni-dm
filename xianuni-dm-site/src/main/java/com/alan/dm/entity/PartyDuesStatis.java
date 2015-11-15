package com.alan.dm.entity;

import javafx.geometry.Orientation;

/**
 * 党费统计信息
 * @Date: 2015-11-14
 * @author: fan
 */
public class PartyDuesStatis {
    private int organizationId; // 组织关系ID
    private Orientation organization; // 党支部
    private int partyMembersNum; // 党员人数
    private int payNum; // 缴费人数

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public Orientation getOrganization() {
        return organization;
    }

    public void setOrganization(Orientation organization) {
        this.organization = organization;
    }

    public int getPartyMembersNum() {
        return partyMembersNum;
    }

    public void setPartyMembersNum(int partyMembersNum) {
        this.partyMembersNum = partyMembersNum;
    }

    public int getPayNum() {
        return payNum;
    }

    public void setPayNum(int payNum) {
        this.payNum = payNum;
    }
}

