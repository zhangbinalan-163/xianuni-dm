package com.alan.dm.dao.mapper;

import com.alan.dm.entity.Page;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.condition.EduTrainingCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface EduTrainingMapper {
    int insert(EduTraining trainingInfo) ;

    void delete(EduTraining trainingInfo) ;

    void update(EduTraining trainingInfo) ;

    EduTraining findOne(int id) ;

    List<EduTraining> getByCondition(@Param(value = "condition") EduTrainingCondition condition,
                                    @Param(value = "page") Page page);

    int countByCondition(@Param(value = "condition") EduTrainingCondition condition);

}
