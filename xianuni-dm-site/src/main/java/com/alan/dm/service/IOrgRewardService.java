package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrgReward;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgRewardCondition;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
public interface IOrgRewardService {
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
