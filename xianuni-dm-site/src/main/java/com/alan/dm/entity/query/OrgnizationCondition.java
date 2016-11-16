package com.alan.dm.entity.query;


import java.util.List;

/**
 * 查询条件
 * Created by zhangbinalan on 15/11/26.
 */
public class OrgnizationCondition {
    private String name;
    private List<Integer> status;

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
