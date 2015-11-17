package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.condition.PrepareInfo;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IIntentionDao {
    /**
     *
     * @param personId
     * @return
     * @throws DMException
     */
    IntentionInfo getByPerson(int personId) throws DMException;
}
