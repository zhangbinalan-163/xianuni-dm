package com.alan.dm.entity.condition;


import com.alan.dm.entity.query.PersonCondition;

/**
 * 教育培训查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class EduTrainingCondition extends PersonCondition{
    private Integer type;//培训类别

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
