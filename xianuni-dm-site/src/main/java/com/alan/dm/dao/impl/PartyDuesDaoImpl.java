package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPartyDuesDao;
import com.alan.dm.dao.mapper.PartyDuesMapper;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyDuesPay;
import com.alan.dm.entity.condition.PartyDuesCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 党费管理DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */
@Repository(value ="partyDuesDao")
public class PartyDuesDaoImpl implements IPartyDuesDao {
    @Autowired
    private PartyDuesMapper partyDuesMapper;

    @Override
    public List<PartyDuesPay> getByCondition(PartyDuesCondition condition, Page page) throws DMException {
        return partyDuesMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(PartyDuesCondition condition) throws DMException {
        return partyDuesMapper.countByCondition(condition);
    }

    @Override
    public void insert(PartyDuesPay partyDuesPay) throws DMException {
        partyDuesMapper.insert(partyDuesPay);
    }

    @Override
    public void delete(PartyDuesPay partyDuesPay) throws DMException {
        partyDuesMapper.delete(partyDuesPay);
    }
}
