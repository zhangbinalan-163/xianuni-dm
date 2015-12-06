package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.StudyRecord;
import com.alan.dm.entity.condition.MediaCondition;
import com.alan.dm.entity.condition.StudyRecordCondition;
import com.alan.dm.entity.condition.EduTrainingCondition;

import java.util.List;

/**
 * 教育培训
 * @Date: 2015-11-14
 * @author: fan
 */
public interface IEduTrainingService {
    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    EduTraining getById(int id) throws DMException;

    /**
     * 新增教育培训
     * @param trainingInfo
     * @return
     * @throws DMException
     */
    void addTraining(EduTraining trainingInfo) throws DMException;

    /**
     *
     * @param id
     * @throws DMException
     */
    void deleteTraining(EduTraining id) throws DMException;

    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<EduTraining> getTrainingByCondtion(EduTrainingCondition condition, Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countTrainByCondtion(EduTrainingCondition condition) throws DMException;
}
