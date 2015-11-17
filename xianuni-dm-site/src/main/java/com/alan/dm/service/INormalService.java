package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Person;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface INormalService {
    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    NormalInfo getByPerson(Person person) throws DMException;
}