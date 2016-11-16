package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.RelationTransferInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.RelationTransferCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface RelationTransferMapper {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<RelationTransferInfo> getByCondition(@Param(value="condition")RelationTransferCondition condition,@Param(value="page") Page page);

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(@Param(value="condition")RelationTransferCondition condition);

    /**
     *
     * @param relationTransferInfo
     * @throws DMException
     */
    void insert(@Param(value="relationTransferInfo")RelationTransferInfo relationTransferInfo);

    /**
     *
     * @param relationTransferInfo
     */
    void update(@Param(value="relationTransferInfo")RelationTransferInfo relationTransferInfo);
    /**
     *
     * @param relationTransferInfo
     * @throws DMException
     */
    void delete(@Param(value="relationTransferInfo")RelationTransferInfo relationTransferInfo);

    /**
     *
     * @param id
     * @return
     */
    RelationTransferInfo getById(@Param(value="id")int id);

}
