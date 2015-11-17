package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ApplicationInfo;
import com.alan.dm.entity.Person;
import com.alan.dm.service.IApplicationService;

/**
 * Created by zhangbinalan on 15/11/16.
 */
public class ApplicationServiceDao implements IApplicationService{
    @Override
    public ApplicationInfo getByPerson(Person person) throws DMException {
        return null;
    }
}
