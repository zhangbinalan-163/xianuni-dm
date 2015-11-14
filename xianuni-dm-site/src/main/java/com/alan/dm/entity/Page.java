package com.alan.dm.entity;

/**
 * 分页信息
 * Created by zhangbinalan on 15/11/11.
 */
public class Page {
    private int size;
    private int current;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
