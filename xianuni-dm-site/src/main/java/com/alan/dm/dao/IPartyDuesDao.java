package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyDuesPay;
import com.alan.dm.entity.condition.PartyDuesCondition;

import java.util.List;

/**
 * 党费管理DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IPartyDuesDao {

    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<PartyDuesPay> getByCondition(PartyDuesCondition condition, Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(PartyDuesCondition condition) throws DMException;

    /**
     *
     * @param partyDuesPay
     * @throws DMException
     */
    void insert(PartyDuesPay partyDuesPay) throws DMException;

    /**
     *
     * @param partyDuesPay
     * @throws DMException
     */
    void delete(PartyDuesPay partyDuesPay) throws DMException;
}



