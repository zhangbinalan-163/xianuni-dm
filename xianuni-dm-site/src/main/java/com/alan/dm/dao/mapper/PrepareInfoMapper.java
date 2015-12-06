package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.PrepareInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * 预备党员操作MAPPER
 * Created by zhangbinalan on 15/11/16.
 */
public interface PrepareInfoMapper {
    /**
     *
     * @param prepareInfo
     */
    void update(@Param(value="prepareInfo")PrepareInfo prepareInfo);
    /**
     *
     * @param prepareInfo
     * @throws DMException
     */
    void insert(@Param(value="prepareInfo")PrepareInfo prepareInfo);

    /**
     *
     * @param prepareInfo
     * @throws DMException
     */
    void delete(@Param(value="prepareInfo")PrepareInfo prepareInfo);

    /**
     *
     * @param prepareId
     * @return
     * @throws DMException
     */
    PrepareInfo getById(@Param(value="prepareId")int prepareId);

    /**
     * 统计组织内在指定时间段内成为预备党员的数量
     * @param orgIdList
     * @param start
     * @param end
     * @return
     */
    int countByOrgWithTime(@Param(value="orgIdList")List<Integer> orgIdList,@Param(value="start")Date start,@Param(value="end")Date end);
}
