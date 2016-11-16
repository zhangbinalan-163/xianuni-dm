package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.RelationTransferInfo;
import com.alan.dm.entity.condition.RelationTransferCondition;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/18.
 */
public interface IRelationTransferService {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<RelationTransferInfo> getByCondition(RelationTransferCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(RelationTransferCondition condition) throws DMException;

    /**
     *
     * @param relationTransferInfo
     * @throws DMException
     */
    void createTransfer(RelationTransferInfo relationTransferInfo) throws DMException;

    /**
     *
     * @param relationTransferInfo
     * @throws DMException
     */
    void updateTransfer(RelationTransferInfo relationTransferInfo) throws DMException;
    /**
     *
     * @param relationTransferInfo
     * @throws DMException
     */
    void deleteTransfer(RelationTransferInfo relationTransferInfo) throws DMException;

    /**
     *
     * @param id
     * @return
     */
    RelationTransferInfo getById(int id) throws DMException;
}
