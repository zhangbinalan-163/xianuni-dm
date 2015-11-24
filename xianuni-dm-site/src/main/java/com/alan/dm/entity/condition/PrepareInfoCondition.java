package com.alan.dm.entity.condition;

/**
 * 预备党员信息查询条件
 */
public class PrepareInfoCondition extends BaseCondition{
    private String number;
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
}
