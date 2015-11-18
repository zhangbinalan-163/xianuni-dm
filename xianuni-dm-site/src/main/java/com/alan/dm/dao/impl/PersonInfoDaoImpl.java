package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.dao.mapper.PersonInfoMapper;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.PersonCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 基础信息DAO
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "personInfoDao")
public class PersonInfoDaoImpl implements IPersonInfoDao {

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Override
    public List<Person> getByCondition(PersonCondition condition, Page page) throws DMException {
        return personInfoMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(PersonCondition condition) throws DMException {
        return personInfoMapper.countByCondition(condition);
    }
}
