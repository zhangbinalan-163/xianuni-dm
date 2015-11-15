package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IMediaResourceDao;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MediaCondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 媒体资源管理DAO实现
 * @Date: 2015-11-15
 * @author: fan
 */
public class MediaResourceDaoImpl extends BaseDao implements IMediaResourceDao {
    private static final String TABLE_NAME = "media_resource";

    @Override
    public int insert(MediaResource mediaResource) throws DMException {
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(name, type, forbidden, uploadTime, description, resourcePath," +
                " createTime) " +
                "VALUES (?,?,?,?,?,?,?)";
        return update(sql, mediaResource.getName(), mediaResource.getType(), mediaResource.isForbidden(),
                mediaResource.getUploadDate(), mediaResource.getDescription(), mediaResource.getResourcePath(),
                new Date());
    }

    @Override
    public void delete(MediaResource mediaResource) throws DMException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        update(sql, mediaResource.getId());
    }

    @Override
    public void update(MediaResource mediaResource) throws DMException {
        String sql = "UPDATE " + TABLE_NAME + " SET " +
                "name=?, type=?, forbidden=?, uploadTime=?, description=?, resourcePath=?, updateTime=? " +
                "WHERE id=?";
        update(sql, mediaResource.getName(), mediaResource.getType(), mediaResource.isForbidden(),
                mediaResource.getUploadDate(), mediaResource.getDescription(), mediaResource.getResourcePath(),
                new Date(), mediaResource.getId());
    }

    @Override
    public MediaResource findOne(int id) throws DMException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        return getBean(sql, MediaResource.class, id);
    }

    @Override
    public List<MediaResource> getMedias(MediaCondition condition, Page page) throws DMException {
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + " WHERE ");
        if(condition.getMediaType() != 0) {
            sql.append("type=?");
            objects.add(condition.getMediaType());
        }
        if(!StringUtils.isEmpty(condition.getMediaName())) {
            sql.append("name LIKE %?%");
            objects.add(condition.getMediaName());
        }
        setLimit(page.getCurrent(), page.getSize());
        return getBeanList(sql.toString(), MediaResource.class, objects.toArray());
    }
}
