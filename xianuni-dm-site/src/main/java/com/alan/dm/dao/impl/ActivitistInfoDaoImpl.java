package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IActivitistInfoDao;
import com.alan.dm.entity.ActivitistInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "activitistInfoDao")
public class ActivitistInfoDaoImpl extends BaseDao implements IActivitistInfoDao {

    private static final String tableName="tb_party_activist";

    @Override
    public ActivitistInfo getByPerson(int personId) throws DMException {
        String sql="select * from "+tableName+" where personId=?";
        return getBean(sql,ActivitistInfo.class,personId);
    }
}
