package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.IntentionInfo;
import org.apache.ibatis.annotations.Param;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IntentionInfoMapper {

    /**
     *
     * @param intentionInfo
     * @throws DMException
     */
    void insert(@Param(value="intentionInfo")IntentionInfo intentionInfo) throws DMException;

    /**
     *
     * @param intentionInfo
     * @throws DMException
     */
    void delete(@Param(value="intentionInfo")IntentionInfo intentionInfo) throws DMException;

    /**
     *
     * @param intentionId
     * @return
     * @throws DMException
     */
    IntentionInfo getById(@Param(value="intentionId")int intentionId) throws DMException;
}
