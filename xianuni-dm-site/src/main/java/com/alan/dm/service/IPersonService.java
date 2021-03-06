package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.query.PersonCondition;
import com.alan.dm.entity.query.PersonResult;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
public interface IPersonService {
    /**
     * 统计指定时间内成为党员的数量
     * @param orgnization
     * @param source
     * @param statusList
     * @param startDate
     * @param endDate
     * @return
     * @throws DMException
     */
    int countBySourceAndTime(Orgnization orgnization,int source,List<Integer> statusList,Date startDate,Date endDate,boolean withAllSub) throws DMException;
    /**
     *
     * @param person
     * @throws DMException
     */
    void updatePassword(Person person) throws DMException;
    /**
     * 统计某个党组织特定状态的党员的数量,包含下属组织
     * @param orgnization
     * @param statusList
     * @return
     * @throws DMException
     */
    int countByOrgWithStatus(Orgnization orgnization,
                             List<Integer> statusList,List<Integer> sexList,List<Integer> nationList,
                             List<Integer> degreeList,Date startBirth,Date endBirth,
                             boolean withAllSub) throws DMException;

    /**
     * 根据条件分页查询人员信息，会带回响应的信息
     * @param condition
     * @param page
     * @return
     */
    List<Person> getByCondition(PersonCondition condition,Page page) throws DMException;

    /**
     * 根据条件分页查询人员信息
     * @param condition
     * @param page
     * @return
     */
    List<Person> getByCondition(PersonCondition condition,Page page,PersonResult result) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(PersonCondition condition) throws DMException;

    /**
     *
     * @param personId
     * @return
     * @throws DMException
     */
    Person getById(int personId)throws DMException;
    /**
     *
     * @param number
     * @return
     * @throws DMException
     */
    Person getByNumber(String number) throws DMException;

    /**
     *
     * @param person
     * @throws DMException
     */
    void createPerson(Person person) throws DMException;

    /**
     *
     * @param person
     * @throws DMException
     */
    void deletePerson(Person person) throws DMException;
    /**
     *
     * @param person
     * @throws DMException
     */
    void updatePerson(Person person) throws DMException;
}
