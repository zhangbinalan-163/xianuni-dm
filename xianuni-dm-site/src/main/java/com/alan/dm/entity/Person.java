package com.alan.dm.entity;

import java.util.Date;

/**
 * 基本信息实体
 * Created by zhangbinalan on 15/11/16.
 */
public class Person {
    public static int SOURCE_DEFAULT=0;//校内添加
    public static int SOURCE_OUT=1;//校外转入的党员

    private int id;
    private String name;
    private int status;
    private int type;//0-学生 1-教工
    private String number;//学工号
    private String idNumber;//身份证号
    private int sex;//0-男 1-女
    private int nation;//民族
    private int degree;//学历
    private String hometown;
    private String password;
    private Date birth;
    private int profession;//职称
    private Date createTime;
    private Date updateTime;
    private String personDesc;
    private int source;

    private Orgnization orgnization;//党组织
    private int orgId;//
    private ApplierInfo applierInfo;//申请人信息
    private int applierInfoId;
    private ActivitistInfo activitistInfo;//积极分子信息
    private int activitistInfoId;
    private IntentionInfo intentionInfo;//发展对象信息
    private int intentionInfoId;
    private PrepareInfo prepareInfo;//预备党员相关信息
    private int prepareInfoId;
    private NormalInfo normalInfo;//正式党员所需要的信息
    private int normalInfoId;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getPersonDesc() {
        return personDesc;
    }

    public void setPersonDesc(String personDesc) {
        this.personDesc = personDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getNation() {
        return nation;
    }

    public void setNation(int nation) {
        this.nation = nation;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Orgnization getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(Orgnization orgnization) {
        this.orgnization = orgnization;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public ApplierInfo getApplierInfo() {
        return applierInfo;
    }

    public void setApplierInfo(ApplierInfo applierInfo) {
        this.applierInfo = applierInfo;
    }

    public int getApplierInfoId() {
        return applierInfoId;
    }

    public void setApplierInfoId(int applierInfoId) {
        this.applierInfoId = applierInfoId;
    }

    public ActivitistInfo getActivitistInfo() {
        return activitistInfo;
    }

    public void setActivitistInfo(ActivitistInfo activitistInfo) {
        this.activitistInfo = activitistInfo;
    }

    public int getActivitistInfoId() {
        return activitistInfoId;
    }

    public void setActivitistInfoId(int activitistInfoId) {
        this.activitistInfoId = activitistInfoId;
    }

    public IntentionInfo getIntentionInfo() {
        return intentionInfo;
    }

    public void setIntentionInfo(IntentionInfo intentionInfo) {
        this.intentionInfo = intentionInfo;
    }

    public int getIntentionInfoId() {
        return intentionInfoId;
    }

    public void setIntentionInfoId(int intentionInfoId) {
        this.intentionInfoId = intentionInfoId;
    }

    public PrepareInfo getPrepareInfo() {
        return prepareInfo;
    }

    public void setPrepareInfo(PrepareInfo prepareInfo) {
        this.prepareInfo = prepareInfo;
    }

    public int getPrepareInfoId() {
        return prepareInfoId;
    }

    public void setPrepareInfoId(int prepareInfoId) {
        this.prepareInfoId = prepareInfoId;
    }

    public NormalInfo getNormalInfo() {
        return normalInfo;
    }

    public void setNormalInfo(NormalInfo normalInfo) {
        this.normalInfo = normalInfo;
    }

    public int getNormalInfoId() {
        return normalInfoId;
    }

    public void setNormalInfoId(int normalInfoId) {
        this.normalInfoId = normalInfoId;
    }
}
