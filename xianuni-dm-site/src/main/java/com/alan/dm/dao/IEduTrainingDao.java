package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.condition.EduTrainingCondition;

import java.util.List;

/**
 * 教育培训DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IEduTrainingDao {
    int insert(EduTraining trainingInfo) throws DMException;

    void delete(EduTraining trainingInfo) throws DMException;

    void update(EduTraining trainingInfo) throws DMException;

    EduTraining findOne(int id) throws DMException;

    List<EduTraining> getByCondition(EduTrainingCondition condition, Page page);

    int countByCondition(EduTrainingCondition condition);
}
