package com.alan.dm.entity.query;

import java.util.List;

/**
 * Created by apple on 15/11/29.
 */
public class MailCondition {
    private Integer adminId;
    private String number;//学工号
    private List<Integer> orgList;//部门的列表

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Integer> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<Integer> orgList) {
        this.orgList = orgList;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}
