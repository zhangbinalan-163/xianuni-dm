package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IIntentionInfoDao;
import com.alan.dm.dao.mapper.IntentionInfoMapper;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.IntentionInfoCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "intentionInfoDao")
public class IntentionInfoDaoImpl implements IIntentionInfoDao {

    @Autowired
    private IntentionInfoMapper intentionInfoMapper;

    @Override
    public List<IntentionInfo> getByCondition(IntentionInfoCondition condition, Page page) throws DMException {
        return intentionInfoMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(IntentionInfoCondition condition) throws DMException {
        return intentionInfoMapper.countByCondition(condition);
    }
}
