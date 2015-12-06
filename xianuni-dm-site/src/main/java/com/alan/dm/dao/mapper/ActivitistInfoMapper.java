package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 操作MAPPER
 * Created by zhangbinalan on 15/11/16.
 */
public interface ActivitistInfoMapper {
    /**
     *
     * @param activitistInfo
     */
    void update(@Param(value = "activitistInfo")ActivitistInfo activitistInfo);

    /**
     *
     * @param activitistInfo
     * @throws DMException
     */
    void insert(@Param(value = "activitistInfo")ActivitistInfo activitistInfo);

    /**
     *
     * @param id
     * @throws DMException
     */
    void delete(@Param(value = "id")int id);

    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    ActivitistInfo getById(@Param(value = "id")int id);
}
