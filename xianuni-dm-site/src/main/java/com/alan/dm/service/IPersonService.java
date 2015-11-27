package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.query.PersonCondition;
import com.alan.dm.entity.query.PersonResult;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
public interface IPersonService {

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
