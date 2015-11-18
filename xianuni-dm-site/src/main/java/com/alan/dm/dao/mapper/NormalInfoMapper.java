package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import com.alan.dm.entity.condition.NormalInfoCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作MAPPER
 * Created by zhangbinalan on 15/11/16.
 */
public interface NormalInfoMapper {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @
     */
    List<NormalInfo> getByCondition(@Param(value="condition") NormalInfoCondition condition, @Param(value="page") Page page);

    /**
     *
     * @param condition
     * @return
     * @
     */
    int countByCondition(@Param(value="condition") NormalInfoCondition condition);
}