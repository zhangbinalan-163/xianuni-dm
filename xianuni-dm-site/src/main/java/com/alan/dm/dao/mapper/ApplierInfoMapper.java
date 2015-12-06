package com.alan.dm.dao.mapper;

import com.alan.dm.entity.ApplierInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 预备党员操作MAPPER
 * Created by zhangbinalan on 15/11/16.
 */
public interface ApplierInfoMapper {
    /**
     *
     * @param applierInfo
     */
    void update(@Param(value="applier")ApplierInfo applierInfo);

    /**
     *
     * @param applierInfo
     */
    void insert(@Param(value="applier")ApplierInfo applierInfo);

    /**
     *
     * @param applierId
     */
    void delete(@Param(value="applierId")int applierId);

    /**
     *
     * @param applierId
     * @return
     */
    ApplierInfo getById(@Param(value="applierId")int applierId);
}
