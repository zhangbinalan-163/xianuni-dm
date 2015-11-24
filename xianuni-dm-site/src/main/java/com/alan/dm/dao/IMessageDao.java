package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Message;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MessageCondition;

import java.util.List;

/**
 * 消息通知管理DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IMessageDao {
    /**
     *
     * @param message
     * @return
     * @throws DMException
     */
    void insert(Message message) throws DMException;

    /**
     *
     * @param message
     * @throws DMException
     */
    void delete(Message message) throws DMException;

    /**
     *
     * @param message
     * @throws DMException
     */
    void update(Message message) throws DMException;

    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    Message getById(int id) throws DMException;

    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<Message> getByCondition(MessageCondition condition, Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(MessageCondition condition) throws DMException;
}

