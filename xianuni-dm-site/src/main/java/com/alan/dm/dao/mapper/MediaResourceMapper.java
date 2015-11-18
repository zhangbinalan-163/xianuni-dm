package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MediaCondition;

import java.util.List;

/**
 * @Date: 2015-11-16
 * @author: fan
 */
public interface MediaResourceMapper {
    int insert(MediaResource mediaResource);
    void delete(MediaResource mediaResource);
    void update(MediaResource mediaResource);
    MediaResource findOne(int id);
    List<MediaResource> getMedias(MediaCondition condition, Page page);

}
