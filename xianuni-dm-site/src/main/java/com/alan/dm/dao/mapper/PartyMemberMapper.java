package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyMember;
import com.alan.dm.entity.condition.MemberCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface PartyMemberMapper {
    int insert(PartyMember memberInfo) throws DMException;

    void delete(PartyMember memberInfo) throws DMException;

    void update(PartyMember memberInfo) throws DMException;

    PartyMember findOne(@Param(value = "id") int id) throws DMException;

    List<PartyMember> getMembers(@Param(value = "con") MemberCondition condition,
                                 @Param(value = "page")Page page) throws DMException;
}
