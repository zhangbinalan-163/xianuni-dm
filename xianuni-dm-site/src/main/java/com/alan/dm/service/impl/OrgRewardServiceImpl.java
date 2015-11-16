package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IOrgRewardDao;
import com.alan.dm.entity.OrgReward;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgRewardCondition;
import com.alan.dm.service.IOrgRewardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "orgRewardService")
public class OrgRewardServiceImpl implements IOrgRewardService{

    @Resource(name = "orgRewardDao")
    private IOrgRewardDao orgRewardDao;

    @Override
    public List<OrgReward> getByCondition(OrgRewardCondition condition, Page page) throws DMException {
        return orgRewardDao.getByCondition(condition,page);
    }

    @Override
    public OrgReward getById(int id) throws DMException {
        return orgRewardDao.getById(id);
    }

    @Override
    public int countByCondition(OrgRewardCondition condition) throws DMException {
        return orgRewardDao.countByCondition(condition);
    }
}
