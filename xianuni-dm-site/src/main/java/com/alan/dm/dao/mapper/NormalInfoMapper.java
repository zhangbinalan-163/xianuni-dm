package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.NormalInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 操作MAPPER
 * Created by zhangbinalan on 15/11/16.
 */
public interface NormalInfoMapper {
    /**
     *
     * @param normalInfo
     * @throws DMException
     */
    void insert(@Param(value="normalInfo")NormalInfo normalInfo);

    /**
     *
     * @param normalInfo
     * @throws DMException
     */
    void delete(@Param(value="normalInfo") NormalInfo normalInfo);

    /**
     *
     * @param normalId
     * @return
     * @throws DMException
     */
    NormalInfo getById(@Param(value="normalId") int normalId);
}
