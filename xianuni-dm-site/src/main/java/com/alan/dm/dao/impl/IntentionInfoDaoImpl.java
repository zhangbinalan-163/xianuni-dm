package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IActivitistInfoDao;
import com.alan.dm.dao.IIntentionDao;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.IntentionInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "intentionInfoDao")
public class IntentionInfoDaoImpl extends BaseDao implements IIntentionDao {

    private static final String tableName="tb_development_object";

    @Override
    public IntentionInfo getByPerson(int personId) throws DMException {
        String sql="select * from "+tableName+" where personId=?";
        return getBean(sql,IntentionInfo.class,personId);
    }
}
