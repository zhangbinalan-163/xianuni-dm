package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.IntentionInfoMapper;
import com.alan.dm.entity.*;
import com.alan.dm.service.IIntentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "intentionService")
public class IntentionServiceImpl implements IIntentionService {

    @Autowired
    private IntentionInfoMapper intentionInfoMapper;

    @Override
    public void createIntention(IntentionInfo intentionInfo) throws DMException {
        intentionInfo.setCreateTime(new Date());
        intentionInfoMapper.insert(intentionInfo);
    }

    @Override
    public void deleteIntention(IntentionInfo intentionInfo) throws DMException {
        intentionInfoMapper.delete(intentionInfo);
    }

    @Override
    public IntentionInfo getById(int intentionId) throws DMException {
        return intentionInfoMapper.getById(intentionId);
    }

}
