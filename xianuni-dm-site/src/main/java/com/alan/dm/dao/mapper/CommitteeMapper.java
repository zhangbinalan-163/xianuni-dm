package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.CommitteeInfo;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.CommitteeCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/26.
 */
public interface CommitteeMapper {
    /**
     *
     * @param orgnizationList
     * @param number
     * @param page
     * @return
     * @throws DMException
     */
    List<Person> getCandidatePerson(@Param("orgList") List<Orgnization> orgnizationList,@Param("number") String number,@Param("page") Page page) throws DMException;
    /**
     *
     * @param committeeInfo
     */
    void insert(@Param(value = "committeeInfo")CommitteeInfo committeeInfo);

    /**
     *
     * @param committeeInfo
     */
    void delete(@Param(value = "committeeInfo")CommitteeInfo committeeInfo);
    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    CommitteeInfo getById(@Param(value = "id")int id);

    /**
     *
     * @param person
     * @return
     */
    CommitteeInfo getByPerson(@Param(value = "person")Person person);

    /**
     *
     * @param condition
     * @param page
     * @return
     * @
     */
    List<CommitteeInfo> getByCondition(@Param(value = "condition") CommitteeCondition condition, @Param(value="page") Page page);

    /**
     *
     * @param condition
     * @return
     * @
     */
    int countByCondition(@Param(value = "condition")CommitteeCondition condition);
}

