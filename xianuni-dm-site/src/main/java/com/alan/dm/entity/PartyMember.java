package com.alan.dm.entity;

import java.util.Date;

/**
 * 党员基本信息
 * @Date: 2015-11-14
 * @author: fan
 */
public class PartyMember {
    private int id;
    private int organizationId; // 组织关系ID
    private Orgnization orgnization; // 党员所属支部
    private int type; // 党员类型 1-教工 2-学生
    private String memberId; // 学工号
    private String name; // 姓名
    private String idCardNo; // 身份证号
    private int sex; // 性别 1-男 2-女
    private String nation; // 民族
    private int education; // 学历
    private String degree; // 学位
    private String nativePlace; // 籍贯
    private Date birthday; // 出生日期
    private int stats; // 党员状态 // todo
    private int origin; // 党员来源 1-校内 2-校外
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

    public PartyMember(int organizationId, Orgnization orgnization, int type,
                       String memberId, String name, String idCardNo, int sex) {
        this.organizationId = organizationId;
        this.orgnization = orgnization;
        this.type = type;
        this.memberId = memberId;
        this.name = name;
        this.idCardNo = idCardNo;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public Orgnization getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(Orgnization orgnization) {
        this.orgnization = orgnization;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
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
}
