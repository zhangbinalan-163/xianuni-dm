package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MediaCondition;

import java.util.List;

/**
 * 媒体资源管理DAO层
 * @Date: 2015-11-15
 * @author: fan
 */
public interface IMediaResourceDao {
    int insert(MediaResource mediaResource) throws DMException;
    void delete(MediaResource mediaResource) throws DMException;
    void update(MediaResource mediaResource) throws DMException;
    MediaResource findOne(int id) throws DMException;
    List<MediaResource> getMedias(MediaCondition condition, Page page) throws DMException;
}
