package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import com.alan.dm.entity.condition.CommitteeCondition;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
public interface ICommitteeService {
    /**
     *
     * @param orgnizationList
     * @param number
     * @param page
     * @return
     * @throws DMException
     */
    List<Person> getCandidatePerson(List<Orgnization> orgnizationList,String number,Page page) throws DMException;
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<CommitteeInfo> getByCondition(CommitteeCondition condition, Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(CommitteeCondition condition)throws DMException;

    /**
     *
     * @param activitistInfo
     * @throws DMException
     */
    void createCommitteeInfo(CommitteeInfo activitistInfo) throws DMException;

    /**
     *
     * @param activitistInfo
     * @throws DMException
     */
    void deleteCommitteeInfo(CommitteeInfo activitistInfo) throws DMException;

    /**
     *
     * @param activitistId
     * @return
     * @throws DMException
     */
    CommitteeInfo getById(int activitistId) throws DMException;

    /**
     *
     * @param person
     * @return
     * @throws DMException
     */
    CommitteeInfo getByPerson(Person person) throws DMException;
}
