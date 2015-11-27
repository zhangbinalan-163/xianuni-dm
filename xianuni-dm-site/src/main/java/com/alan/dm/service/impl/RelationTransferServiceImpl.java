package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IRelationTransferInfoDao;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.RelationTransferInfo;
import com.alan.dm.entity.condition.RelationTransferCondition;
import com.alan.dm.service.IRelationTransferService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by apple on 15/11/18.
 */
@Service(value = "relationTransferService")
public class RelationTransferServiceImpl implements IRelationTransferService {

    @Resource(name = "relationTransferDao")
    private IRelationTransferInfoDao relationTransferInfoDao;

    @Override
    public List<RelationTransferInfo> getByCondition(RelationTransferCondition condition, Page page) throws DMException {
        return relationTransferInfoDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(RelationTransferCondition condition) throws DMException {
        return relationTransferInfoDao.countByCondition(condition);
    }

    @Override
    public void createTransfer(RelationTransferInfo relationTransferInfo) throws DMException {
        relationTransferInfoDao.insert(relationTransferInfo);
    }

    @Override
    public void deleteTransfer(RelationTransferInfo relationTransferInfo) throws DMException {
        relationTransferInfoDao.delete(relationTransferInfo);
    }

    @Override
    public RelationTransferInfo getById(int id) throws DMException{
        return relationTransferInfoDao.getById(id);
    }
}
