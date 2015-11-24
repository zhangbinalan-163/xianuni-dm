package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ApplierInfo;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.ApplierInfoCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预备党员操作MAPPER
 * Created by zhangbinalan on 15/11/16.
 */
public interface ApplierInfoMapper {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @
     */
    List<ApplierInfo> getByCondition(@Param(value="condition") ApplierInfoCondition condition, @Param(value="page") Page page);

    /**
     *
     * @param condition
     * @return
     * @
     */
    int countByCondition(@Param(value="condition") ApplierInfoCondition condition);

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

    /**
     *
     * @param personId
     * @return
     * @throws DMException
     */
    ApplierInfo getByPersonId(@Param(value="personId") int personId);

}
