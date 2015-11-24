package com.alan.dm.entity.condition;


/**
 * 个人信息查询条件
 */
public class PersonCondition extends BaseCondition{
    private String name;
    private String number;
    private Integer status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
