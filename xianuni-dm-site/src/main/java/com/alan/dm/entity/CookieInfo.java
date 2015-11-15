package com.alan.dm.entity;


/**
 * 登录信息实体
 * Created by zhangbinalan on 15/11/15.
 */
public class CookieInfo {
    private String cookie;
    private int userId;
    private String userIp;
    private long loginTime;
    private long timeValidate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public long getTimeValidate() {
        return timeValidate;
    }

    public void setTimeValidate(long timeValidate) {
        this.timeValidate = timeValidate;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
