package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.ActivitistInfoMapper;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.service.IActivitistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "activitistService")
public class ActivitistServiceImpl implements IActivitistService{
    @Autowired
    private ActivitistInfoMapper activitistInfoMapper;

    @Override
    public void createActivitist(ActivitistInfo activitistInfo) throws DMException {
        activitistInfo.setCreateTime(new Date());
        activitistInfoMapper.insert(activitistInfo);
    }

    @Override
    public void deleteActivitist(ActivitistInfo activitistInfo) throws DMException {
        activitistInfoMapper.delete(activitistInfo.getId());
    }

    @Override
    public ActivitistInfo getById(int activitistId) throws DMException {
        return activitistInfoMapper.getById(activitistId);
    }

    @Override
    public void updateActivitist(ActivitistInfo activitistInfo) throws DMException {
        activitistInfo.setUpdateTime(new Date());
        activitistInfoMapper.update(activitistInfo);
    }
}
