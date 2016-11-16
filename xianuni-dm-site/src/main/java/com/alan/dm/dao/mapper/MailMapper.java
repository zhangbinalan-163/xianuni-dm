package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.MailInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.query.MailCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @Date: 2015-11-16
 * @author: zhangbinaan
 */
public interface MailMapper {

    /**
     *
     * @param id
     */
    void delete(@Param(value = "id") int id);
    /**
     *
     * @param person
     * @param page
     * @return
     */
    List<MailInfo> getByPerson(@Param(value = "person") Person person, @Param(value = "page") Page page);

    /**
     *
     * @param person
     * @return
     */
    int countByPerson(@Param(value = "person") Person person);
    /**
     *
     * @param id
     * @return
     */
    MailInfo getById(@Param(value = "id") Integer id);

    /**
     *
     * @param mailInfo
     */
    void insert(@Param(value = "mailInfo") MailInfo mailInfo);
    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(@Param(value = "condition") MailCondition condition);
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<MailInfo> getByCondition(@Param(value = "condition") MailCondition condition,@Param(value = "page") Page page);
}
