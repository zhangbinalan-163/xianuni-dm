package com.alan.dm.entity;

/**
 * 党组织奖惩类型
 * Created by zhangbinalan on 15/11/16.
 */
public enum RelationTransferType {

    INNER(0,"校内转"),OUT_OTHER(2,"校外转出"),IN_OTHER(1,"校外转入");

    private int id;
    private String name;

    RelationTransferType(int id, String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static RelationTransferType getInstance(int id){
        if(id==0){
            return INNER;
        }
        if(id==1){
            return IN_OTHER;
        }
        if(id==2){
            return OUT_OTHER;
        }
        return null;
    }
}
