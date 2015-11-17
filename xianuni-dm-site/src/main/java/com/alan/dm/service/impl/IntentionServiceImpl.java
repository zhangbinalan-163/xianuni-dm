package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IIntentionDao;
import com.alan.dm.dao.IPrepareDao;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.PrepareInfo;
import com.alan.dm.service.IIntentionService;
import com.alan.dm.service.IPrepareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "intentionService")
public class IntentionServiceImpl implements IIntentionService {

    @Resource(name = "intentionInfoDao")
    private IIntentionDao intentionDao;

    @Override
    public IntentionInfo getByPerson(Person person) throws DMException {
        return intentionDao.getByPerson(person.getId());
    }
}
