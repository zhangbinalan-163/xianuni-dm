package com.alan.dm.entity;

/**
 * 党组织奖惩类型
 * Created by zhangbinalan on 15/11/16.
 */
public enum OrgRewardType {

    REWARD(0,"奖励"),PUNISHMENT(1,"惩处");

    private int id;
    private String name;

    OrgRewardType(int id,String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static OrgRewardType getInstance(int id){
        if(id==0){
            return REWARD;
        }
        if(id==1){
            return PUNISHMENT;
        }
        return null;
    }
}
