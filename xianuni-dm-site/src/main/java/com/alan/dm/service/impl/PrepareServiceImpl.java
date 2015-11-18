package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPrepareInfoDao;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.PrepareInfo;
import com.alan.dm.entity.condition.PrepareInfoCondition;
import com.alan.dm.service.IPrepareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "prepareService")
public class PrepareServiceImpl implements IPrepareService {

    @Resource(name = "prepareInfoDao")
    private IPrepareInfoDao prepareDao;

    @Override
    public List<PrepareInfo> getByCondition(PrepareInfoCondition condition, Page page) throws DMException {
        return prepareDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(PrepareInfoCondition condition) throws DMException {
        return prepareDao.countByCondition(condition);
    }
}
