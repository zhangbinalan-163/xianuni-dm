package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
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

    /**
     *
     * @param prepareInfo
     * @throws DMException
     */
    void insert(PrepareInfo prepareInfo) throws DMException;

    /**
     *
     * @param prepareInfo
     * @throws DMException
     */
    void delete(PrepareInfo prepareInfo) throws DMException;

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
