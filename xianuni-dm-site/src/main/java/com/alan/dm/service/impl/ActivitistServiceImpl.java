package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IActivitistInfoDao;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.PersonStatus;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import com.alan.dm.service.IActivitistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "activitistService")
public class ActivitistServiceImpl implements IActivitistService{
    @Resource(name="activitistInfoDao")
    private IActivitistInfoDao activitistInfoDao;

    @Resource(name = "personInfoDao")
    private IPersonInfoDao personInfoDao;

    @Override
    public List<ActivitistInfo> getByCondition(ActivitistInfoCondition condition, Page page) throws DMException {
        return activitistInfoDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(ActivitistInfoCondition condition) throws DMException {
        return activitistInfoDao.countByCondition(condition);
    }

    @Override
    public void createActivitist(ActivitistInfo activitistInfo) throws DMException {
        activitistInfo.setCreateTime(new Date());
        activitistInfoDao.insert(activitistInfo);
        //修改用户状态
        activitistInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(activitistInfo.getPerson(), PersonStatus.ACTIVISTS);
    }

    @Override
    public void deleteActivitist(ActivitistInfo activitistInfo) throws DMException {
        activitistInfoDao.delete(activitistInfo);
        //状态修改为基础人员
        activitistInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(activitistInfo.getPerson(), PersonStatus.APPLIER);
    }

    @Override
    public ActivitistInfo getById(int activitistId) throws DMException {
        return activitistInfoDao.getById(activitistId);
    }

    @Override
    public ActivitistInfo getByPerson(Person person) throws DMException {
        return activitistInfoDao.getByPerson(person);
    }
}
