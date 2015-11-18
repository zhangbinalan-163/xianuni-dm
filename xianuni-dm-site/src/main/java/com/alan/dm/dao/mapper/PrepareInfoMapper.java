package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.PrepareInfo;
import com.alan.dm.entity.condition.PersonCondition;
import com.alan.dm.entity.condition.PrepareInfoCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预备党员操作MAPPER
 * Created by zhangbinalan on 15/11/16.
 */
public interface PrepareInfoMapper {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @
     */
    List<PrepareInfo> getByCondition(@Param(value="condition")PrepareInfoCondition condition,@Param(value="page") Page page) ;

    /**
     *
     * @param condition
     * @return
     * @
     */
    int countByCondition(@Param(value="condition")PrepareInfoCondition condition);
}
