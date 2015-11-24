package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 党组织机构DAO接口
 * Created by zhangbinalan on 15/11/11.
 */
public interface OrgnizationMapper {
    /**
     * 根据父组织查询直属组织
     * @param parentId
     * @return
     * @throws DMException
     */
    List<Orgnization> getByParentOrg(@Param(value="parentId") int parentId);

    /**
     * 计算子组织数量
     * @param parentId
     * @return
     * @throws DMException
     */
    int countSubOrg(@Param(value="parentId") int parentId);

    /**
     * 查询党组织信息
     * @param id
     * @return
     * @throws DMException
     */
    Orgnization getById(@Param(value="id") int id);

    /**
     *
     * @param orgnization
     */
    void update(@Param(value="orgnization")Orgnization orgnization);
    /**
     *
     * @param orgnization
     */
    void insert(@Param(value="orgnization")Orgnization orgnization);
    /**
     *
     * @param orgnization
     */
    void delete(@Param(value="orgnization")Orgnization orgnization);
}
