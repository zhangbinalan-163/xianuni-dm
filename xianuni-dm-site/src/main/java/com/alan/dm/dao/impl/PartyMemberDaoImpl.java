package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPartyMemberDao;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyMember;
import com.alan.dm.entity.condition.MemberCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2015-11-15
 * @author: fan
 */
@Repository(value = "partyMemberDao")
public class PartyMemberDaoImpl implements IPartyMemberDao {
    @Override
    public int insert(PartyMember memberInfo) throws DMException {
        return 0;
    }

    @Override
    public void delete(PartyMember memberInfo) throws DMException {

    }

    @Override
    public void update(PartyMember memberInfo) throws DMException {

    }

    @Override
    public PartyMember findOne(int id) throws DMException {
        return null;
    }

    @Override
    public List<PartyMember> getMembers(MemberCondition condition, Page page) throws DMException {
        return null;
    }

    @Override
    public void batchInsert(List<PartyMember> memberInfos) throws DMException {

    }
}
