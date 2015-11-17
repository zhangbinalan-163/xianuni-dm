package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IApplicationInfoDao;
import com.alan.dm.entity.ApplicationInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "applicationInfoDao")
public class ApplicationInfoDaoImpl extends BaseDao implements IApplicationInfoDao{
    private static final String tableName="tb_party_application";

    @Override
    public ApplicationInfo getByPerson(int personId) throws DMException {
        String sql="select * from "+tableName+" where personId=?";
        return getBean(sql,ApplicationInfo.class,personId);
    }
}
