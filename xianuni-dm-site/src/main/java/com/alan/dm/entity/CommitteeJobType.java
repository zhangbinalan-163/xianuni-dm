package com.alan.dm.entity;

/**
 * 基础人员类型
 * Created by zhangbinalan on 15/11/16.
 */
public enum CommitteeJobType {

    NORMAL(0,"委员"),ZUZHI(1,"宣传委员"),XUANCHUAN(2,"宣传委员"),OTHER(3,"其他职务"),LEAD(4,"书记");

    private int id;
    private String name;

    CommitteeJobType(int id, String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static CommitteeJobType getInstance(int id){
        if(id==0){
            return NORMAL;
        }
        if(id==1){
            return ZUZHI;
        }
        if(id==2){
            return XUANCHUAN;
        }
        if(id==3){
            return OTHER;
        }
        return null;
    }
}
