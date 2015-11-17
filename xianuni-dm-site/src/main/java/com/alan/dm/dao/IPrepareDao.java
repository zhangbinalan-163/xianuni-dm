package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.condition.PrepareInfo;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IPrepareDao {
    /**
     *
     * @param personId
     * @return
     * @throws DMException
     */
    PrepareInfo getByPerson(int personId) throws DMException;
}
