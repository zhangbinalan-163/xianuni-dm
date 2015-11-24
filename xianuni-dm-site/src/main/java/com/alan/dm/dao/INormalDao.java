package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.NormalInfoCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface INormalDao {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<NormalInfo> getByCondition(NormalInfoCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(NormalInfoCondition condition)throws DMException;

    /**
     *
     * @param normalInfo
     * @throws DMException
     */
    void insert(NormalInfo normalInfo) throws DMException;

    /**
     *
     * @param normalInfo
     * @throws DMException
     */
    void delete(NormalInfo normalInfo) throws DMException;

    /**
     *
     * @param prepareId
     * @return
     * @throws DMException
     */
    NormalInfo getById(int prepareId) throws DMException;

    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    NormalInfo getByPerson(Person person) throws DMException;
}
