package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.DateRange;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyDuesPay;
import com.alan.dm.entity.PartyDuesStatis;
import com.alan.dm.entity.condition.PartyDuesCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 党费管理Mapper
 * @Date: 2015-11-16
 * @author: fan
 */
public interface PartyDuesMapper {
    int insert(PartyDuesPay partyDuesPay) throws DMException;
    void delete(PartyDuesPay partyDuesPay) throws DMException;
    void update(PartyDuesPay partyDuesPay) throws DMException;
    PartyDuesPay findOne(int id) throws DMException;
    List<PartyDuesPay> getPartyDuesPay(@Param(value = "con") PartyDuesCondition condition,
                                       @Param(value = "page") Page page) throws DMException;

    /**
     * 根据日期统计党费缴费状况
     * @param range
     * @param page
     * @return
     * @throws DMException
     */
    List<PartyDuesStatis> statisPartyDuesPay(@Param(value = "con") DateRange range,
                                             @Param(value = "page") Page page) throws DMException;
}
