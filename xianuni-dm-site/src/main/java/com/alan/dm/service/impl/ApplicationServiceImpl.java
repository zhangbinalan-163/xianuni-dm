package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IApplicationInfoDao;
import com.alan.dm.entity.ApplicationInfo;
import com.alan.dm.entity.Person;
import com.alan.dm.service.IApplicationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "applicationService")
public class ApplicationServiceImpl implements IApplicationService{

    @Resource(name="applicationInfoDao")
    private IApplicationInfoDao applicationInfoDao;

    @Override
    public ApplicationInfo getByPerson(Person person) throws DMException {
        return applicationInfoDao.getByPerson(person.getId());
    }
}
