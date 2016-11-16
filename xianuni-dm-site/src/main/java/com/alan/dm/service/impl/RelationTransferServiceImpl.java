package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.RelationTransferMapper;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.RelationTransferInfo;
import com.alan.dm.entity.condition.RelationTransferCondition;
import com.alan.dm.service.IRelationTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by apple on 15/11/18.
 */
@Service(value = "relationTransferService")
public class RelationTransferServiceImpl implements IRelationTransferService {
    @Autowired
    private RelationTransferMapper relationTransferMapper;

    @Override
    public void updateTransfer(RelationTransferInfo relationTransferInfo) throws DMException {
        relationTransferMapper.update(relationTransferInfo);
    }

    @Override
    public List<RelationTransferInfo> getByCondition(RelationTransferCondition condition, Page page) throws DMException {
        return relationTransferMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(RelationTransferCondition condition) throws DMException {
        return relationTransferMapper.countByCondition(condition);
    }

    @Override
    public void createTransfer(RelationTransferInfo relationTransferInfo) throws DMException {
        relationTransferMapper.insert(relationTransferInfo);
    }

    @Override
    public void deleteTransfer(RelationTransferInfo relationTransferInfo) throws DMException {
        relationTransferMapper.delete(relationTransferInfo);
    }

    @Override
    public RelationTransferInfo getById(int id) throws DMException{
        return relationTransferMapper.getById(id);
    }
}
