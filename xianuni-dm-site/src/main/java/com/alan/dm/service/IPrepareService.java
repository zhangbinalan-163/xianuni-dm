package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.PrepareInfo;

/**
 * 预备党员
 * Created by zhangbinalan on 15/11/16.
 */
public interface IPrepareService {
    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    PrepareInfo getByPerson(Person person) throws DMException;
}
