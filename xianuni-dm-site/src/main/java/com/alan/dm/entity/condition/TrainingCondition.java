package com.alan.dm.entity.condition;


/**
 * 教育培训查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class TrainingCondition {
    private int type; // 培训类型 1-党员培训 2-学习记录 3-专题教育
    private int trainingType; // 培训类别
    private int[] organizationIds; // 组织名称ID数组
    private String organization; // 组织名称

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(int trainingType) {
        this.trainingType = trainingType;
    }

    public int[] getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(int[] organizationIds) {
        this.organizationIds = organizationIds;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }


}
