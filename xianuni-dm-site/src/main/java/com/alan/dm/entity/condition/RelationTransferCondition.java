package com.alan.dm.entity.condition;

import java.util.List;

/**
 * 组织关系转入转出
 * @Date: 2015-11-14
 * @author: fan
 */
public class RelationTransferCondition {
    private String number;
    private List<Integer> types;
    private List<Integer> fromOrgId;
    private List<Integer> toOrgId;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public List<Integer> getFromOrgId() {
        return fromOrgId;
    }

    public void setFromOrgId(List<Integer> fromOrgId) {
        this.fromOrgId = fromOrgId;
    }

    public List<Integer> getToOrgId() {
        return toOrgId;
    }

    public void setToOrgId(List<Integer> toOrgId) {
        this.toOrgId = toOrgId;
    }
}
