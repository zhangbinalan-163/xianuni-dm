package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IAdminDao;
import com.alan.dm.dao.mapper.AdminMapper;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Orgnization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 党组织DAO实现
 * Created by zhangbinalan on 15/11/11.
 */
@Repository(value = "adminDao")
public class AdminDaoimpl implements IAdminDao{

    @Autowired
    private AdminMapper adminMapper;

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
        return adminMapper.getByNumber(schoolNumber);
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
