package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.IntentionInfoCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IntentionInfoMapper {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @
     */
    List<IntentionInfo> getByCondition(@Param(value="condition") IntentionInfoCondition condition,@Param(value="page")  Page page);

    /**
     *
     * @param condition
     * @return
     * @
     */
    int countByCondition(@Param(value="condition") IntentionInfoCondition condition);
}
