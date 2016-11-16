package com.alan.dm.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by apple on 15/12/9.
 */
public enum NationType {
    HANZU(0,"汉族"),MENGGUZU(1,"蒙古族"),HUIZU(2,"回族")
    ,ZANGZU(3,"藏族"),WEIWUER(4,"维吾尔族"),MIAOZU(5,"苗族"),YIZU(6,"彝族")
    ,ZHUANGZU(7,"壮族"),BUYIZU(8,"布依族"),CHAOXIANZU(9,"朝鲜族"),MANZU(10,"满族")
    ,TUJIAZU(11,"土家族"),HANSAKE(12,"哈萨克族"),DAIZU(13,"傣族"),QITA(14,"其他");

    private int id;
    private String name;

    NationType(int id,String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<Integer> allType(){
        return Arrays.asList(
                NationType.HANZU.getId(),
                NationType.BUYIZU.getId(),
                NationType.CHAOXIANZU.getId(),NationType.DAIZU.getId(),NationType.HANSAKE.getId(),
                NationType.HUIZU.getId(),NationType.MANZU.getId(),NationType.MENGGUZU.getId(),
                NationType.MIAOZU.getId(),NationType.TUJIAZU.getId(),NationType.QITA.getId(),NationType.WEIWUER.getId(),
                NationType.YIZU.getId(),NationType.ZHUANGZU.getId(),NationType.ZANGZU.getId()
        );
    }
    public static NationType getInstance(int id){
        if(id==HANZU.getId()){
            return HANZU;
        }
        if(id==MENGGUZU.getId()){
            return MENGGUZU;
        }
        if(id==HUIZU.getId()){
            return HUIZU;
        }
        if(id==ZANGZU.getId()){
            return ZANGZU;
        }
        if(id==WEIWUER.getId()){
            return WEIWUER;
        }
        if(id==MIAOZU.getId()){
            return MIAOZU;
        }if(id==YIZU.getId()){
            return YIZU;
        }
        if(id==ZHUANGZU.getId()){
            return ZHUANGZU;
        }
        if(id==BUYIZU.getId()){
            return BUYIZU;
        }
        if(id==CHAOXIANZU.getId()){
            return CHAOXIANZU;
        }
        if(id==MANZU.getId()){
            return MANZU;
        }
        if(id==TUJIAZU.getId()){
            return TUJIAZU;
        }
        if(id==HANSAKE.getId()){
            return HANSAKE;
        }
        if(id==DAIZU.getId()){
            return DAIZU;
        }
        if(id==QITA.getId()){
            return QITA;
        }
        return QITA;
    }
    public static NationType getInstanceByName(String name){
        if(HANZU.getName().equals(name)){
            return HANZU;
        }
        if(MENGGUZU.getName().equals(name)){
            return MENGGUZU;
        }
        if(HUIZU.getName().equals(name)){
            return HUIZU;
        }
        if(ZANGZU.getName().equals(name)){
            return ZANGZU;
        }
        if(WEIWUER.getName().equals(name)){
            return WEIWUER;
        }
        if(MIAOZU.getName().equals(name)){
            return MIAOZU;
        }
        if(YIZU.getName().equals(name)){
            return YIZU;
        }
        if(ZHUANGZU.getName().equals(name)){
            return ZHUANGZU;
        }
        if(BUYIZU.getName().equals(name)){
            return BUYIZU;
        }
        if(CHAOXIANZU.getName().equals(name)){
            return CHAOXIANZU;
        }
        if(MANZU.getName().equals(name)){
            return MANZU;
        }
        if(TUJIAZU.getName().equals(name)){
            return TUJIAZU;
        }
        if(HANSAKE.getName().equals(name)){
            return HANSAKE;
        }
        if(DAIZU.getName().equals(name)){
            return DAIZU;
        }
        if(QITA.getName().equals(name)){
            return QITA;
        }
        return QITA;
    }

}
