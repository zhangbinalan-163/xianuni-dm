package com.alan.dm.entity;

/**
 * 党员的状态
 * Created by zhangbinalan on 15/11/16.
 */
public enum PersonStatus {
    NO(0,"基础人员"),APPLIER(1,"申请人"),
    ACTIVISTS(2,"积极分子"),INTENTION(3,"发展对象"),
    PERPARE(4,"预备党员"),NORMAL(5,"正式党员"),
    HISTORY_OUT(6,"历史党员(关系迁出)"),HISTORY_DEATH(7,"历史党员(死亡)"),HISTORY_PUNISH(8,"历史党员(处分)");

    private int id;
    private String name;

    PersonStatus(int id, String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PersonStatus getInstance(int id){
        if(id==0){
            return NO;
        }
        if(id==1){
            return APPLIER;
        }
        if(id==2){
            return ACTIVISTS;
        }
        if(id==3){
            return INTENTION;
        }
        if(id==4){
            return PERPARE;
        }
        if(id==5){
            return NORMAL;
        }
        if(id==6){
            return HISTORY_OUT;
        }
        if(id==7){
            return HISTORY_DEATH;
        }
        if(id==8){
            return HISTORY_PUNISH;
        }
        return null;
    }
}
