package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.CommitteeInfoDao;
import com.alan.dm.dao.mapper.CommitteeMapper;
import com.alan.dm.entity.CommitteeInfo;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.CommitteeCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/26.
 */
@Repository(value = "committeeInfoDao")
public class CommitteeInfoDaoImpl implements CommitteeInfoDao{
    @Autowired
    private CommitteeMapper committeeMapper;

    @Override
    public List<CommitteeInfo> getByCondition(CommitteeCondition condition, Page page) throws DMException {
        return committeeMapper.getByCondition(condition,page);
    }

    @Override
    public CommitteeInfo getByPerson(Person person) throws DMException {
        return null;
    }

    @Override
    public int countByCondition(CommitteeCondition condition) throws DMException {
        return committeeMapper.countByCondition(condition);
    }

    @Override
    public void insert(CommitteeInfo committeeInfo) throws DMException {
        committeeMapper.insert(committeeInfo);
    }

    @Override
    public void delete(CommitteeInfo committeeInfo) throws DMException {
        committeeMapper.delete(committeeInfo);
    }

    @Override
    public CommitteeInfo getById(int id) throws DMException {
        return committeeMapper.getById(id);
    }
}
