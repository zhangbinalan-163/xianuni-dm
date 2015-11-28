package com.alan.dm.entity.condition;

import java.util.List;

/**
 * Created by apple on 15/11/27.
 */
public class BaseCondition {
    private List<Integer> orgList;//部门的列表
    private List<Integer> status;

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public List<Integer> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<Integer> orgList) {
        this.orgList = orgList;
    }
}
