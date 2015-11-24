package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IMessageDao;
import com.alan.dm.dao.mapper.AdminMapper;
import com.alan.dm.dao.mapper.MessageMapper;
import com.alan.dm.entity.Message;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MessageCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息通知管理DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */

@Repository(value = "messageDaoImpl")
public class MessageDaoImpl implements IMessageDao {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void insert(Message message) throws DMException {
        messageMapper.insert(message);
    }

    @Override
    public void delete(Message message) throws DMException {
        messageMapper.delete(message);
    }

    @Override
    public void update(Message message) throws DMException {
        messageMapper.update(message);
    }

    @Override
    public Message getById(int id) throws DMException {
        return messageMapper.getById(id);
    }

    @Override
    public List<Message> getByCondition(MessageCondition condition, Page page) throws DMException {
        return messageMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(MessageCondition condition) throws DMException {
        return messageMapper.countByCondition(condition);
    }
}
