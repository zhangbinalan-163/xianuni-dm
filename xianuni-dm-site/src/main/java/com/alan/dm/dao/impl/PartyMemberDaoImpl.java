package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IPartyMemberDao;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyMember;
import com.alan.dm.entity.condition.MemberCondition;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2015-11-15
 * @author: fan
 */
@Repository(value = "partyMemberDao")
public class PartyMemberDaoImpl extends BaseDao implements IPartyMemberDao {
    private static final String TABLE_NAME = "party_member";
    @Override
    public int insert(PartyMember memberInfo) throws DMException {
        String sql = "INSERT INTO "+ TABLE_NAME +
                " (organizationId, type, memberId, name, idCardNo, sex, nation," +
                " education, degree, nativePlace, birthday, stats, origin, createTime)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return update(sql, memberInfo.getOrganizationId(), memberInfo.getType(), memberInfo.getMemberId(),
                memberInfo.getName(), memberInfo.getIdCardNo(), memberInfo.getSex(), memberInfo.getNation(),
                memberInfo.getEducation(), memberInfo.getDegree(), memberInfo.getNativePlace(),
                memberInfo.getBirthday(), memberInfo.getStats(), memberInfo.getOrigin(), new Date());
    }

    @Override
    public void delete(PartyMember memberInfo) throws DMException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        update(sql, memberInfo.getId());
    }

    @Override
    public void update(PartyMember memberInfo) throws DMException {
        String sql = "UPDATE " + TABLE_NAME + " SET type=?, name=?, idCardNo=?, sex=?, nation=?, education=?, " +
                "degree=?, nativePlace=?, birthday=?, stats=?, origin=?, updateTime=? " +
                "WHERE id=?";
        update(sql, memberInfo.getType(), memberInfo.getName(), memberInfo.getIdCardNo(), memberInfo.getSex(),
                memberInfo.getNation(), memberInfo.getEducation(), memberInfo.getDegree(), memberInfo.getNativePlace(),
                memberInfo.getBirthday(), memberInfo.getStats(), memberInfo.getOrigin(), new Date(), memberInfo.getId());
    }

    @Override
    public PartyMember findOne(int id) throws DMException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        return getBean(sql, PartyMember.class, id);
    }

    @Override
    public List<PartyMember> getMembers(MemberCondition condition, Page page) throws DMException {
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
         return getBeanList(sql.toString(), PartyMember.class, objects.toArray());
    }

    @Override
    public void batchInsert(List<PartyMember> memberInfos) throws DMException {
        StringBuilder sql = new StringBuilder();
        List<Object> objects = new ArrayList<>();
        sql.append("INSERT INTO "+ TABLE_NAME +
                " (organizationId, type, memberId, name, idCardNo, sex, nation," +
                " education, degree, nativePlace, birthday, stats, origin, createTime) VALUES ");
        for(PartyMember memberInfo: memberInfos) {
            sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?), ");
            objects.add(memberInfo.getOrganizationId());
            objects.add(memberInfo.getType());
            objects.add(memberInfo.getMemberId());
            objects.add(memberInfo.getName());
            objects.add(memberInfo.getIdCardNo());
            objects.add(memberInfo.getSex());
            objects.add(memberInfo.getNation());
            objects.add(memberInfo.getEducation());
            objects.add(memberInfo.getDegree());
            objects.add(memberInfo.getNativePlace());
            objects.add(memberInfo.getBirthday());
            objects.add(memberInfo.getStats());
            objects.add(memberInfo.getOrigin());
            objects.add(new Date());
        }
        update(sql.toString(), objects.toArray());
    }
}
