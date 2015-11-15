package com.alan.dm.entity.condition;

import com.alan.dm.entity.DateRange;

/**
 * 教育培训查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class TrainingCondition {
    private String trainingType; // 培训类别
    private String organization; // 组织名称
    private DateRange range; // 时间范围

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public DateRange getRange() {
        return range;
    }

    public void setRange(DateRange range) {
        this.range = range;
    }
}