package com.alan.dm.entity.condition;

/**
 * 党员查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class MemberCondition {
    private int organizationId; // 所属支部
    private String name; // 姓名
    private String idCardNo; // 身份证号
    private String memberId; // 学工号
    private int memberType; // 人员类型
    private String education; // 学历
    private int sex; // 性别 1-男 2-女
    private int stats; // 党员状态 1-历史党员 ...

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getMemberType() {
        return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }
}
