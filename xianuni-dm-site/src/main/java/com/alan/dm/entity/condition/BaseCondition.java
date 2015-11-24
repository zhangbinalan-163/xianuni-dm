package com.alan.dm.entity.condition;

import java.util.List;

/**
 * 公用的信息查询条件
 * Created by zhangbinalan on 15/11/24.
 */
public class BaseCondition {
    private Integer orgId;
    private Boolean containSub;//是否级联包含所有的子部门
    private List<Integer> orgList;//部门的列表

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Boolean getContainSub() {
        return containSub;
    }

    public void setContainSub(Boolean containSub) {
        this.containSub = containSub;
    }

    public List<Integer> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<Integer> orgList) {
        this.orgList = orgList;
    }
}
