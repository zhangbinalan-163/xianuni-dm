package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.IntentionInfoCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IIntentionService {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<IntentionInfo> getByCondition(IntentionInfoCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(IntentionInfoCondition condition)throws DMException;
    /**
     *
     * @param intentionInfo
     * @throws DMException
     */
    void createIntention(IntentionInfo intentionInfo) throws DMException;

    /**
     *
     * @param intentionInfo
     * @throws DMException
     */
    void deleteIntention(IntentionInfo intentionInfo) throws DMException;

    /**
     *
     * @param intentionId
     * @return
     * @throws DMException
     */
    IntentionInfo getById(int intentionId) throws DMException;

    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    IntentionInfo getByPerson(Person person) throws DMException;
}
