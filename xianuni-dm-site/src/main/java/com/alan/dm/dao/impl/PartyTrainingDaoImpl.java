package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IPartTrainingDao;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyTraining;
import com.alan.dm.entity.condition.TrainingCondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2015-11-15
 * @author: fan
 */
public class PartyTrainingDaoImpl extends BaseDao implements IPartTrainingDao {
    private static final String TABLE_NAME = "party_training";

    @Override
    public int insert(PartyTraining trainingInfo) throws DMException {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (title, trainingType, organizationId, startTime, endTime, period, trainingObject," +
                " content, harvest, opinion, createTime)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        return update(sql, trainingInfo.getTitle(), trainingInfo.getTrainingType(), trainingInfo.getOrganizationId(),
                 trainingInfo.getStartTime(), trainingInfo.getEndTime(), trainingInfo.getPeriod(),
                 trainingInfo.getTrainingObject(), trainingInfo.getContent(), trainingInfo.getHarvest(),
                 trainingInfo.getOpinion(), new Date());
    }

    @Override
    public void delete(PartyTraining trainingInfo) throws DMException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        update(sql, trainingInfo.getId());
    }

    @Override
    public void update(PartyTraining trainingInfo) throws DMException {
        String sql = "UPDATE " + TABLE_NAME + " SET " +
                "title=?, trainingType=?, organizationId=?, startTime=?, endTime=?, " +
                "period=?, trainingObject=?, content=?, harvest=?, opinion=?, updateTime=? " +
                "WHERE id=?";
        update(sql, trainingInfo.getTitle(), trainingInfo.getTrainingType(), trainingInfo.getOrganizationId(),
                trainingInfo.getStartTime(), trainingInfo.getEndTime(), trainingInfo.getPeriod(),
                trainingInfo.getTrainingObject(), trainingInfo.getContent(), trainingInfo.getHarvest(),
                trainingInfo.getOpinion(), new Date(), trainingInfo.getId());
    }

    @Override
    public PartyTraining findOne(int id) throws DMException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        return getBean(sql, PartyTraining.class, id);
    }

    @Override
    public List<PartyTraining> getTrainings(TrainingCondition condition, Page page) throws DMException {
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + " WHERE ");
        if(condition.getTrainingType() != 0) {
            sql.append("trainingType=? ");
            objects.add(condition.getTrainingType());
        }
        if(condition.getOrganizationIds() != null && condition.getOrganizationIds().length > 0) {
            sql.append("AND organizationId IN (");
            int length = condition.getOrganizationIds().length;
            for(int i = 0; i < length; i++) {
                if(i == length - 1) {
                    sql.append("?");
                } else {
                    sql.append("?,");
                }
                objects.add(condition.getOrganizationIds()[i]);
            }
        }
        if(condition.getRange() != null) {
            sql.append("AND startTime BETWEEN ? AND ?");
            objects.add(condition.getRange().getStartDate());
            objects.add(condition.getRange().getEndDate());
        }
        return getBeanList(sql.toString(), PartyTraining.class, objects.toArray());
    }
}
