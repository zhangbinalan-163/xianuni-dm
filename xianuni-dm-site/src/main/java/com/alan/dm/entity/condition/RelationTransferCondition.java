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
    private Integer fromOrgId;
    private Integer toOrgId;

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

    public Integer getFromOrgId() {
        return fromOrgId;
    }

    public void setFromOrgId(Integer fromOrgId) {
        this.fromOrgId = fromOrgId;
    }

    public Integer getToOrgId() {
        return toOrgId;
    }

    public void setToOrgId(Integer toOrgId) {
        this.toOrgId = toOrgId;
    }
}
