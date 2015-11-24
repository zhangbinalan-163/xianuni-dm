package com.alan.dm.entity.condition;

/**
 * 通知查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class MessageCondition extends BaseCondition{
    private Boolean toSub;

    public Boolean getToSub() {
        return toSub;
    }

    public void setToSub(Boolean toSub) {
        this.toSub = toSub;
    }
}
