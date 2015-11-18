package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.INormalDao;
import com.alan.dm.entity.NormalInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.NormalInfoCondition;
import com.alan.dm.service.INormalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/17.
 */
@Service(value = "normalService")
public class NormalServiceImpl implements INormalService {

    @Resource(name = "normalDao")
    private INormalDao normalDao;

    @Override
    public List<NormalInfo> getByCondition(NormalInfoCondition condition, Page page) throws DMException {
        return normalDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(NormalInfoCondition condition) throws DMException {
        return normalDao.countByCondition(condition);
    }
}
