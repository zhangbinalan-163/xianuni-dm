package com.alan.dm.entity;

/**
 * 分页信息
 * Created by zhangbinalan on 15/11/11.
 */
public class Page {
    private static final int DEFAULT_SIZE = 0;
    private static final int DEFAULT_CURRENT = 20;
    private int size;
    private int current;

    public Page() {
    }

    public Page(int size, int current) {
        this.size = size == 0 ? DEFAULT_SIZE : size;
        this.current = current == 0 ? DEFAULT_CURRENT : current;
    }

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
