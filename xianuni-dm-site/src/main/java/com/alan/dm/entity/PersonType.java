package com.alan.dm.entity;

/**
 * 基础人员类型
 * Created by zhangbinalan on 15/11/16.
 */
public enum PersonType {

    STUDENT(0,"学生"),TEACHER(1,"教工"),OTHER(2,"其他");

    private int id;
    private String name;

    PersonType(int id, String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PersonType getInstance(int id){
        if(id==0){
            return STUDENT;
        }
        if(id==1){
            return TEACHER;
        }
        if(id==2){
            return OTHER;
        }
        return null;
    }
}
