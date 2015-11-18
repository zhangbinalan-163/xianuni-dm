package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPartyRelationDao;
import com.alan.dm.entity.RelationTransferInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MemberCondition;

import java.util.List;

/**
 * 组织转入转出关系DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */
public class PartyRelationDaoImpl  implements IPartyRelationDao {
    @Override
    public int insert(RelationTransferInfo relation) throws DMException {
        return 0;
    }

    @Override
    public void delete(RelationTransferInfo relation) throws DMException {

    }

    @Override
    public void update(RelationTransferInfo relation) throws DMException {

    }

    @Override
    public RelationTransferInfo findOne(int id) throws DMException {
        return null;
    }

    @Override
    public List<RelationTransferInfo> getMembers(MemberCondition condition, Page page) throws DMException {
        return null;
    }
}
