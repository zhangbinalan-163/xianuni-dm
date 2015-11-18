package com.alan.dm.dao.mapper;

import com.alan.dm.entity.ActivitistInfo;
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
}
