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
    int insert(Message message) throws DMException;
    void delete(Message message) throws DMException;
    void update(Message message) throws DMException;
    Message findOne(int id) throws DMException;
    List<Message> getMessages(MessageCondition condition, Page page) throws DMException;
}

