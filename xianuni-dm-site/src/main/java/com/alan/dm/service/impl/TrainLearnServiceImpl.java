package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.TrainLearnMapper;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.TrainLearn;
import com.alan.dm.entity.query.TrainLearnCondition;
import com.alan.dm.service.EduTrainingLearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangbinalan on 15/12/6.
 */
@Service
public class TrainLearnServiceImpl implements EduTrainingLearnService {
    @Autowired
    private TrainLearnMapper trainLearnMapper;

    @Override
    public void updateLearn(TrainLearn trainLearn) throws DMException {
        trainLearnMapper.update(trainLearn);
    }

    @Override
    public int countLearnByCondtion(TrainLearnCondition condition) throws DMException {
        return trainLearnMapper.countByCondition(condition);
    }

    @Override
    public TrainLearn getByPersonWithTrain(Person person, EduTraining training) throws DMException {
        return trainLearnMapper.getByPersonAndTrain(person.getId(),training.getId());
    }

    @Override
    public List<TrainLearn> getLearnByCondtion(TrainLearnCondition condition, Page page) throws DMException {
        return trainLearnMapper.getByCondition(condition,page);
    }

    @Override
    public void deleteLearn(TrainLearn trainLearn) throws DMException {
        trainLearnMapper.delete(trainLearn);
    }

    @Override
    public void createLearn(TrainLearn trainLearn) throws DMException {
        trainLearnMapper.insert(trainLearn);
    }

    @Override
    public TrainLearn getById(int id) throws DMException {
        return trainLearnMapper.getById(id);
    }

}
