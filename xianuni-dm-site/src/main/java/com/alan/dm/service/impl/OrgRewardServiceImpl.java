package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.OrgRewardMapper;
import com.alan.dm.entity.OrgReward;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgRewardCondition;
import com.alan.dm.service.IOrgRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "orgRewardService")
public class OrgRewardServiceImpl implements IOrgRewardService{

    @Autowired
    private OrgRewardMapper orgRewardMapper;

    @Override
    public List<OrgReward> getByCondition(OrgRewardCondition condition, Page page) throws DMException {
        return orgRewardMapper.getByCondition(condition,page);
    }

    @Override
    public OrgReward getById(int id) throws DMException {
        return orgRewardMapper.getById(id);
    }

    @Override
    public int countByCondition(OrgRewardCondition condition) throws DMException {
        return orgRewardMapper.countByCondition(condition);
    }

    @Override
    public void deleteBatch(List<Integer> idList) throws DMException {
        for(Integer id:idList){
            orgRewardMapper.delete(id);
        }
    }

    @Override
    public void addReward(OrgReward orgReward) throws DMException {
        orgReward.setCreateTime(new Date());
        orgRewardMapper.insert(orgReward);
    }
}
