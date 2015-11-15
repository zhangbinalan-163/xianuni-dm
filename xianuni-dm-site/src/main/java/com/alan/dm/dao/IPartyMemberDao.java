package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyMember;
import com.alan.dm.entity.condition.MemberCondition;

import java.util.List;

/**
 * 党员管理DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IPartyMemberDao {
    int insert(PartyMember memberInfo) throws DMException;

    void delete(PartyMember memberInfo) throws DMException;

    void update(PartyMember memberInfo) throws DMException;

    PartyMember findOne(int id) throws DMException;

    List<PartyMember> getMembers(MemberCondition condition, Page page) throws DMException;

    void batchInsert(List<PartyMember> memberInfos) throws DMException;

}
