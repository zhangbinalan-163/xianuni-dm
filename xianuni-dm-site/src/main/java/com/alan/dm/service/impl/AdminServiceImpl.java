package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IAdminDao;
import com.alan.dm.dao.IPersonInfoDao;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.AdminCondition;
import com.alan.dm.service.IAdminService;
import com.alan.dm.service.IOrgnizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 管理员业务类实现
 * Created by zhangbinalan on 15/11/15.
 */
@Service(value = "adminService")
public class AdminServiceImpl implements IAdminService{
    @Resource(name = "adminDao")
    private IAdminDao adminDao;

    @Resource(name = "personInfoDao")
    private IPersonInfoDao personInfoDao;

    @Resource(name = "orgnizationService")
    private IOrgnizationService orgnizationService;

    @Override
    public Admin getBySchoolNumber(String schoolNumber) throws DMException {
        return adminDao.getByNumber(schoolNumber);
    }

    @Override
    public List<Person> getCandidatePerson(List<Orgnization> orgnizationList,String number,Page page) throws DMException {
        return personInfoDao.getAdminCandidateList(orgnizationList,number,page);
    }

    @Override
    public Admin getById(int adminId) throws DMException {
        return adminDao.getById(adminId);
    }

    @Override
    public void createAdmin(Admin admin) throws DMException {
        admin.setCreateTime(new Date());
        adminDao.insert(admin);
    }

    @Override
    public void deleteAdmin(Admin admin) throws DMException {
        adminDao.delete(admin);
    }

    @Override
    public List<Admin> getByCondition(AdminCondition condition, Page page) throws DMException {
        return adminDao.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(AdminCondition condition) throws DMException {
        return adminDao.countByCondition(condition);
    }

    @Override
    public boolean hasRight(Admin admin, Orgnization orgnization) throws DMException {
        if(admin==null){
            return false;
        }
        if(admin.getType()==Admin.SYSTEM_ADMIN){
            return true;
        }
        if(admin.getType()==Admin.ORG_ADMIN&&admin.getOrgId()==orgnization.getId()){
            return true;
        }
        List<Orgnization> orgnizationList=orgnizationService.getOrgByParent(orgnization,true);
        if(orgnizationList!=null){
            for(Orgnization subOrg:orgnizationList){
                if(subOrg.getId()==admin.getOrgId()){
                    return true;
                }
            }
        }
        return false;
    }
}
