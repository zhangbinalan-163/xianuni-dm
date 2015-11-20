package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IEduTrainingDao;
import com.alan.dm.dao.IMediaResourceDao;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.StudyRecord;
import com.alan.dm.entity.condition.MediaCondition;
import com.alan.dm.entity.condition.StudyRecordCondition;
import com.alan.dm.entity.condition.EduTrainingCondition;
import com.alan.dm.service.IEduTrainingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教育培训业务逻辑
 * @Date: 2015-11-17
 * @author: fan
 */
@Service(value = "eduTrainingService")
public class EduTrainingServiceImpl implements IEduTrainingService {

    @Resource(name = "eduTrainingDao")
    private IEduTrainingDao eduTrainingDao;

    @Resource(name = "mediaResourceDao")
    private IMediaResourceDao mediaResourceDao;

    @Override
    public int addTraining(EduTraining trainingInfo) throws DMException {
        return eduTrainingDao.insert(trainingInfo);
    }

    @Override
    public int addMediaResource(MediaResource mediaResource) throws DMException {
        return mediaResourceDao.insert(mediaResource);
    }

    @Override
    public void deleteTraining(EduTraining eduTraining) throws DMException {
        eduTrainingDao.delete(eduTraining);
    }

    @Override
    public void deleteMediaResource(MediaResource mediaResource) throws DMException {
        mediaResourceDao.delete(mediaResource);
    }

    @Override
    public void modifyTraining(EduTraining trainingInfo) throws DMException {
        eduTrainingDao.update(trainingInfo);
    }

    @Override
    public void modifyMediaResource(MediaResource mediaResource) throws DMException {
        mediaResourceDao.update(mediaResource);
    }

    @Override
    public List<EduTraining> getTraining(EduTrainingCondition condition, Page page) throws DMException {
        return eduTrainingDao.getByCondition(condition, page);
    }

    @Override
    public int countTrain(EduTrainingCondition condition) throws DMException {
        return eduTrainingDao.countByCondition(condition);
    }

    @Override
    public List<MediaResource> getMediaResource(MediaCondition condition, Page page) throws DMException {
        return mediaResourceDao.getByCondition(condition, page);
    }

    @Override
    public int countMediaResource(MediaCondition condition) throws DMException {
        return mediaResourceDao.countByCondition(condition);
    }
}
