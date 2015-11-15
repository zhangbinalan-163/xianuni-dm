package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.DateRange;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyDuesPay;
import com.alan.dm.entity.PartyDuesStatis;
import com.alan.dm.entity.condition.PartyDuesCondition;

import java.util.List;

/**
 * 党费管理
 * @Date: 2015-11-14
 * @author: fan
 */
public interface IPartyDuesService {

    /**
     * 新增党员缴费
     * @param dues
     * @return
     * @throws DMException
     */
    int addDues(PartyDuesPay dues) throws DMException;

    /**
     * 删除党员缴费信息
     * @param id
     * @throws DMException
     */
    void delete(int id) throws DMException;

    /**
     * 获取党员缴费记录
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<PartyDuesPay> getPartyDuesPay(PartyDuesCondition condition, Page page) throws DMException;

    /**
     * 各支部党员缴费统计
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<PartyDuesStatis> getDuesStatis(DateRange condition, Page page) throws DMException;
}
