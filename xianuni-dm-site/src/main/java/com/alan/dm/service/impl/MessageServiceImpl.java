package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IAdminDao;
import com.alan.dm.dao.IMessageDao;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Message;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.AdminCondition;
import com.alan.dm.entity.condition.MessageCondition;
import com.alan.dm.service.IAdminService;
import com.alan.dm.service.IMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 管理员业务类实现
 * Created by zhangbinalan on 15/11/15.
 */
@Service(value = "messageService")
public class MessageServiceImpl implements IMessageService{

    @Resource(name = "messageDaoImpl")
    private IMessageDao messageDao;

    @Override
    public void createMessage(Message message) throws DMException {
        message.setCreateTime(new Date());
        messageDao.insert(message);
    }

    @Override
    public void deleteMessage(Message message) throws DMException {
        messageDao.delete(message);
    }

    @Override
    public void updateMessage(Message message) throws DMException {
        messageDao.update(message);
    }

    @Override
    public List<Message> getMessageByCondtition(MessageCondition condition, Page page) throws DMException {
        return messageDao.getByCondition(condition,page);
    }

    @Override
    public int countMessageByCondtition(MessageCondition condition) throws DMException {
        return messageDao.countByCondition(condition);
    }
}
