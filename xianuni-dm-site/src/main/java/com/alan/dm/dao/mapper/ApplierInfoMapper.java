package com.alan.dm.dao.mapper;

import com.alan.dm.entity.ApplierInfo;
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
}
