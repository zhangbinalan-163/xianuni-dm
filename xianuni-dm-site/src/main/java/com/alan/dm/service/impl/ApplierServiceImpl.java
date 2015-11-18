package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IApplierInfoDao;
import com.alan.dm.entity.ApplierInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.ApplierInfoCondition;
import com.alan.dm.service.IApplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "applierService")
public class ApplierServiceImpl implements IApplierService {

    @Resource(name="applierInfoDao")
    private IApplierInfoDao applicationInfoDao;

    @Override
    public List<ApplierInfo> getByCondition(ApplierInfoCondition condition, Page page) throws DMException {
        return applicationInfoDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(ApplierInfoCondition condition) throws DMException {
        return applicationInfoDao.countByCondition(condition);
    }
}