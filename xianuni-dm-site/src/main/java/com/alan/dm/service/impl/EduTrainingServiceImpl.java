package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.EduTrainingMapper;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.Resource;
import com.alan.dm.entity.condition.EduTrainingCondition;
import com.alan.dm.service.IEduTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教育培训业务逻辑
 * @Date: 2015-11-17
 * @author: fan
 */
@Service(value = "eduTrainingService")
public class EduTrainingServiceImpl implements IEduTrainingService {

    @Autowired
    private EduTrainingMapper eduTrainingMapper;

    @Override
    public EduTraining getById(int id) throws DMException {
        return eduTrainingMapper.getById(id);
    }

    @Override
    public void addTraining(EduTraining trainingInfo) throws DMException {
        //培训信息增加
        eduTrainingMapper.insert(trainingInfo);
        //附件信息增加
        List<Resource> resourceList = trainingInfo.getResourceList();
        if(resourceList!=null){
            for(Resource resource:resourceList){
                resource.setTrainId(trainingInfo.getId());
                eduTrainingMapper.insertResource(resource);
            }
        }
    }

    @Override
    public void deleteTraining(EduTraining trainingInfo) throws DMException {
        eduTrainingMapper.delete(trainingInfo);
    }

    @Override
    public List<EduTraining> getTrainingByCondtion(EduTrainingCondition condition, Page page) throws DMException {
        return eduTrainingMapper.getByCondition(condition,page);
    }

    @Override
    public int countTrainByCondtion(EduTrainingCondition condition) throws DMException {
        return eduTrainingMapper.countByCondition(condition);
    }

}
