package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IActivitistInfoDao {
    /**
     *
     * @param personId
     * @return
     * @throws DMException
     */
    ActivitistInfo getByPerson(int personId) throws DMException;
}
