package com.alan.dm.entity.condition;


/**
 * 教育培训查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class EduTrainingCondition {
    private int type; // 培训类型 1-党员培训 2-学习记录 3-专题教育
    private int trainingType; // 培训类别
    private int orgId; // 组织名称ID
    private String orgName; // 组织名称

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

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
