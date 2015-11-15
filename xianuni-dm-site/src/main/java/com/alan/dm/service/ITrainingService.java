package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyTraining;
import com.alan.dm.entity.StudyRecord;
import com.alan.dm.entity.condition.MediaCondition;
import com.alan.dm.entity.condition.StudyRecordCondition;
import com.alan.dm.entity.condition.TrainingCondition;

import java.util.List;

/**
 * 教育培训
 * @Date: 2015-11-14
 * @author: fan
 */
public interface ITrainingService {

    /**
     * 新增党员培训
     * @param trainingInfo
     * @return
     * @throws DMException
     */
    int addTraining(PartyTraining trainingInfo) throws DMException;

    /**
     * 新增媒体资源
     * @param mediaResource
     * @return
     * @throws DMException
     */
    int addMediaResource(MediaResource mediaResource) throws DMException;

    /**
     * 新增学习记录
     * @param studyRecord
     * @return
     * @throws DMException
     */
    int addStudyRecord(StudyRecord studyRecord) throws DMException;

    void deleteTraining(int id) throws DMException;

    void deleteMediaResource(int id) throws DMException;

    void deleteStudyRecord(int id) throws DMException;

    void modifyTraining(PartyTraining trainingInfo) throws DMException;

    void modifyMediaResource(MediaResource mediaResource) throws DMException;

    void modifyStudyRecord(StudyRecord studyRecord) throws DMException;

    List<PartyTraining> getTraining(TrainingCondition condition, Page page) throws DMException;

    List<MediaResource> getMediaResource(MediaCondition condition, Page page) throws DMException;

    List<StudyRecord> getStudyRecord(StudyRecordCondition condition, Page page) throws DMException;
}
