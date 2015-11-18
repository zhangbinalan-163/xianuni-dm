package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IPartTrainingDao;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyTraining;
import com.alan.dm.entity.StudyRecord;
import com.alan.dm.entity.condition.MediaCondition;
import com.alan.dm.entity.condition.StudyRecordCondition;
import com.alan.dm.entity.condition.TrainingCondition;
import com.alan.dm.service.ITrainingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教育培训业务逻辑
 * @Date: 2015-11-17
 * @author: fan
 */
@Service(value = "trainingService")
public class TrainingServiceImpl implements ITrainingService {

    @Resource(name = "partyTrainingDao")
    private IPartTrainingDao partTrainingDao;

    @Override
    public int addTraining(PartyTraining trainingInfo) throws DMException {
        return 0;
    }

    @Override
    public int addMediaResource(MediaResource mediaResource) throws DMException {
        return 0;
    }

    @Override
    public int addStudyRecord(StudyRecord studyRecord) throws DMException {
        return 0;
    }

    @Override
    public void deleteTraining(int id) throws DMException {

    }

    @Override
    public void deleteMediaResource(int id) throws DMException {

    }

    @Override
    public void deleteStudyRecord(int id) throws DMException {

    }

    @Override
    public void modifyTraining(PartyTraining trainingInfo) throws DMException {

    }

    @Override
    public void modifyMediaResource(MediaResource mediaResource) throws DMException {

    }

    @Override
    public void modifyStudyRecord(StudyRecord studyRecord) throws DMException {

    }

    @Override
    public List<PartyTraining> getTraining(TrainingCondition condition, Page page) throws DMException {
        return partTrainingDao.getTrainings(condition, page);
    }

    @Override
    public List<MediaResource> getMediaResource(MediaCondition condition, Page page) throws DMException {
        return null;
    }

    @Override
    public List<StudyRecord> getStudyRecord(StudyRecordCondition condition, Page page) throws DMException {
        return null;
    }
}
