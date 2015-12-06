package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ApplierInfo;

/**
 * 党员申请人
 * Created by zhangbinalan on 15/11/16.
 */
public interface IApplierService {
    /**
     *
     * @param applierInfo
     * @throws DMException
     */
    void updateApplier(ApplierInfo applierInfo) throws DMException;
    /**
     *
     * @param applierInfo
     * @throws DMException
     */
    void createApplier(ApplierInfo applierInfo) throws DMException;

    /**
     *
     * @param applierInfo
     * @throws DMException
     */
    void deleteApplier(ApplierInfo applierInfo) throws DMException;

    /**
     *
     * @param applierId
     * @return
     * @throws DMException
     */
    ApplierInfo getById(int applierId) throws DMException;
}
