package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PrepareInfo;
import com.alan.dm.entity.condition.PrepareInfoCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IPrepareInfoDao {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<PrepareInfo> getByCondition(PrepareInfoCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(PrepareInfoCondition condition)throws DMException;
}
