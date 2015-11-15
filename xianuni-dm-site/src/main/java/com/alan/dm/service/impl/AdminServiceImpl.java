package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IAdminDao;
import com.alan.dm.entity.Admin;
import com.alan.dm.service.IAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 管理员业务类实现
 * Created by zhangbinalan on 15/11/15.
 */
@Service(value = "adminService")
public class AdminServiceImpl implements IAdminService{

    @Resource(name = "adminDao")
    private IAdminDao adminDao;

    @Override
    public Admin getBySchoolNumber(String schoolNumber) throws DMException {
        return adminDao.getByNumber(schoolNumber);
    }
}
