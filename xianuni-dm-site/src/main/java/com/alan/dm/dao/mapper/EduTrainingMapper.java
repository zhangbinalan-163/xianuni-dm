package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.EduTraining;
import com.alan.dm.entity.Resource;
import com.alan.dm.entity.condition.EduTrainingCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface EduTrainingMapper {
    /**
     *
     * @param resource
     * @throws DMException
     */
    void insertResource(@Param(value = "resource") Resource resource) throws DMException;
    /**
     *
     * @param trainingInfo
     * @return
     */
    int insert(@Param(value = "training") EduTraining trainingInfo) ;

    /**
     *
     * @param trainingInfo
     */
    void delete(@Param(value = "training")EduTraining trainingInfo) ;

    /**
     *
     * @param trainingInfo
     */
    void update(@Param(value = "training") EduTraining trainingInfo) ;

    /**
     *
     * @param id
     * @return
     */
    EduTraining getById(@Param(value = "id") int id) ;

    /**
     *
     * @param condition
     * @param page
     * @return
     */
    List<EduTraining> getByCondition(@Param(value = "condition") EduTrainingCondition condition,
                                    @Param(value = "page") Page page);

    /**
     *
     * @param condition
     * @return
     */
    int countByCondition(@Param(value = "condition") EduTrainingCondition condition);
}
