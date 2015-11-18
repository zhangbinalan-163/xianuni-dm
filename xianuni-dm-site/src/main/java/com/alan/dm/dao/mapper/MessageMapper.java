package com.alan.dm.dao.mapper;

import com.alan.dm.entity.Message;
import com.alan.dm.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface MessageMapper {
    int insert(Message message);
    void delete(Message message);
    void update(Message message);
    Message findOne(int id);
    List<Message> getMessages(@Param(value = "organizationId") int organizationId,
                              @Param(value = "title") String title,
                              @Param(value = "page") Page page);
}
