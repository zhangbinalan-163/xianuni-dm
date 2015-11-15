package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyTraining;
import com.alan.dm.entity.condition.TrainingCondition;

import java.util.List;

/**
 * 教育培训DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IPartTrainingDao {
    int insert(PartyTraining trainingInfo) throws DMException;

    void delete(PartyTraining trainingInfo) throws DMException;

    void update(PartyTraining trainingInfo) throws DMException;

    PartyTraining findOne(int id) throws DMException;

    List<PartyTraining> getTrainings(TrainingCondition condition, Page page) throws DMException;
}
