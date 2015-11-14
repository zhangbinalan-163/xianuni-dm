package com.alan.dm.entity;

import java.util.Date;

/**
 * 党员基本信息
 * @Date: 2015-11-14
 * @author: fan
 */
public class PartyMemberInfo {
    private int id;
    private Orgnization orgnization; // 党员所属支部
    private String type; // 党员类型
    private String memberId; // 学工号
    private String name; // 姓名
    private String idCardNo; // 身份证号
    private int sex; // 性别 1-男 2-女
    private String nation; // 民族
    private String education; // 学历
    private String degree; // 学位
    private String nativePlace; // 籍贯
    private Date birthday; // 出生日期
    private int stats; // 党员状态 // todo

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Orgnization getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(Orgnization orgnization) {
        this.orgnization = orgnization;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
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
}
