package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.ActivitistInfoCondition;
import com.alan.dm.entity.condition.AdminCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface AdminMapper {
    /**
     * 根据学工号查询管理员
     * @param schoolNumber
     * @return
     * @
     */
    Admin getByNumber(@Param(value = "number")String schoolNumber);

    /**
     * 插入管理员
     * @param admin
     * @throws DMException
     */
    void insert(@Param(value = "admin")Admin admin);

    /**
     * 删除管理员
     * @param admin
     * @throws DMException
     */
    void delete(@Param(value = "admin")Admin admin);

    /**
     * 修改管理员
     * @param admin
     * @throws DMException
     */
    void update(@Param(value = "admin")Admin admin);

    /**
     *
     * @param condition
     * @param page
     * @return
     * @
     */
    List<Admin> getByCondition(@Param(value = "condition") AdminCondition condition, @Param(value="page") Page page);

    /**
     *
     * @param condition
     * @return
     * @
     */
    int countByCondition(@Param(value = "condition")AdminCondition condition);

    /**
     * 根据ID查询管理员
     * @param id
     * @return
     * @throws DMException
     */
    Admin getById(@Param(value = "adminId")int id);
}
