package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ApplierInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.ApplierInfoCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IApplierInfoDao {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<ApplierInfo> getByCondition(ApplierInfoCondition condition, Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(ApplierInfoCondition condition)throws DMException;

    /**
     *
     * @param applierInfo
     * @throws DMException
     */
    void insert(ApplierInfo applierInfo) throws DMException;
}
