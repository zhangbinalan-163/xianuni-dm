package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrganizationalRelation;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MemberCondition;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface PartyRelationMapper {
    int insert(OrganizationalRelation relation) throws DMException;

    void delete(OrganizationalRelation relation) throws DMException;

    void update(OrganizationalRelation relation) throws DMException;

    OrganizationalRelation findOne(int id) throws DMException;

    List<OrganizationalRelation> getRelation(MemberCondition condition, Page page) throws DMException;

}
