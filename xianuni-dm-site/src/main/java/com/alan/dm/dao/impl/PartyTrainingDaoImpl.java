package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPartTrainingDao;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyTraining;
import com.alan.dm.entity.condition.TrainingCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2015-11-15
 * @author: fan
 */
@Repository(value = "partyTrainingDao")
public class PartyTrainingDaoImpl implements IPartTrainingDao {
    @Override
    public int insert(PartyTraining trainingInfo) throws DMException {
        return 0;
    }

    @Override
    public void delete(PartyTraining trainingInfo) throws DMException {

    }

    @Override
    public void update(PartyTraining trainingInfo) throws DMException {

    }

    @Override
    public PartyTraining findOne(int id) throws DMException {
        return null;
    }

    @Override
    public List<PartyTraining> getTrainings(TrainingCondition condition, Page page) throws DMException {
        return null;
    }
}
