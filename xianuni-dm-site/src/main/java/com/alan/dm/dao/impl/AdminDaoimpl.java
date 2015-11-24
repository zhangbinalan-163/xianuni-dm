package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IAdminDao;
import com.alan.dm.dao.mapper.AdminMapper;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.AdminCondition;
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
        adminMapper.insert(admin);
    }

    @Override
    public void delete(Admin admin) throws DMException {
        adminMapper.delete(admin);
    }

    @Override
    public void update(Admin admin) throws DMException {
        adminMapper.update(admin);
    }

    @Override
    public Admin getByNumber(String schoolNumber) throws DMException {
        return adminMapper.getByNumber(schoolNumber);
    }

    @Override
    public Admin getById(int id) throws DMException {
        return adminMapper.getById(id);
    }

    @Override
    public List<Admin> getByCondition(AdminCondition condition, Page page) throws DMException {
        return adminMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(AdminCondition condition) throws DMException {
        return adminMapper.countByCondition(condition);
    }
}
