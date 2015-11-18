package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;

import java.util.List;

/**
 * 党组织机构DAO接口
 * Created by zhangbinalan on 15/11/11.
 */
public interface IOrgnizationDao {
    /**
     * 插入党组织数据
     * @param orgnization
     * @throws DMException
     */
    void insert(Orgnization orgnization) throws DMException;

    /**
     * 删除党组织数据
     * @param orgnization
     * @throws DMException
     */
    void delete(Orgnization orgnization) throws DMException;

    /**
     * 修改党组织数据
     * @param orgnization
     * @throws DMException
     */
    void update(Orgnization orgnization) throws DMException;

    /**
     * 根据父组织查询直属组织
     * @param parentId
     * @return
     * @throws DMException
     */
    List<Orgnization> getByParentOrg(int parentId,Page page) throws DMException;

    /**
     * 查询党组织信息
     * @param id
     * @return
     * @throws DMException
     */
    Orgnization getById(int id) throws DMException;

    /**
     * 计算子组织数量
     * @param parentId
     * @return
     * @throws DMException
     */
    int countSubOrg(int parentId) throws DMException;
}
