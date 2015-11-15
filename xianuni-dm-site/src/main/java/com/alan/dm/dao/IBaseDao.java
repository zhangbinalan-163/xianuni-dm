package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;

import java.util.List;

/**
 * 基本DAO接口
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IBaseDao<T> {

    /**
     * 插入数据
     * @param entity
     * @throws DMException
     */
    void insert(T entity) throws DMException;

    /**
     * 删除数据
     * @param id
     * @throws DMException
     */
    void delete(int id) throws DMException;

    /**
     * 修改数据
     * @param entity
     * @throws DMException
     */
    void update(T entity) throws DMException;

    /**
     * 根据ID查找数据
     * @param id
     * @return
     * @throws DMException
     */
    T findOne(int id) throws DMException;
}
