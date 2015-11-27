package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.*;

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
}
