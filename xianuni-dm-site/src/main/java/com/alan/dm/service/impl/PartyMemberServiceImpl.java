package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPartyMemberDao;
import com.alan.dm.entity.PartyMember;
import com.alan.dm.entity.condition.MemberCondition;
import com.alan.dm.service.IPartyMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date: 2015-11-15
 * @author: fan
 */
@Service(value = "partyMemberService")
public class PartyMemberServiceImpl implements IPartyMemberService{
    @Resource(name = "partyMemberDao")
    private IPartyMemberDao partyMemberDao;

    @Override
    public int addMember(PartyMember member) throws DMException {

        return partyMemberDao.insert(member);
    }

    @Override
    public void batchMembers(List<PartyMember> list) throws DMException {

    }

    @Override
    public void modifyMember(PartyMember member) throws DMException {

    }

    @Override
    public void deleteMember(int id) throws DMException {

    }

    @Override
    public List<PartyMember> getMembers(MemberCondition condition) throws DMException {
        return null;
    }

    @Override
    public PartyMember getMember(int id) throws DMException {
        return null;
    }
}
