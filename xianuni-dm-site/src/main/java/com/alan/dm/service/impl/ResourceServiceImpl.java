package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.MediaResourceMapper;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MediaCondition;
import com.alan.dm.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangbinalan on 15/12/2.
 */
@Service(value = "resourceService")
public class ResourceServiceImpl implements IResourceService{

    @Autowired
    private MediaResourceMapper mediaResourceMapper;

    @Override
    public void createResource(MediaResource mediaResource) throws DMException {
        mediaResourceMapper.insert(mediaResource);
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
    public MediaResource getById(int id) throws DMException {
        return mediaResourceMapper.getById(id);
    }

    @Override
    public List<MediaResource> getByCondition(MediaCondition condition, Page page) {
        return mediaResourceMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(MediaCondition condition) {
        return mediaResourceMapper.countByCondition(condition);
    }
}
