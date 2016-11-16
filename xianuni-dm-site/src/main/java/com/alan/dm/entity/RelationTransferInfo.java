package com.alan.dm.entity;

import java.util.Date;

/**
 * 组织关系转入转出
 * @Date: 2015-11-14
 * @author: fan
 */
public class RelationTransferInfo {
    public static final int CHECKING=1;//正在审核
    public static final int DONE=0;//已经完成

    private int id;
    private Person person;
    private int status;
    private int transferType;
    private int fromOrgId;
    private int toOrgId;
    private String fromOrgName;
    private String toOrgName;
    private String tel;
    private Date transferTime;
    private int personId;
    private Date transferDoneTime;

    public Date getTransferDoneTime() {
        return transferDoneTime;
    }

    public void setTransferDoneTime(Date transferDoneTime) {
        this.transferDoneTime = transferDoneTime;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public int getFromOrgId() {
        return fromOrgId;
    }

    public void setFromOrgId(int fromOrgId) {
        this.fromOrgId = fromOrgId;
    }

    public int getToOrgId() {
        return toOrgId;
    }

    public void setToOrgId(int toOrgId) {
        this.toOrgId = toOrgId;
    }

    public String getFromOrgName() {
        return fromOrgName;
    }

    public void setFromOrgName(String fromOrgName) {
        this.fromOrgName = fromOrgName;
    }

    public String getToOrgName() {
        return toOrgName;
    }

    public void setToOrgName(String toOrgName) {
        this.toOrgName = toOrgName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }
}
