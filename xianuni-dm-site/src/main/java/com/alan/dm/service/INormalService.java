package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.*;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface INormalService {
    /**
     *
     * @param normalInfo
     * @throws DMException
     */
    void updateNormal(NormalInfo normalInfo) throws DMException;
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
     * @param start
     * @param end
     * @return
     * @throws DMException
     */
    int countByOrgWithTime(List<Integer> orgIdlist,Date start,Date end) throws DMException;
}