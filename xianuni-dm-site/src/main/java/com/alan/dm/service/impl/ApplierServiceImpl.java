package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IApplierInfoDao;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.ApplierInfoCondition;
import com.alan.dm.service.IApplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "applierService")
public class ApplierServiceImpl implements IApplierService {
    @Resource(name="applierInfoDao")
    private IApplierInfoDao applicationInfoDao;

    @Resource(name = "personInfoDao")
    private IPersonInfoDao personInfoDao;

    @Override
    public List<ApplierInfo> getByCondition(ApplierInfoCondition condition, Page page) throws DMException {
        return applicationInfoDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(ApplierInfoCondition condition) throws DMException {
        return applicationInfoDao.countByCondition(condition);
    }

    @Override
    public void createApplier(ApplierInfo applierInfo) throws DMException {
        //添加数据
        applierInfo.setCreateTime(new Date());
        applicationInfoDao.insert(applierInfo);
        //修改用户状态
        applierInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(applierInfo.getPerson(), PersonStatus.APPLIER);
    }

    @Override
    public void deleteApplier(ApplierInfo applierInfo) throws DMException {
        //删除申请人信息
        applicationInfoDao.delete(applierInfo);
        //状态修改为基础人员
        applierInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(applierInfo.getPerson(),PersonStatus.NO);
    }

    @Override
    public ApplierInfo getById(int applierId) throws DMException {
        ApplierInfo applierInfo = applicationInfoDao.getById(applierId);
        if(applierInfo!=null){
            Person person=new Person();
            person.setId(applierInfo.getPersonId());
            applierInfo.setPerson(person);
        }
        return applierInfo;
    }
    @Override
    public ApplierInfo getByPerson(Person person) throws DMException {
        return applicationInfoDao.getByPerson(person);
    }
}
