package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IActivitistInfoDao;
import com.alan.dm.dao.IPrepareDao;
import com.alan.dm.entity.ActivitistInfo;
import com.alan.dm.entity.condition.PrepareInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "prepareInfoDao")
public class PrepareInfoDaoImpl extends BaseDao implements IPrepareDao {

    private static final String tableName="tb_party_probationary";

    @Override
    public PrepareInfo getByPerson(int personId) throws DMException {
        String sql="select * from "+tableName+" where personId=?";
        return getBean(sql,PrepareInfo.class,personId);
    }
}
