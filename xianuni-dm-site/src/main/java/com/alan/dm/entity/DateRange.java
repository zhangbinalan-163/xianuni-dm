package com.alan.dm.entity;

import java.util.Date;

/**
 * 时间范围
 * @Date: 2015-11-14
 * @author: fan
 */
public class DateRange {
    private Date startDate; // 开始日期
    private Date endDate; // 结束日期

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
