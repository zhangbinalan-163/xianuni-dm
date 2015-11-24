package com.alan.dm.entity.condition;

/**
 * 管理员的查询条件
 */
public class AdminCondition extends BaseCondition {
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
