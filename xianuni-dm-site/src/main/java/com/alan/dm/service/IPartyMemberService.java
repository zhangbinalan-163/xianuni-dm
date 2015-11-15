package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.PartyMember;
import com.alan.dm.entity.condition.MemberCondition;

import java.util.List;

/**
 * 党员管理
 * @Date: 2015-11-14
 * @author: fan
 */
public interface IPartyMemberService {

    /**
     * 新增一名党员
     * @param member
     * @return
     * @throws DMException
     */
    int addMember(PartyMember member) throws DMException;

    /**
     * 批量导入党员信息
     * @param list
     * @throws DMException
     */
    void batchMembers(List<PartyMember> list) throws DMException;

    /**
     * 修改党员信息
     * @param member
     * @throws DMException
     */
    void modifyMember(PartyMember member) throws DMException;

    /**
     * 删除党员信息
     * @param id
     * @throws DMException
     */
    void deleteMember(int id) throws DMException;

    /**
     * 获取党员列表
     * @param condition
     * @return
     * @throws DMException
     */
    List<PartyMember> getMembers(MemberCondition condition) throws DMException;

    /**
     * 根据ID获取党员信息
     * @param id
     * @return
     * @throws DMException
     */
    PartyMember getMember(int id) throws DMException;


}
