package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IMediaResourceDao;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MediaCondition;

import java.util.List;

/**
 * 媒体资源管理DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */
public class MediaResourceDaoImpl implements IMediaResourceDao {

    @Override
    public int insert(MediaResource mediaResource) throws DMException {
        return 0;
    }

    @Override
    public void delete(MediaResource mediaResource) throws DMException {
    }

    @Override
    public void update(MediaResource mediaResource) throws DMException {
    }

    @Override
    public MediaResource findOne(int id) throws DMException {
        return null;
    }

    @Override
    public List<MediaResource> getMedias(MediaCondition condition, Page page) throws DMException {
       return null;
    }
}
