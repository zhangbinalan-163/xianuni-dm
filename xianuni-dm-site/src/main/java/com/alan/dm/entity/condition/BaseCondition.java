package com.alan.dm.entity.condition;

import java.util.List;

/**
 * 公用的信息查询条件
 * Created by zhangbinalan on 15/11/24.
 */
public class BaseCondition {
    private List<Integer> orgList;//部门的列表
    private List<Integer> status;//部门的列表

    public List<Integer> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<Integer> orgList) {
        this.orgList = orgList;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }
}
