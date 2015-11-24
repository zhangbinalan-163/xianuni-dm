package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.INormalDao;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.PersonStatus;
import com.alan.dm.entity.condition.NormalInfoCondition;
import com.alan.dm.service.INormalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "normalService")
public class NormalServiceImpl implements INormalService {
    @Resource(name = "normalDao")
    private INormalDao normalDao;

    @Resource(name = "personInfoDao")
    private IPersonInfoDao personInfoDao;

    @Override
    public List<NormalInfo> getByCondition(NormalInfoCondition condition, Page page) throws DMException {
        return normalDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(NormalInfoCondition condition) throws DMException {
        return normalDao.countByCondition(condition);
    }

    @Override
    public void createNormal(NormalInfo normalInfo) throws DMException {
        normalInfo.setCreateTime(new Date());
        normalDao.insert(normalInfo);
        //修改用户状态
        normalInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(normalInfo.getPerson(), PersonStatus.NORMAL);
    }

    @Override
    public void deleteNormal(NormalInfo normalInfo) throws DMException {
        normalDao.delete(normalInfo);
        //修改用户状态
        normalInfo.getPerson().setUpdateTime(new Date());
        personInfoDao.updateStatus(normalInfo.getPerson(), PersonStatus.PERPARE);

    }

    @Override
    public NormalInfo getById(int normalId) throws DMException {
        return normalDao.getById(normalId);
    }

    @Override
    public NormalInfo getByPerson(Person person) throws DMException {
        return normalDao.getByPerson(person);
    }
}
