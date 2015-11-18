package com.alan.dm.dao.mapper;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.OrgReward;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgRewardCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
public interface OrgRewardMapper {
    /**
     * 查询党组织奖惩记录
     *
     * @throws DMException
     */
    List<OrgReward> getByCondition(@Param(value="condition")OrgRewardCondition condition,@Param(value="page")Page page);

    /**
     * 计算数量
     * @return
     * @throws DMException
     */
    int countByCondition(@Param(value="condition")OrgRewardCondition condition);
}
