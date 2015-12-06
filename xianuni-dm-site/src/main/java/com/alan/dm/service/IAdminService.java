package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.AdminCondition;

import java.util.List;

/**
 * 管理员业务类
 * Created by zhangbinalan on 15/11/15.
 */
public interface IAdminService {
    /**
     *
     * @param admin
     * @throws DMException
     */
    void updatePassword(Admin admin) throws DMException;
    /**
     *
     * @param orgnizationList
     * @return
     * @throws DMException
     */
    List<Person> getCandidatePerson(List<Orgnization> orgnizationList,String number,Page page) throws DMException;
    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    List<Admin> getByCondition(AdminCondition condition,Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(AdminCondition condition) throws DMException;

    /**
     * 根据学工号查询管理员信息
     * @param schoolNumber
     * @return
     * @throws DMException
     */
    Admin getBySchoolNumber(String schoolNumber) throws DMException;

    /**
     *
     * @param adminId
     * @return
     * @throws DMException
     */
    Admin getById(int adminId) throws DMException;

    /**
     *
     * @param admin
     * @throws DMException
     */
    void createAdmin(Admin admin) throws DMException;

    /**
     *
     * @param admin
     * @throws DMException
     */
    void deleteAdmin(Admin admin) throws DMException;

    /**
     *
     * @param admin
     * @param orgnization
     * @return
     * @throws DMException
     */
    boolean hasRight(Admin admin,Orgnization orgnization) throws DMException;
}
