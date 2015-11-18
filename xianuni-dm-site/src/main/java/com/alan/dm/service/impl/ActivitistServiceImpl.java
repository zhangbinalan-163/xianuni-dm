package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IActivitistInfoDao;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import com.alan.dm.service.IActivitistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "activitistService")
public class ActivitistServiceImpl implements IActivitistService{

    @Resource(name="activitistInfoDao")
    private IActivitistInfoDao activitistInfoDao;

    @Override
    public List<ActivitistInfo> getByCondition(ActivitistInfoCondition condition, Page page) throws DMException {
        return activitistInfoDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(ActivitistInfoCondition condition) throws DMException {
        return activitistInfoDao.countByCondition(condition);
    }
}
