package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IRelationTransferInfoDao;
import com.alan.dm.dao.mapper.RelationTransferMapper;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.RelationTransferInfo;
import com.alan.dm.entity.condition.RelationTransferCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/18.
 */
@Repository(value = "relationTransferDao")
public class RelationTransferDaoImpl implements IRelationTransferInfoDao {
    @Autowired
    private RelationTransferMapper relationTransferMapper;

    @Override
    public List<RelationTransferInfo> getByCondition(RelationTransferCondition condition, Page page) throws DMException {
        return relationTransferMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(RelationTransferCondition condition) throws DMException {
        return relationTransferMapper.countByCondition(condition);
    }

    @Override
    public void insert(RelationTransferInfo relationTransferInfo) throws DMException {
        relationTransferMapper.insert(relationTransferInfo);
    }

    @Override
    public void delete(RelationTransferInfo relationTransferInfo) throws DMException {
        relationTransferMapper.delete(relationTransferInfo);
    }

    @Override
    public RelationTransferInfo getById(int id) throws DMException {
        return relationTransferMapper.getById(id);
    }
}
