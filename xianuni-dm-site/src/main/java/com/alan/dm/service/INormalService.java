package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.ApplierInfoCondition;
import com.alan.dm.entity.condition.NormalInfoCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface INormalService {
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
    void createNormal(NormalInfo normalInfo) throws DMException;

    /**
     *
     * @param normalInfo
     * @throws DMException
     */
    void deleteNormal(NormalInfo normalInfo) throws DMException;

    /**
     *
     * @param normalId
     * @return
     * @throws DMException
     */
    NormalInfo getById(int normalId) throws DMException;

    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    NormalInfo getByPerson(Person person) throws DMException;

}