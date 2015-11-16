package com.alan.dm.entity;

/**
 * 党组织奖惩类型
 * Created by zhangbinalan on 15/11/16.
 */
public enum OrgRewardLevel {

    SCHOOLE(0,"校级"),CITY(1,"市级"),PROVINCE(2,"省级"),COUNTRY(3,"国家级");

    private int id;
    private String name;

    OrgRewardLevel(int id, String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static OrgRewardLevel getInstance(int id){
        if(id==0){
            return SCHOOLE;
        }
        if(id==1){
            return CITY;
        }
        if(id==2){
            return PROVINCE;
        }
        if(id==3){
            return COUNTRY;
        }
        return null;
    }
}
