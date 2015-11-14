package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.MediaInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PartyTrainingInfo;
import com.alan.dm.entity.StudyRecordInfo;
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
    int addTraining(PartyTrainingInfo trainingInfo) throws DMException;

    /**
     * 新增媒体资源
     * @param mediaInfo
     * @return
     * @throws DMException
     */
    int addMediaResource(MediaInfo mediaInfo) throws DMException;

    /**
     * 新增学习记录
     * @param studyRecordInfo
     * @return
     * @throws DMException
     */
    int addStudyRecord(StudyRecordInfo studyRecordInfo) throws DMException;

    void deleteTraining(int id) throws DMException;

    void deleteMediaResource(int id) throws DMException;

    void deleteStudyRecord(int id) throws DMException;

    void modifyTraining(PartyTrainingInfo trainingInfo) throws DMException;

    void modifyMediaResource(MediaInfo mediaInfo) throws DMException;

    void modifyStudyRecord(StudyRecordInfo studyRecordInfo) throws DMException;

    List<PartyTrainingInfo> getTraining(TrainingCondition condition, Page page) throws DMException;

    List<MediaInfo> getMediaResource(MediaCondition condition, Page page) throws DMException;

    List<StudyRecordInfo> getStudyRecord(StudyRecordCondition condition, Page page) throws DMException;
}
