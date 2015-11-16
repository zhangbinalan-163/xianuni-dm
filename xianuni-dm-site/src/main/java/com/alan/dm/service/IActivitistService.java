package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.ApplicationInfo;
import com.alan.dm.entity.Person;

/**
 * 党员积极分子
 * Created by zhangbinalan on 15/11/16.
 */
public interface IActivitistService {
    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    ActivitistInfo getByPerson(Person person) throws DMException;
}
