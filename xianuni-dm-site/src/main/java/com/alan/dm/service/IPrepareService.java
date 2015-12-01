package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.*;

import java.util.Date;
import java.util.List;

/**
 * 预备党员
 * Created by zhangbinalan on 15/11/16.
 */
public interface IPrepareService {
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
     * @param start
     * @param end
     * @return
     * @throws DMException
     */
    int countByOrgWithTime(List<Integer> orgIdlist,Date start,Date end) throws DMException;
}
