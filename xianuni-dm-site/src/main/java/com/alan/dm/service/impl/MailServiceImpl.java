package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.MailMapper;
import com.alan.dm.entity.MailInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.query.MailCondition;
import com.alan.dm.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by apple on 15/11/29.
 */
@Service(value="mailService")
public class MailServiceImpl implements MailService {
    @Autowired
    private MailMapper mailMapper;

    @Override
    public int countByPerson(Person person) throws DMException {
        return mailMapper.countByPerson(person);
    }

    @Override
    public void deleteMail(MailInfo mailInfo) throws DMException {
        mailMapper.delete(mailInfo.getId());
    }

    @Override
    public List<MailInfo> getByPerson(Person person, Page page) throws DMException {
        return mailMapper.getByPerson(person,page);
    }

    @Override
    public MailInfo getById(Integer id) throws DMException {
        return mailMapper.getById(id);
    }

    @Override
    public void sendMail(MailInfo mailInfo) throws DMException {
        mailMapper.insert(mailInfo);
    }

    @Override
    public int countByCondition(MailCondition condition) throws DMException {
        return mailMapper.countByCondition(condition);
    }

    @Override
    public List<MailInfo> getByCondition(MailCondition condition, Page page) throws DMException {
        return mailMapper.getByCondition(condition,page);
    }
}
