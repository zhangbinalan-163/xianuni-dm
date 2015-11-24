package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.NormalInfo;
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
    /**
     *
     * @param personId
     * @return
     * @throws DMException
     */
    IntentionInfo getByPersonId(@Param(value="personId") int personId);
}
