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
     * 新增教育培训
     * @param trainingInfo
     * @return
     * @throws DMException
     */
    int addTraining(EduTraining trainingInfo) throws DMException;

    /**
     * 新增媒体资源
     * @param mediaResource
     * @return
     * @throws DMException
     */
    int addMediaResource(MediaResource mediaResource) throws DMException;

    void deleteTraining(EduTraining id) throws DMException;

    void deleteMediaResource(MediaResource id) throws DMException;

    void modifyTraining(EduTraining trainingInfo) throws DMException;

    void modifyMediaResource(MediaResource mediaResource) throws DMException;

    List<EduTraining> getTraining(EduTrainingCondition condition, Page page) throws DMException;

    int countTrain(EduTrainingCondition condition) throws DMException;

    List<MediaResource> getMediaResource(MediaCondition condition, Page page) throws DMException;

    int countMediaResource(MediaCondition condition) throws DMException;
}
