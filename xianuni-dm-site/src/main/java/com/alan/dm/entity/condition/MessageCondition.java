package com.alan.dm.entity.condition;

/**
 * 通知查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class MessageCondition extends BaseCondition{
    private Boolean toSub;
    private Integer orgId;
    private Boolean personQuery;

    public Boolean getPersonQuery() {
        return personQuery;
    }

    public void setPersonQuery(Boolean personQuery) {
        this.personQuery = personQuery;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Boolean getToSub() {
        return toSub;
    }

    public void setToSub(Boolean toSub) {
        this.toSub = toSub;
    }
}
