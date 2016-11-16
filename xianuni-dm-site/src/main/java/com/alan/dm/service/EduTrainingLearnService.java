package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.TrainLearn;
import com.alan.dm.entity.condition.EduTrainingCondition;
import com.alan.dm.entity.query.TrainLearnCondition;

import java.util.List;

/**
 * 教育培训
 * @Date: 2015-11-14
 * @author: fan
 */
public interface EduTrainingLearnService {
    /**
     *
     * @param person
     * @param training
     * @return
     * @throws DMException
     */
    TrainLearn getByPersonWithTrain(Person person,EduTraining training) throws DMException;
    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    TrainLearn getById(int id) throws DMException;

    /**
     * 新增教育培训新的
     * @param trainLearn
     * @return
     * @throws DMException
     */
    void createLearn(TrainLearn trainLearn) throws DMException;

    /**
     *
     * @param trainLearn
     * @throws DMException
     */
    void updateLearn(TrainLearn trainLearn) throws DMException;

    /**
     *
     * @param trainLearn
     * @throws DMException
     */
    void deleteLearn(TrainLearn trainLearn) throws DMException;

    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<TrainLearn> getLearnByCondtion(TrainLearnCondition condition, Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countLearnByCondtion(TrainLearnCondition condition) throws DMException;
}
