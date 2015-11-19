package com.alan.dm.dao.mapper;

import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MediaCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface MediaResourceMapper {
    int insert(@Param(value = "media") MediaResource mediaResource);
    void delete(MediaResource mediaResource);
    void update(@Param(value = "media") MediaResource mediaResource);
    MediaResource findOne(int id);

    List<MediaResource> getByCondition(@Param(value = "condition") MediaCondition condition,
                                       @Param(value = "page") Page page);

    int countByCondition(@Param(value = "condition") MediaCondition condition);

}
