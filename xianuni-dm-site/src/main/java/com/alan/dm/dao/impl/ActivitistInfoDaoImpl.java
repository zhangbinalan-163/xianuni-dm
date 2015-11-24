package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IActivitistInfoDao;
import com.alan.dm.dao.mapper.ActivitistInfoMapper;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "activitistInfoDao")
public class ActivitistInfoDaoImpl implements IActivitistInfoDao {
    @Autowired
    private ActivitistInfoMapper activitistInfoMapper;

    @Override
    public List<ActivitistInfo> getByCondition(ActivitistInfoCondition condition, Page page) throws DMException {
        return activitistInfoMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(ActivitistInfoCondition condition) throws DMException {
        return activitistInfoMapper.countByCondition(condition);
    }

    @Override
    public void insert(ActivitistInfo activitistInfo) throws DMException {
        activitistInfoMapper.insert(activitistInfo);
    }

    @Override
    public void delete(ActivitistInfo activitistInfo) throws DMException {
        activitistInfoMapper.delete(activitistInfo);
    }

    @Override
    public ActivitistInfo getById(int activitistId) throws DMException {
        return activitistInfoMapper.getById(activitistId);
    }

    @Override
    public ActivitistInfo getByPerson(Person person) throws DMException {
        return activitistInfoMapper.getByPersonId(person.getId());
    }
}
