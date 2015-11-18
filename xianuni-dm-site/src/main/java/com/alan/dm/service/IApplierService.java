package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.ApplierInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import com.alan.dm.entity.condition.ApplierInfoCondition;

import java.util.List;

/**
 * 党员申请人
 * Created by zhangbinalan on 15/11/16.
 */
public interface IApplierService {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<ApplierInfo> getByCondition(ApplierInfoCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(ApplierInfoCondition condition)throws DMException;
}
