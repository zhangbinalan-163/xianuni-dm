package com.alan.dm.dao;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrgReward;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgRewardCondition;

import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IOrgRewardDao {
    /**
     * 增加奖惩记录信息
     * @param orgReward
     * @throws DMException
     */
    void insert(OrgReward orgReward) throws DMException;

    /**
     *
     * @param orgReward
     * @throws DMException
     */
    void update(OrgReward orgReward) throws DMException;

    /**
     *
     * @param orgReward
     * @throws DMException
     */
    void delete(OrgReward orgReward) throws DMException;

    /**
     * 查询党组织奖惩记录
     *
     * @throws DMException
     */
    List<OrgReward> getByCondition(OrgRewardCondition condition,Page page) throws DMException;

    /**
     * 查询信息
     * @param id
     * @return
     * @throws DMException
     */
    OrgReward getById(int id) throws DMException;

    /**
     * 计算数量
     * @return
     * @throws DMException
     */
    int countByCondition(OrgRewardCondition condition) throws DMException;
}
