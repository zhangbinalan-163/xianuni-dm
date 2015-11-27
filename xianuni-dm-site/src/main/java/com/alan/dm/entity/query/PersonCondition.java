package com.alan.dm.entity.query;

import java.util.List;

/**
 * 人员的查询条件
 * Created by zhangbinalan on 15/11/26.
 */
public class PersonCondition {
    private String name;//姓名
    private String number;//学工号
    private List<Integer> orgList;//部门的列表
    private List<Integer> status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }
}
