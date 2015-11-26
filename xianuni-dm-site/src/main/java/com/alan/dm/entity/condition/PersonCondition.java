package com.alan.dm.entity.condition;


import java.util.List;

/**
 * 个人信息查询条件
 */
public class PersonCondition extends BaseCondition{
    private String name;
    private String number;
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

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }
}
