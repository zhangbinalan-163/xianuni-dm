package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.dao.mapper.PersonInfoMapper;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.PersonStatus;
import com.alan.dm.entity.condition.PersonCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    public List<Person> getCommitteeCandidateList(List<Orgnization> orgnizationList, String number, Page page) {
        return personInfoMapper.getCommitteeCandidateList(orgnizationList,number,page);
    }

    @Override
    public Person getById(int id) throws DMException {
        return personInfoMapper.getById(id);
    }

    @Override
    public Person getByNumber(String number) throws DMException {
        return personInfoMapper.getByNumber(number);
    }

    @Override
    public List<Person> getByCondition(PersonCondition condition, Page page) throws DMException {
        return personInfoMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(PersonCondition condition) throws DMException {
        return personInfoMapper.countByCondition(condition);
    }

    @Override
    public void insertPerson(Person person) throws DMException {
        personInfoMapper.insert(person);
    }

    @Override
    public void updateStatus(Person person,PersonStatus status) throws DMException {
        personInfoMapper.setStatus(person.getId(),status.getId(),person.getUpdateTime());
    }

    @Override
    public void delete(Person person) throws DMException {
        personInfoMapper.delete(person);
    }

    @Override
    public List<Person> getAdminCandidateList(List<Orgnization> orgnizationList,String number,Page page) {
        return personInfoMapper.getAdminCandidateList(orgnizationList,number,page);
    }
}
