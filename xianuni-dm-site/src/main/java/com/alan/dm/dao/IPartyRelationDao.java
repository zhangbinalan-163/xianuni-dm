package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrganizationalRelation;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MemberCondition;

import java.util.List;

/**
 * 党组织关系转入转出DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IPartyRelationDao {
    int insert(OrganizationalRelation relation) throws DMException;

    void delete(OrganizationalRelation relation) throws DMException;

    void update(OrganizationalRelation relation) throws DMException;

    OrganizationalRelation findOne(int id) throws DMException;

    List<OrganizationalRelation> getMembers(MemberCondition condition, Page page) throws DMException;
}
