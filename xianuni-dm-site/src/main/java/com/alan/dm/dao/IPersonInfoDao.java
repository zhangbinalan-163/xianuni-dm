package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.*;
import com.alan.dm.entity.condition.PersonCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IPersonInfoDao {
    /**
     *
     * @param orgnizationList
     * @param number
     * @param page
     * @return
     */
    List<Person> getCommitteeCandidateList(List<Orgnization> orgnizationList,String number,Page page);
    /**
     *
     * @param orgnizationList
     * @param page
     * @return
     */
    List<Person> getAdminCandidateList(List<Orgnization> orgnizationList,String number,Page page);
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException˙
     */
    List<Person> getByCondition(PersonCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(PersonCondition condition) throws DMException;

    /**
     *
     * @param person
     * @throws DMException
     */
    void insertPerson(Person person) throws DMException;

    /**
     *
     * @param number
     * @return
     * @throws DMException
     */
    Person getByNumber(String number) throws DMException;
    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    Person getById(int id) throws DMException;


    /**
     *
     * @param status
     * @throws DMException
     */
    void updateStatus(Person person,PersonStatus status) throws DMException;

    /**
     *
     * @param person
     * @throws DMException
     */
    void delete(Person person) throws DMException;
}

