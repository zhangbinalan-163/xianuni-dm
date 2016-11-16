package com.alan.dm.entity;

import java.util.Date;
import java.util.List;

/**
 * 教育培训
 * @Date: 2015-11-14
 * @author: fan
 */
public class EduTraining {
    public static final int FORMAL_TYPE=0;
    public static final int OBJ_TYPE=1;

    private int id;
    private String title; // 培训标题
    private Orgnization organization; // 组织机构
    private Integer orgId;
    private Date startTime; // 开始时间
    private Date endTime; // 结束时间
    private String content; // 培训内容
    private Date createTime; //
    private int trainType;//培训类型
    private List<Resource> resourceList;//

    public int getTrainType() {
        return trainType;
    }

    public void setTrainType(int trainType) {
        this.trainType = trainType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Orgnization getOrganization() {
        return organization;
    }

    public void setOrganization(Orgnization organization) {
        this.organization = organization;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }
}
