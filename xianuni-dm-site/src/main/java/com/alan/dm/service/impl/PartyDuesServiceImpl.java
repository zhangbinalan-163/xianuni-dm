package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPartyDuesDao;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyDuesPay;
import com.alan.dm.entity.condition.PartyDuesCondition;
import com.alan.dm.service.IPartyDuesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "partyDuesService")
public class PartyDuesServiceImpl implements IPartyDuesService {

    @Resource(name = "partyDuesDao")
    private IPartyDuesDao partyDuesDao;

    @Override
    public List<PartyDuesPay> getByCondition(PartyDuesCondition condition, Page page) throws DMException {
        return partyDuesDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(PartyDuesCondition condition) throws DMException {
        return partyDuesDao.countByCondition(condition);
    }
}
