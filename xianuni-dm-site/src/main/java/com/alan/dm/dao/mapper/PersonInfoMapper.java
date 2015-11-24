package com.alan.dm.dao.mapper;

import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.PersonCondition;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
public interface PersonInfoMapper {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @
     */
    List<Person> getByCondition(@Param(value="condition")PersonCondition condition, @Param(value="page") Page page);

    /**
     *
     * @param condition
     * @return
     * @
     */
    int countByCondition(@Param(value="condition")PersonCondition condition);

    /**
     *
     * @param person
     */
    void insert(@Param(value = "person")Person person);

    /**
     *
     * @param person
     */
    void delete(@Param(value = "person")Person person);

    /**
     *
     * @param number
     * @return
     */
    Person getByNumber(@Param(value = "number")String number);
    /**
     *
     * @param id
     * @return
     */
    Person getById(@Param(value = "id")int id);

    /**
     *
     * @param personId
     * @param status
     */
    void setStatus(@Param(value = "personId")int personId,@Param(value = "status") int status,@Param(value = "updateTime")Date updateTime);
}
