package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Message;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MessageCondition;

import java.util.List;

/**
 * 消息通知管理
 * @Date: 2015-11-14
 * @author: fan
 */
public interface IMessageService {

    /**
     * 新增消息
     * @param message
     * @return
     * @throws DMException
     */
    int addMessage(Message message) throws DMException;

    /**
     * 删除消息
     * @param id
     * @throws DMException
     */
    void deleteMessage(int id) throws DMException;

    /**
     * 修改消息
     * @param message
     * @throws DMException
     */
    void modifyMessage(Message message) throws DMException;

    /**
     * 获取消息
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<Message> getMessage(MessageCondition condition, Page page) throws DMException;
}
