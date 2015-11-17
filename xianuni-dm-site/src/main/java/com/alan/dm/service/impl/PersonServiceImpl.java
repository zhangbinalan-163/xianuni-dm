package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPersonDao;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.PersonCondition;
import com.alan.dm.service.IPersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "personService")
public class PersonServiceImpl implements IPersonService{

    @Resource(name = "personDao")
    private IPersonDao personDao;

    @Override
    public List<Person> getByCondition(PersonCondition condition, Page page) throws DMException {
        return personDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(PersonCondition condition) throws DMException {
        return personDao.countByCondition(condition);
    }
}
