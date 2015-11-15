package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IMessageDao;
import com.alan.dm.entity.Message;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MessageCondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息通知管理DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */
public class MessageDaoImpl extends BaseDao implements IMessageDao {
    private static final String TABLE_NAME = "tb_message";

    @Override
    public int insert(Message message) throws DMException {
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(messageTime, type, title, content, createTime) " +
                "VALUES (?,?,?,?,?)";
        return update(sql, message.getMessageTime(), message.getTitle(), message.getType(),
                message.getContent(), new Date());
    }

    @Override
    public void delete(Message message) throws DMException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        update(sql, message.getId());
    }

    @Override
    public void update(Message message) throws DMException {
        String sql = "UPDATE " + TABLE_NAME + " SET " +
                "messageTime=?, type=?, title=?, content=?, updateTime=? " +
                "WHERE id=?";
        update(sql, message.getMessageTime(), message.getTitle(), message.getType(),
                message.getContent(), new Date(), message.getId());
    }

    @Override
    public Message findOne(int id) throws DMException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        return getBean(sql, Message.class, id);
    }

    @Override
    public List<Message> getMessages(MessageCondition condition, Page page) throws DMException {
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + " WHERE ");
        if(condition.getOrganizationId() != 0) {
            sql.append("organizationId=? ");
            objects.add(condition.getOrganizationId());
        }
        if(!StringUtils.isEmpty(condition.getTitle())) {
            sql.append("AND title=? ");
            objects.add(condition.getTitle());
        }
        if(condition.getRange() != null) {
            sql.append("AND messageTime BETWEEN ? AND ?");
            objects.add(condition.getRange().getStartDate());
            objects.add(condition.getRange().getEndDate());
        }
        setLimit(page.getCurrent(), page.getSize());
        return getBeanList(sql.toString(), Message.class, objects.toArray());
    }
}
