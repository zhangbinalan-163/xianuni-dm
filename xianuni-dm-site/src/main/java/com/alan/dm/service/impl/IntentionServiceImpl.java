package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IIntentionInfoDao;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.IntentionInfoCondition;
import com.alan.dm.service.IIntentionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "intentionService")
public class IntentionServiceImpl implements IIntentionService {

    @Resource(name = "intentionInfoDao")
    private IIntentionInfoDao intentionDao;

    @Resource(name = "personInfoDao")
    private IPersonInfoDao personInfoDao;

    @Override
    public List<IntentionInfo> getByCondition(IntentionInfoCondition condition, Page page) throws DMException {
        return intentionDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(IntentionInfoCondition condition) throws DMException {
        return intentionDao.countByCondition(condition);
    }

    @Override
    public void createIntention(IntentionInfo intentionInfo) throws DMException {
        intentionInfo.setCreateTime(new Date());
        intentionDao.insert(intentionInfo);
        //修改用户状态
        intentionInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(intentionInfo.getPerson(), PersonStatus.INTENTION);
    }

    @Override
    public void deleteIntention(IntentionInfo intentionInfo) throws DMException {
        intentionDao.delete(intentionInfo);
        //修改用户状态
        intentionInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(intentionInfo.getPerson(), PersonStatus.ACTIVISTS);
    }

    @Override
    public IntentionInfo getById(int intentionId) throws DMException {
        return intentionDao.getById(intentionId);
    }

    @Override
    public IntentionInfo getByPerson(Person person) throws DMException {
        return intentionDao.getByPerson(person);
    }
}
