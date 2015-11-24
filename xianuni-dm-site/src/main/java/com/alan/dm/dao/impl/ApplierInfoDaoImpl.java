package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IApplierInfoDao;
import com.alan.dm.dao.mapper.ApplierInfoMapper;
import com.alan.dm.entity.ApplierInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.ApplierInfoCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "applierInfoDao")
public class ApplierInfoDaoImpl implements IApplierInfoDao {
    @Autowired
    private ApplierInfoMapper applierInfoMapper;

    @Override
    public List<ApplierInfo> getByCondition(ApplierInfoCondition condition, Page page) throws DMException {
        return applierInfoMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(ApplierInfoCondition condition) throws DMException {
        return applierInfoMapper.countByCondition(condition);
    }

    @Override
    public void insert(ApplierInfo applierInfo) throws DMException {
        applierInfo.setCreateTime(new Date());
        applierInfoMapper.insert(applierInfo);
    }

    @Override
    public void delete(ApplierInfo applierInfo) throws DMException {
        applierInfoMapper.delete(applierInfo.getId());
    }

    @Override
    public ApplierInfo getById(int applierId) throws DMException {
        return applierInfoMapper.getById(applierId);
    }

    @Override
    public ApplierInfo getByPerson(Person person) throws DMException {
        return applierInfoMapper.getByPersonId(person.getId());
    }
}
