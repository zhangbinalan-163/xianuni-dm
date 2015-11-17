package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IPartyDuesDao;
import com.alan.dm.entity.DateRange;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyDuesPay;
import com.alan.dm.entity.PartyDuesStatis;
import com.alan.dm.entity.condition.PartyDuesCondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 党费管理DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */
public class PartyDuesDaoImpl extends BaseDao implements IPartyDuesDao {
    private static final String TABLE_NAME = "party_dues_pay";

    @Override
    public int insert(PartyDuesPay partyDuesPay) throws DMException {
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(partyMemberId, payStartDate, payEndDate, payDate, createTime) " +
                "VALUES (?,?,?,?,?)";
        return update(sql, partyDuesPay.getPersonId(),
                partyDuesPay.getPayStartTime(), partyDuesPay.getPayEndTime(), partyDuesPay.getPayDate(),
                new Date());
    }

    @Override
    public void delete(PartyDuesPay partyDuesPay) throws DMException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        update(sql, partyDuesPay.getId());
    }

    @Override
    public void update(PartyDuesPay partyDuesPay) throws DMException {
        String sql = "UPDATE " + TABLE_NAME + " SET " +
                "payStartDate=?, payEndDate=?, payDate=?, updateTime=? " +
                "WHERE id=?";
        update(sql, partyDuesPay.getPayStartTime(), partyDuesPay.getPayEndTime(),
                partyDuesPay.getPayDate(), new Date(), partyDuesPay.getId());
    }

    @Override
    public PartyDuesPay findOne(int id) throws DMException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        return getBean(sql, PartyDuesPay.class, id);
    }

    @Override
    public List<PartyDuesPay> getPartyDuesPay(PartyDuesCondition condition, Page page) throws DMException {
        // 是否需要
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + " WHERE ");
        int[] ids = condition.getPartyMemberIds();
        if(ids != null && ids.length > 0) {
            sql.append("organizationId IN(");
            for(int i = 0; i < ids.length; i++) {
                if(i == ids.length - 1) {
                    sql.append("?) ");
                } else {
                    sql.append("?, ");
                }
                objects.add(ids[i]);
            }
        }
        if(condition.getRange() != null) {
            sql.append("AND payDate BETWEEN ? AND ? ");
            objects.add(condition.getRange().getStartDate());
            objects.add(condition.getRange().getEndDate());
        }
        setLimit(page.getCurrent(), page.getSize());
        return getBeanList(sql.toString(), PartyDuesPay.class, objects.toArray());
    }

    @Override
    public List<PartyDuesStatis> statisPartyDuesPay(DateRange range, Page page) throws DMException {
        // TODO
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT organizationId, COUNT(*) AS partyMembersNum FROM " + TABLE_NAME + " ");
        if(range != null) {
            sql.append("WHERE payDate BETWEEN ? AND ? ");
            objects.add(range.getStartDate());
            objects.add(range.getEndDate());
        }
        sql.append("GROUP BY organizationId ");
        setLimit(page.getCurrent(), page.getSize());
        return getBeanList(sql.toString(), PartyDuesStatis.class, objects.toArray());
    }
}
