package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
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
    /**
     *
     * @param message
     * @return
     * @throws DMException
     */
    void insert(@Param(value = "message")Message message);

    /**
     *
     * @param message
     * @throws DMException
     */
    void delete(@Param(value = "message")Message message);

    /**
     *
     * @param message
     * @throws DMException
     */
    void update(@Param(value = "message")Message message);

    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    Message getById(@Param(value = "id")int id);

    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<Message> getByCondition(@Param(value = "condition")MessageCondition condition,@Param(value = "page")Page page);

    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(@Param(value = "condition")MessageCondition condition);
}
