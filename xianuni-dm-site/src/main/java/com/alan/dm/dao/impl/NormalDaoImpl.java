package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.INormalDao;
import com.alan.dm.dao.mapper.NormalInfoMapper;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.NormalInfoCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "normalDao")
public class NormalDaoImpl implements INormalDao {
    @Autowired
    private NormalInfoMapper normalInfoMapper;

    @Override
    public List<NormalInfo> getByCondition(NormalInfoCondition condition, Page page) throws DMException {
        return normalInfoMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(NormalInfoCondition condition) throws DMException {
        return normalInfoMapper.countByCondition(condition);
    }

    @Override
    public void insert(NormalInfo normalInfo) throws DMException {
        normalInfoMapper.insert(normalInfo);
    }

    @Override
    public void delete(NormalInfo normalInfo) throws DMException {
        normalInfoMapper.delete(normalInfo);
    }

    @Override
    public NormalInfo getById(int normalId) throws DMException {
        return normalInfoMapper.getById(normalId);
    }

    @Override
    public NormalInfo getByPerson(Person person) throws DMException {
        return normalInfoMapper.getByPersonId(person.getId());
    }
}
