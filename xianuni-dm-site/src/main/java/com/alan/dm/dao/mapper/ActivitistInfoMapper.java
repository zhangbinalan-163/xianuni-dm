package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作MAPPER
 * Created by zhangbinalan on 15/11/16.
 */
public interface ActivitistInfoMapper {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @
     */
    List<ActivitistInfo> getByCondition(@Param(value = "condition") ActivitistInfoCondition condition, @Param(value="page") Page page);

    /**
     *
     * @param condition
     * @return
     * @
     */
    int countByCondition(@Param(value = "condition")ActivitistInfoCondition condition);
    /**
     *
     * @param activitistInfo
     * @throws DMException
     */
    void insert(@Param(value = "activitistInfo")ActivitistInfo activitistInfo);

    /**
     *
     * @param activitistInfo
     * @throws DMException
     */
    void delete(@Param(value = "activitistInfo")ActivitistInfo activitistInfo);

    /**
     *
     * @param activitistId
     * @return
     * @throws DMException
     */
    ActivitistInfo getById(@Param(value = "activitistId")int activitistId);
    /**
     *
     * @param personId
     * @return
     * @throws DMException
     */
    ActivitistInfo getByPersonId(@Param(value="personId") int personId);
}
