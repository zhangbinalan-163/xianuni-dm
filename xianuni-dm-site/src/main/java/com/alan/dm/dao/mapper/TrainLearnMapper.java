package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.TrainLearn;
import com.alan.dm.entity.query.PersonCondition;
import com.alan.dm.entity.query.TrainLearnCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface TrainLearnMapper {
    /**
     *
     * @param personId
     * @param trainId
     * @return
     */
    TrainLearn getByPersonAndTrain(@Param(value="personId")int personId,@Param(value="trainId")int trainId);
    /**
     *
     * @param condition
     * @param page
     * @return
     * @
     */
    List<TrainLearn> getByCondition(@Param(value="condition")TrainLearnCondition condition, @Param(value="page") Page page);

    /**
     *
     * @param condition
     * @return
     * @
     */
    int countByCondition(@Param(value="condition")TrainLearnCondition condition);


    /**
     *
     * @param trainLearn
     * @throws DMException
     */
    void insert(@Param(value = "trainLearn") TrainLearn trainLearn) throws DMException;

    /**
     *
     * @param trainLearn
     * @throws DMException
     */
    void update(@Param(value = "trainLearn") TrainLearn trainLearn) throws DMException;

    /**
     *
     * @param trainLearn
     * @throws DMException
     */
    void delete(@Param(value = "trainLearn") TrainLearn trainLearn) throws DMException;

    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    TrainLearn getById(@Param(value = "id") int id) throws DMException;
}
