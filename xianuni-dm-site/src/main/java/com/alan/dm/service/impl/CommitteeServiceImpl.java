package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.CommitteeInfoDao;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.entity.CommitteeInfo;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.CommitteeCondition;
import com.alan.dm.service.ICommitteeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/26.
 */
@Service(value = "committeeService")
public class CommitteeServiceImpl implements ICommitteeService {

    @Resource(name = "personInfoDao")
    private IPersonInfoDao personInfoDao;

    @Resource(name="committeeInfoDao")
    private CommitteeInfoDao committeeInfoDao;

    @Override
    public List<CommitteeInfo> getByCondition(CommitteeCondition condition, Page page) throws DMException {
        return committeeInfoDao.getByCondition(condition,page);
    }

    @Override
    public List<Person> getCandidatePerson(List<Orgnization> orgnizationList, String number, Page page) throws DMException {
        return personInfoDao.getCommitteeCandidateList(orgnizationList,number,page);
    }

    @Override
    public int countByCondition(CommitteeCondition condition) throws DMException {
        return committeeInfoDao.countByCondition(condition);
    }

    @Override
    public void createCommitteeInfo(CommitteeInfo committeeInfo) throws DMException {
        committeeInfo.setCreateTime(new Date());
        committeeInfoDao.insert(committeeInfo);
    }

    @Override
    public void deleteCommitteeInfo(CommitteeInfo committeeInfo) throws DMException {
        committeeInfoDao.delete(committeeInfo);
    }

    @Override
    public CommitteeInfo getById(int id) throws DMException {
        return committeeInfoDao.getById(id);
    }

    @Override
    public CommitteeInfo getByPerson(Person person) throws DMException {
        return committeeInfoDao.getByPerson(person);
    }
}
