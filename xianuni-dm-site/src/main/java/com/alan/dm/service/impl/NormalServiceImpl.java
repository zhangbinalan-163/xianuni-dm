package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.NormalInfoMapper;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.service.INormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "normalService")
public class NormalServiceImpl implements INormalService {
    @Autowired
    private NormalInfoMapper normalInfoMapper;

    @Override
    public void createNormal(NormalInfo normalInfo) throws DMException {
        normalInfo.setCreateTime(new Date());
        normalInfoMapper.insert(normalInfo);
    }

    @Override
    public void deleteNormal(NormalInfo normalInfo) throws DMException {
        normalInfoMapper.delete(normalInfo);
    }

    @Override
    public NormalInfo getById(int normalId) throws DMException {
        return normalInfoMapper.getById(normalId);
    }

    @Override
    public int countByOrgWithTime(List<Integer> orgIdlist, Date start, Date end) throws DMException {
        return normalInfoMapper.countByOrgWithTime(orgIdlist,start,end);
    }

    @Override
    public void updateNormal(NormalInfo normalInfo) throws DMException {
        normalInfo.setUpdateTime(new Date());
        normalInfoMapper.update(normalInfo);
    }
}
