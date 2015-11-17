package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyTraining;
import com.alan.dm.entity.condition.TrainingCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface PartyTrainingMapper {
    int insert(PartyTraining trainingInfo) throws DMException;

    void delete(PartyTraining trainingInfo) throws DMException;

    void update(PartyTraining trainingInfo) throws DMException;

    PartyTraining findOne(int id) throws DMException;

    List<PartyTraining> getTrainings(@Param(value = "con") TrainingCondition condition,
                                     @Param(value = "page") Page page) throws DMException;

}
