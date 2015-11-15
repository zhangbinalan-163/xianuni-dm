package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IPartyRelationDao;
import com.alan.dm.entity.OrganizationalRelation;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MemberCondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织转入转出关系DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */
public class PartyRelationDaoImpl extends BaseDao implements IPartyRelationDao {
    private static final String TABLE_NAME = "organization_relation";
    @Override
    public int insert(OrganizationalRelation relation) throws DMException {
        String sql = "INSERT INTO "+ TABLE_NAME +
                " (organizationId, partyMemberId, rollInType, rollOutType, rollInOrganization, rollOutOrganization," +
                " rollDate, reviewDate, operator, tel, createTime)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        return update(sql, relation.getOrganizationId(), relation.getPartyMemberId(), relation.getRollInType(),
                relation.getRollOutType(), relation.getRollInOrganization(), relation.getRollOutOrganization(),
                relation.getRollDate(), relation.getReviewDate(), relation.getOperator(), relation.getTel(), new Date());
    }

    @Override
    public void delete(OrganizationalRelation relation) throws DMException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        update(sql, relation.getId());
    }

    @Override
    public void update(OrganizationalRelation relation) throws DMException {
        String sql = "UPDATE "+ TABLE_NAME + " SET organizationId=?, rollInType=?, rollOutType=?, rollInOrganization=?, " +
                "rollOutOrganization=?, rollDate=?, reviewDate=?, operator=?, tel=?, updateTime=? " +
                "WHERE id=?";
        update(sql, relation.getOrganizationId(), relation.getRollInType(), relation.getRollOutType(),
                relation.getRollInOrganization(), relation.getRollOutOrganization(), relation.getRollDate(),
                relation.getReviewDate(), relation.getOperator(), relation.getTel(), new Date(), relation.getId());
    }

    @Override
    public OrganizationalRelation findOne(int id) throws DMException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        return getBean(sql, OrganizationalRelation.class, id);
    }

    @Override
    public List<OrganizationalRelation> getMembers(MemberCondition condition, Page page) throws DMException {
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + " WHERE ");
        if(condition.getOrganizationId() != 0) {
            sql.append("organizationId=? ");
            objects.add(condition.getOrganizationId());
        }
        if(!StringUtils.isEmpty(condition.getName())) {
            sql.append("AND name=? ");
            objects.add(condition.getName());
        }
        if(!StringUtils.isEmpty(condition.getIdCardNo())) {
            sql.append("AND idCardNo=? ");
            objects.add(condition.getIdCardNo());
        }
        if(!StringUtils.isEmpty(condition.getMemberId())) {
            sql.append("AND memberId=? ");
            objects.add(condition.getMemberId());
        }
        if(condition.getMemberType() != 0) {
            sql.append("AND type=? ");
            objects.add(condition.getMemberType());
        }
        if(condition.getEducation() != 0) {
            sql.append("AND education=? ");
            objects.add(condition.getEducation());
        }
        if(condition.getSex() != 0) {
            sql.append("AND sex=? ");
            objects.add(condition.getSex());
        }
        if(condition.getStats() != 0) {
            sql.append("AND stats=? ");
            objects.add(condition.getStats());
        }
        setLimit(page.getCurrent(), page.getSize());
        return getBeanList(sql.toString(), OrganizationalRelation.class, objects.toArray());
    }
}
