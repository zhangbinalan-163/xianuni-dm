package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;

import java.util.List;

/**
 * 党组织业务接口
 * Created by zhangbinalan on 15/11/11.
 */
public interface IOrgnizationService {
    /**
     * 撤销党组织，可以撤销子部门
     * @param orgnization
     * @param withSubOrgs
     * @throws DMException
     */
    void removeOrg(Orgnization orgnization, boolean withSubOrgs) throws DMException;
    /**
     * 删除党组织，可以级联删除子部门
     * @param orgnization
     * @param withSubOrgs
     * @throws DMException
     */
    void deleteOrg(Orgnization orgnization, boolean withSubOrgs) throws DMException;
    /**
     * 根据父组织获取组织列表
     * @param parentOrg
     * @return
     * @throws DMException
     */
    List<Orgnization> getOrgByParent(Orgnization parentOrg,boolean withAllSub) throws DMException;

    /**
     * 获取所有上级组织
     * @param orgnization
     * @return
     * @throws DMException
     */
    List<Orgnization> getParentOrg(Orgnization orgnization) throws DMException;
    /**
     * 根据ID获取党组织信息
     * @param id
     * @return
     * @throws DMException
     */
    Orgnization getOrgById(int id) throws DMException;

    /**
     * 计算直接所属党组织的数量
     * @param parentOrg
     * @return
     * @throws DMException
     */
    int countSubOrg(Orgnization parentOrg) throws DMException;

    /**
     *
     * @param orgnization
     * @throws DMException
     */
    void createOrg(Orgnization orgnization) throws DMException;

    /**
     *
     * @param orgnization
     * @throws DMException
     */
    void updateOrg(Orgnization orgnization) throws DMException;
}
