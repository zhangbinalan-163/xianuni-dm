package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IOrgRewardDao;
import com.alan.dm.dao.mapper.OrgRewardMapper;
import com.alan.dm.entity.OrgReward;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgRewardCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "orgRewardDao")
public class OrgRewardDaoImpl implements IOrgRewardDao{
    @Autowired
    private OrgRewardMapper orgRewardMapper;

    @Override
    public void insert(OrgReward orgReward) throws DMException {
        orgRewardMapper.insert(orgReward);
    }

    @Override
    public void update(OrgReward orgReward) throws DMException {

    }

    @Override
    public void delete(OrgReward orgReward) throws DMException {
        orgRewardMapper.delete(orgReward.getId());
    }

    @Override
    public List<OrgReward> getByCondition(OrgRewardCondition condition, Page page) throws DMException {
        return orgRewardMapper.getByCondition(condition,page);
    }

    @Override
    public OrgReward getById(int id) throws DMException {
        return null;
    }

    @Override
    public int countByCondition(OrgRewardCondition condition) throws DMException {
        return orgRewardMapper.countByCondition(condition);
    }
}
