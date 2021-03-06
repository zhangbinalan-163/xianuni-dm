package com.alan.dm.dao.mapper;

import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.query.PersonCondition;
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
     * @param orgIdList
     * @param sourceList
     * @param statusList
     * @param startDate
     * @param endDate
     * @return
     */
    int countBySource(@Param(value="orgList")List<Integer> orgIdList,
                      @Param(value="sourceList")List<Integer> sourceList,
                      @Param(value="statusList")List<Integer> statusList,
                      @Param(value="startDate")Date startDate,
                      @Param(value="endDate") Date endDate);
    /**
     *
     * @param condition
     * @param page
     * @return
     */
    List<Person> getCommitteeCandidateList(@Param(value="condition")PersonCondition condition, @Param(value="page") Page page);

    /**
     *
     * 获取部门里面可以成为管理员的账号信息
     * @param condition
     * @param page
     * @return
     */
    List<Person> getAdminCandidateList(@Param(value="condition")PersonCondition condition, @Param(value="page") Page page);

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
     * @param person
     */
    void update(@Param(value = "person")Person person);
    /**
     *
     * @param person
     */
    void updatePass(@Param(value = "person")Person person);

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
}
