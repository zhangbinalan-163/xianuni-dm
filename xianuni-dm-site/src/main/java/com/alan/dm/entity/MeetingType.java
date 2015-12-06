package com.alan.dm.entity;

/**
 * 基础人员类型
 * Created by zhangbinalan on 15/11/16.
 */
public enum MeetingType {
    DZLX(1,"党政联席会议"),MZPY(2,"民主评议"),MZSHH(3,"民主生活会"),SHYK(4,"三会一课");

    private int id;
    private String name;

    MeetingType(int id, String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static MeetingType getInstance(int id){

        if(id==1){
            return DZLX;
        }
        if(id==2){
            return MZPY;
        }
        if(id==3){
            return MZSHH;
        }
        if(id==4){
            return SHYK;
        }
        return null;
    }
}
