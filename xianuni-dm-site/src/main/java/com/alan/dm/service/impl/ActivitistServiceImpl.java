package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IActivitistInfoDao;
import com.alan.dm.dao.IApplicationInfoDao;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.ApplicationInfo;
import com.alan.dm.entity.Person;
import com.alan.dm.service.IActivitistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "activitistService")
public class ActivitistServiceImpl implements IActivitistService{

    @Resource(name="activitistInfoDao")
    private IActivitistInfoDao activitistInfoDao;

    @Override
    public ActivitistInfo getByPerson(Person person) throws DMException {
        return activitistInfoDao.getByPerson(person.getId());
    }
}
