package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IIntentionInfoDao;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.IntentionInfoCondition;
import com.alan.dm.service.IIntentionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "intentionService")
public class IntentionServiceImpl implements IIntentionService {

    @Resource(name = "intentionInfoDao")
    private IIntentionInfoDao intentionDao;

    @Override
    public List<IntentionInfo> getByCondition(IntentionInfoCondition condition, Page page) throws DMException {
        return intentionDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(IntentionInfoCondition condition) throws DMException {
        return intentionDao.countByCondition(condition);
    }
}
