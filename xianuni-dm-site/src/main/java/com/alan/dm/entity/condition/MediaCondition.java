package com.alan.dm.entity.condition;

import java.util.Date;

/**
 * 媒体资源查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class MediaCondition {
    private int mediaType; // 媒体类型
    private String mediaName; // 媒体名称
    private Date uploadTime; // 上传日期
    private boolean forbidden; // 禁用

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public boolean isForbidden() {
        return forbidden;
    }

    public void setForbidden(boolean forbidden) {
        this.forbidden = forbidden;
    }
}

