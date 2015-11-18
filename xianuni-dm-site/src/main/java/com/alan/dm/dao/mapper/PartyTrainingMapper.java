package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyTraining;
import com.alan.dm.entity.condition.TrainingCondition;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface PartyTrainingMapper {
    int insert(PartyTraining trainingInfo) ;

    void delete(PartyTraining trainingInfo) ;

    void update(PartyTraining trainingInfo) ;

    PartyTraining findOne(int id) ;

    List<PartyTraining> getTrainings(TrainingCondition condition, Page page) ;

}
