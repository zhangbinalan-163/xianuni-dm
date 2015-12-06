package com.alan.dm.entity.condition;


import com.alan.dm.entity.query.PersonCondition;

import java.util.List;

/**
 * 会议查询条件
 * @Date: 2015-11-14
 * @author: fan
 */
public class OrgMeetingCondition extends PersonCondition{
    private List<Integer> typeList;

    private String theme; // 主题

    public OrgMeetingCondition() {
    }

    public List<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Integer> typeList) {
        this.typeList = typeList;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
