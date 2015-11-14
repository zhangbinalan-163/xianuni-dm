package com.alan.dm.entity.condition;

import com.alan.dm.entity.DateRange;

/**
 * 学习记录查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class StudyRecordCondition {
    private String studyType; // 培训类别
    private String title; // 组织名称
    private DateRange range; // 时间范围

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateRange getRange() {
        return range;
    }

    public void setRange(DateRange range) {
        this.range = range;
    }
}
