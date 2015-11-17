package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IIntentionDao;
import com.alan.dm.dao.INormalDao;
import com.alan.dm.entity.IntentionInfo;
import com.alan.dm.entity.NormalInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "normalDao")
public class NormalDaoImpl extends BaseDao implements INormalDao {

    private static final String tableName="tb_party_full";

    @Override
    public NormalInfo getByPerson(int personId) throws DMException {
        String sql="select * from "+tableName+" where personId=?";
        return getBean(sql,NormalInfo.class,personId);
    }
}
