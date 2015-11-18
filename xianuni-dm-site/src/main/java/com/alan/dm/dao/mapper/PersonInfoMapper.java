package com.alan.dm.dao.mapper;

import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.PersonCondition;
import org.apache.ibatis.annotations.Param;

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
}
