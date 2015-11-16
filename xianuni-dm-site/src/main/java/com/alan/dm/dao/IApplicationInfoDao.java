package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ApplicationInfo;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IApplicationInfoDao {
    /**
     *
     * @param personId
     * @return
     * @throws DMException
     */
    ApplicationInfo getByPerson(int personId) throws DMException;
}
