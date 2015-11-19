package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IEduTrainingDao;
import com.alan.dm.dao.mapper.EduTrainingMapper;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.condition.EduTrainingCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2015-11-15
 * @author: fan
 */
@Repository(value = "eduTrainingDao")
public class EduTrainingDaoImpl implements IEduTrainingDao {
    @Autowired
    private EduTrainingMapper eduTrainingMapper;

    @Override
    public int insert(EduTraining trainingInfo) throws DMException {
        return eduTrainingMapper.insert(trainingInfo);
    }

    @Override
    public void delete(EduTraining trainingInfo) throws DMException {
        eduTrainingMapper.delete(trainingInfo);
    }

    @Override
    public void update(EduTraining trainingInfo) throws DMException {
        eduTrainingMapper.update(trainingInfo);
    }

    @Override
    public EduTraining findOne(int id) throws DMException {
        return null;
    }

    @Override
    public List<EduTraining> getByCondition(EduTrainingCondition condition, Page page) {
        return eduTrainingMapper.getByCondition(condition, page);
    }

    @Override
    public int countByCondition(EduTrainingCondition condition) {
        return eduTrainingMapper.countByCondition(condition);
    }
}
