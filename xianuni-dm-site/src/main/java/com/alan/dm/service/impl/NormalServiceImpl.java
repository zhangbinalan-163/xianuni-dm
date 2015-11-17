package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IIntentionDao;
import com.alan.dm.dao.INormalDao;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Person;
import com.alan.dm.service.IIntentionService;
import com.alan.dm.service.INormalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "normalService")
public class NormalServiceImpl implements INormalService {

    @Resource(name = "normalDao")
    private INormalDao normalDao;

    @Override
    public NormalInfo getByPerson(Person person) throws DMException {
        return normalDao.getByPerson(person.getId());
    }
}
