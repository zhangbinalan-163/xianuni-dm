package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import com.alan.dm.entity.condition.CommitteeCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface CommitteeInfoDao {
    /**
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<CommitteeInfo> getByCondition(CommitteeCondition condition, Page page) throws DMException;

    /**
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(CommitteeCondition condition) throws DMException;

    /**
     * @throws DMException
     */
    void insert(CommitteeInfo committeeInfo) throws DMException;

    /**
     * @throws DMException
     */
    void delete(CommitteeInfo committeeInfo) throws DMException;

    /**
     * @return
     * @throws DMException
     */
    CommitteeInfo getById(int id) throws DMException;
    /**
     * @return
     * @throws DMException
     */
    CommitteeInfo getByPerson(Person person) throws DMException;
}
