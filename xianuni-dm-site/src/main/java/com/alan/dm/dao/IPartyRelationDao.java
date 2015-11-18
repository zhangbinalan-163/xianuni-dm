package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.RelationTransferInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MemberCondition;

import java.util.List;

/**
 * 党组织关系转入转出DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IPartyRelationDao {
    int insert(RelationTransferInfo relation) throws DMException;

    void delete(RelationTransferInfo relation) throws DMException;

    void update(RelationTransferInfo relation) throws DMException;

    RelationTransferInfo findOne(int id) throws DMException;

    List<RelationTransferInfo> getMembers(MemberCondition condition, Page page) throws DMException;
}
