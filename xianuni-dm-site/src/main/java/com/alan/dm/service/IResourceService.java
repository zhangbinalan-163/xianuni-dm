package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.MediaResource;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.MediaCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/12/2.
 */
public interface IResourceService {
    /**
     *
     * @param mediaResource
     * @throws DMException
     */
    void createResource(MediaResource mediaResource) throws DMException;

    /**
     *
     * @param mediaResource
     * @throws DMException
     */
    void delete(MediaResource mediaResource) throws DMException;

    /**
     *
     * @param mediaResource
     * @throws DMException
     */
    void update(MediaResource mediaResource) throws DMException;

    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    MediaResource getById(int id) throws DMException;

    /**
     *
     * @param condition
     * @param page
     * @return
     */
    List<MediaResource> getByCondition(MediaCondition condition, Page page);

    /**
     *
     * @param condition
     * @return
     */
    int countByCondition(MediaCondition condition);
}
