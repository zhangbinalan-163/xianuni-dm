package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.dao.IPrepareInfoDao;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.PrepareInfoCondition;
import com.alan.dm.service.IPrepareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "prepareService")
public class PrepareServiceImpl implements IPrepareService {

    @Resource(name = "prepareInfoDao")
    private IPrepareInfoDao prepareDao;

    @Resource(name = "personInfoDao")
    private IPersonInfoDao personInfoDao;

    @Override
    public List<PrepareInfo> getByCondition(PrepareInfoCondition condition, Page page) throws DMException {
        return prepareDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(PrepareInfoCondition condition) throws DMException {
        return prepareDao.countByCondition(condition);
    }

    @Override
    public void createPrepare(PrepareInfo prepareInfo) throws DMException {
        prepareInfo.setCreateTime(new Date());
        prepareDao.insert(prepareInfo);
        //修改用户状态
        prepareInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(prepareInfo.getPerson(), PersonStatus.PERPARE);
    }

    @Override
    public void deletePrepare(PrepareInfo prepareInfo) throws DMException {
        prepareDao.delete(prepareInfo);
        //修改用户状态
        prepareInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(prepareInfo.getPerson(), PersonStatus.INTENTION);
    }

    @Override
    public PrepareInfo getById(int prepareId) throws DMException {
        return prepareDao.getById(prepareId);
    }

    @Override
    public PrepareInfo getByPerson(Person person) throws DMException {
        return prepareDao.getByPerson(person);
    }
}
