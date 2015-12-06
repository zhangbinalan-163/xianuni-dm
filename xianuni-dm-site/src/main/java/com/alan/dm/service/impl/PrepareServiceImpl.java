package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.PrepareInfoMapper;
import com.alan.dm.entity.*;
import com.alan.dm.service.IPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "prepareService")
public class PrepareServiceImpl implements IPrepareService {
    @Autowired
    private PrepareInfoMapper prepareInfoMapper;

    @Override
    public void createPrepare(PrepareInfo prepareInfo) throws DMException {
        prepareInfo.setCreateTime(new Date());
        prepareInfoMapper.insert(prepareInfo);
    }

    @Override
    public void updatePrepare(PrepareInfo prepareInfo) throws DMException {
        prepareInfo.setUpdateTime(new Date());
        prepareInfoMapper.update(prepareInfo);
    }

    @Override
    public void deletePrepare(PrepareInfo prepareInfo) throws DMException {
        prepareInfoMapper.delete(prepareInfo);
    }

    @Override
    public PrepareInfo getById(int prepareId) throws DMException {
        return prepareInfoMapper.getById(prepareId);
    }

    @Override
    public int countByOrgWithTime(List<Integer> orgIdlist, Date start, Date end) throws DMException {
        return prepareInfoMapper.countByOrgWithTime(orgIdlist,start,end);
    }
}
