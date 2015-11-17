package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ApplicationInfo;
import com.alan.dm.entity.Person;

/**
 * 党员申请人
 * Created by zhangbinalan on 15/11/16.
 */
public interface IApplicationService {
    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    ApplicationInfo getByPerson(Person person) throws DMException;
}
