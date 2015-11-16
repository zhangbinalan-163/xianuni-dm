package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.DateRange;
import com.alan.dm.entity.Message;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MessageCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface MessageMapper {
    int insert(Message message) throws DMException;
    void delete(Message message) throws DMException;
    void update(Message message) throws DMException;
    Message findOne(int id) throws DMException;
    List<Message> getMessages(@Param(value = "organizationId") int organizationId,
                              @Param(value = "title") String title,
                              @Param(value = "range") DateRange range,
                              @Param(value = "page") Page page) throws DMException;
}
