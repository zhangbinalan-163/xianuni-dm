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

    @Override
    public void deleteBatch(List<Integer> idList) throws DMException {
        for(Integer id:idList){
            OrgReward  reward=new OrgReward();
            reward.setId(id);
            orgRewardDao.delete(reward);
        }
    }

    @Override
    public void addReward(OrgReward orgReward) throws DMException {
        orgRewardDao.insert(orgReward);
    }
}
