package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPrepareDao;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.PrepareInfo;
import com.alan.dm.service.IPrepareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "prepareService")
public class PrepareServiceImpl implements IPrepareService {

    @Resource(name = "prepareInfoDao")
    private IPrepareDao prepareDao;

    @Override
    public PrepareInfo getByPerson(Person person) throws DMException {
        return prepareDao.getByPerson(person.getId());
    }
}
