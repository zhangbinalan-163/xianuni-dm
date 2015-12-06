package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.ApplierInfoMapper;
import com.alan.dm.entity.*;
import com.alan.dm.service.IApplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "applierService")
public class ApplierServiceImpl implements IApplierService {
    @Autowired
    private ApplierInfoMapper applierInfoMapper;

    @Override
    public void createApplier(ApplierInfo applierInfo) throws DMException {
        //添加数据
        applierInfo.setCreateTime(new Date());
        applierInfoMapper.insert(applierInfo);
    }

    @Override
    public void deleteApplier(ApplierInfo applierInfo) throws DMException {
        //删除申请人信息
        applierInfoMapper.delete(applierInfo.getId());
    }

    @Override
    public ApplierInfo getById(int applierId) throws DMException {
        return applierInfoMapper.getById(applierId);
    }

    @Override
    public void updateApplier(ApplierInfo applierInfo) throws DMException {
        applierInfo.setUpdateTime(new Date());
        applierInfoMapper.update(applierInfo);
    }
}
