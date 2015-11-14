package com.alan.dm.entity;

import java.util.Date;

/**
 * 通知
 * @Date: 2015-11-14
 * @author: fan
 */
public class Message {
    private int id;
    private Date date; // 日期
    private String type; // 类型
    private String title; // 标题
    private String content; // 内容

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
