package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.NormalInfoCondition;
import com.alan.dm.entity.condition.PrepareInfoCondition;

import java.util.List;

/**
 * 预备党员
 * Created by zhangbinalan on 15/11/16.
 */
public interface IPrepareService {
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
    /**
     *
     * @param prepareInfo
     * @throws DMException
     */
    void createPrepare(PrepareInfo prepareInfo) throws DMException;

    /**
     *
     * @param prepareInfo
     * @throws DMException
     */
    void deletePrepare(PrepareInfo prepareInfo) throws DMException;

    /**
     *
     * @param prepareId
     * @return
     * @throws DMException
     */
    PrepareInfo getById(int prepareId) throws DMException;

    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    PrepareInfo getByPerson(Person person) throws DMException;
}
