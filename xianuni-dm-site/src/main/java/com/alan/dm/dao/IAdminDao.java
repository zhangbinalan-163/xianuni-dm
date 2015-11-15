package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Orgnization;

import java.util.List;

/**
 * 管理员
 * Created by zhangbinalan on 15/11/15.
 */
public interface IAdminDao {
    /**
     * 插入管理员
     * @param admin
     * @throws DMException
     */
    void insert(Admin admin) throws DMException;

    /**
     * 删除管理员
     * @param admin
     * @throws DMException
     */
    void delete(Admin admin) throws DMException;

    /**
     * 修改管理员
     * @param admin
     * @throws DMException
     */
    void update(Admin admin) throws DMException;

    /**
     * 根据学工号查询管理员
     * @param schoolNumber
     * @return
     * @throws DMException
     */
    Admin getByNumber(String schoolNumber) throws DMException;

    /**
     * 查询指定部门的管理员信息
     * @param orgnization
     * @return
     * @throws DMException
     */
    List<Admin> getByOrg(Orgnization orgnization) throws DMException;

    /**
     * 根据ID查询管理员
     * @param id
     * @return
     * @throws DMException
     */
    Admin getById(int id) throws DMException;
}
