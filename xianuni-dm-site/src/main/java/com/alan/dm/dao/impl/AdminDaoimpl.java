package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IAdminDao;
import com.alan.dm.dao.IOrgnizationDao;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Orgnization;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 党组织DAO实现
 * Created by zhangbinalan on 15/11/11.
 */
@Repository(value = "adminDao")
public class AdminDaoimpl extends BaseDao implements IAdminDao{

    private static final String tableName="tb_admin";

    @Override
    public void insert(Admin admin) throws DMException {

    }

    @Override
    public void delete(Admin admin) throws DMException {

    }

    @Override
    public void update(Admin admin) throws DMException {

    }

    @Override
    public Admin getByNumber(String schoolNumber) throws DMException {
        String sql = "select * from "+tableName+" where schoolNumber=?";
        return getBean(sql,Admin.class,schoolNumber);
    }

    @Override
    public List<Admin> getByOrg(Orgnization orgnization) throws DMException {
        return null;
    }

    @Override
    public Admin getById(int id) throws DMException {
        return null;
    }
}
