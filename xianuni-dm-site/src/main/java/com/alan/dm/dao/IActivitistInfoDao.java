package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.ActivitistInfoCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IActivitistInfoDao {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<ActivitistInfo> getByCondition(ActivitistInfoCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(ActivitistInfoCondition condition)throws DMException;
}
