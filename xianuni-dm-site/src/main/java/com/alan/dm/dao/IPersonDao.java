package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.PersonCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IPersonDao {
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<Person> getByCondition(PersonCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(PersonCondition condition) throws DMException;
}
