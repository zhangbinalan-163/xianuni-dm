package com.alan.dm.dao.mapper;

import com.alan.dm.entity.Admin;
import org.apache.ibatis.annotations.Param;

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
}
