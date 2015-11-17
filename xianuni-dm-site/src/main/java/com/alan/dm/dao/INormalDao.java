package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.NormalInfo;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface INormalDao {
    /**
     *
     * @param personId
     * @return
     * @throws DMException
     */
    NormalInfo getByPerson(int personId) throws DMException;
}
