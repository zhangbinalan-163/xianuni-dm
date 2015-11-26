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
     *
     * @param messageId
     * @return
     */
    Message getById(int messageId) throws DMException;
    /**
     * 新增消息
     * @param message
     * @return
     * @throws DMException
     */
    void createMessage(Message message) throws DMException;

    /**
     * 删除消息
     * @param message
     * @throws DMException
     */
    void deleteMessage(Message message) throws DMException;

    /**
     * 修改消息
     * @param message
     * @throws DMException
     */
    void updateMessage(Message message) throws DMException;

    /**
     * 获取消息
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<Message> getMessageByCondtition(MessageCondition condition, Page page) throws DMException;

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countMessageByCondtition(MessageCondition condition) throws DMException;
}
