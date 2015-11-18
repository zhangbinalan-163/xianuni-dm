package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IMessageDao;
import com.alan.dm.entity.Message;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MessageCondition;

import java.util.List;

/**
 * 消息通知管理DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */
public class MessageDaoImpl implements IMessageDao {

    @Override
    public int insert(Message message) throws DMException {
        return 0;
    }

    @Override
    public void delete(Message message) throws DMException {
    }

    @Override
    public void update(Message message) throws DMException {
    }

    @Override
    public Message findOne(int id) throws DMException {
        return null;
    }
    @Override
    public List<Message> getMessages(MessageCondition condition, Page page) throws DMException {
       return null;
    }
}
