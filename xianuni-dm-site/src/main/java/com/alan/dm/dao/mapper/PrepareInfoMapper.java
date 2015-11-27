package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.PrepareInfo;
import org.apache.ibatis.annotations.Param;


/**
 * 预备党员操作MAPPER
 * Created by zhangbinalan on 15/11/16.
 */
public interface PrepareInfoMapper {
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

}
