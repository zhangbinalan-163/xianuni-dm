package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.Person;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IIntentionService {
    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    IntentionInfo getByPerson(Person person) throws DMException;
}
