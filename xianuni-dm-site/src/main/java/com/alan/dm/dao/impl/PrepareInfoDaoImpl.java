package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPrepareInfoDao;
import com.alan.dm.dao.mapper.PrepareInfoMapper;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PrepareInfo;
import com.alan.dm.entity.condition.PersonCondition;
import com.alan.dm.entity.condition.PrepareInfoCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "prepareInfoDao")
public class PrepareInfoDaoImpl implements IPrepareInfoDao {

    @Autowired
    private PrepareInfoMapper prepareInfoMapper;

    @Override
    public List<PrepareInfo> getByCondition(PrepareInfoCondition condition, Page page) throws DMException {
        return prepareInfoMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(PrepareInfoCondition condition) throws DMException {
        return prepareInfoMapper.countByCondition(condition);
    }
}
