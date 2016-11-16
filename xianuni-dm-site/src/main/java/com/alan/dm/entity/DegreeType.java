package com.alan.dm.entity;

/**
 * 基础人员类型
 * Created by zhangbinalan on 15/11/16.
 */
public enum DegreeType {
    CHUZHONG(0,"初中及以下"),GAOZHONG(1,"高中"),ZHUANKE(2,"专科"),BENKE(3,"本科"),YANJIUSHENG(4,"研究生"),BOSHI(5,"博士"),QITA(6,"其他");

    private int id;
    private String name;

    DegreeType(int id, String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static DegreeType getInstance(int id){
        if(id==0){
            return CHUZHONG;
        }
        if(id==1){
            return GAOZHONG;
        }
        if(id==2){
            return ZHUANKE;
        }
        if(id==3){
            return BENKE;
        }
        if(id==4){
            return YANJIUSHENG;
        }
        if(id==5){
            return BOSHI;
        }
        if(id==6){
            return QITA;
        }
        return null;
    }
    public static DegreeType getInstanceByName(String name){
        if(CHUZHONG.getName().equals(name)){
            return CHUZHONG;
        }
        if(GAOZHONG.getName().equals(name)){
            return GAOZHONG;
        }
        if(ZHUANKE.getName().equals(name)){
            return ZHUANKE;
        }
        if(BENKE.getName().equals(name)){
            return BENKE;
        }
        if(YANJIUSHENG.getName().equals(name)){
            return YANJIUSHENG;
        }
        if(BOSHI.getName().equals(name)){
            return BOSHI;
        }
        if(QITA.getName().equals(name)){
            return QITA;
        }
        return QITA;
    }
}
