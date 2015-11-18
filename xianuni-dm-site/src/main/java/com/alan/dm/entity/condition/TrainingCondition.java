package com.alan.dm.entity.condition;


/**
 * 教育培训查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class TrainingCondition {
    private int trainingType; // 培训类别
    private int[] organizationIds; // 组织名称ID数组
    private String organization; // 组织名称

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
