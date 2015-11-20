package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IMediaResourceDao;
import com.alan.dm.dao.mapper.MediaResourceMapper;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MediaCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 媒体资源管理DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */
@Repository(value = "mediaResourceDao")
public class MediaResourceDaoImpl implements IMediaResourceDao {

    @Autowired
    private MediaResourceMapper mediaResourceMapper;

    @Override
    public int insert(MediaResource mediaResource) throws DMException {
        return mediaResourceMapper.insert(mediaResource);
    }

    @Override
    public void delete(MediaResource mediaResource) throws DMException {
        mediaResourceMapper.delete(mediaResource);
    }

    @Override
    public void update(MediaResource mediaResource) throws DMException {
        mediaResourceMapper.update(mediaResource);
    }

    @Override
    public MediaResource findOne(int id) throws DMException {
        return null;
    }

    @Override
    public List<MediaResource> getByCondition(MediaCondition condition, Page page) {
        return mediaResourceMapper.getByCondition(condition, page);
    }

    @Override
    public int countByCondition(MediaCondition condition) {
        return mediaResourceMapper.countByCondition(condition);
    }
}
