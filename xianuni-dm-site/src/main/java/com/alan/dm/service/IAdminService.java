package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.Admin;

/**
 * 管理员业务类
 * Created by zhangbinalan on 15/11/15.
 */
public interface IAdminService {
    /**
     * 根据学工号查询管理员信息
     * @param schoolNumber
     * @return
     * @throws DMException
     */
    Admin getBySchoolNumber(String schoolNumber) throws DMException;
}
