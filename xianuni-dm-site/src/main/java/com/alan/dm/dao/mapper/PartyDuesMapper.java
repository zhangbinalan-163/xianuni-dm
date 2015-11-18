package com.alan.dm.dao.mapper;

import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyDuesPay;
import com.alan.dm.entity.condition.PartyDuesCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 党费管理Mapper
 * @Date: 2015-11-16
 * @author: fan
 */
public interface PartyDuesMapper {
    /**
     *
     * @param condition
     * @param page
     * @return
     */
    List<PartyDuesPay> getByCondition(@Param(value = "condition") PartyDuesCondition condition,
                                       @Param(value = "page") Page page);

    /**
     *
     * @param condition
     * @return
     */
    int countByCondition(@Param(value = "condition") PartyDuesCondition condition);
}
