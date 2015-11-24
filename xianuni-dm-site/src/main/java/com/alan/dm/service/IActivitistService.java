package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.ApplierInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.ActivitistInfoCondition;

import java.util.List;

/**
 * 党员积极分子
 * Created by zhangbinalan on 15/11/16.
 */
public interface IActivitistService {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<ActivitistInfo> getByCondition(ActivitistInfoCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(ActivitistInfoCondition condition)throws DMException;

    /**
     *
     * @param activitistInfo
     * @throws DMException
     */
    void createActivitist(ActivitistInfo activitistInfo) throws DMException;

    /**
     *
     * @param activitistInfo
     * @throws DMException
     */
    void deleteActivitist(ActivitistInfo activitistInfo) throws DMException;

    /**
     *
     * @param activitistId
     * @return
     * @throws DMException
     */
    ActivitistInfo getById(int activitistId) throws DMException;

    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    ActivitistInfo getByPerson(Person person) throws DMException;
}
