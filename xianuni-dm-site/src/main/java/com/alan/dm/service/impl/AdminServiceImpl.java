package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.AdminMapper;
import com.alan.dm.dao.mapper.PersonInfoMapper;
import com.alan.dm.entity.Admin;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.AdminCondition;
import com.alan.dm.entity.query.PersonCondition;
import com.alan.dm.service.IAdminService;
import com.alan.dm.service.IOrgnizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管理员业务类实现
 * Created by zhangbinalan on 15/11/15.
 */
@Service(value = "adminService")
public class AdminServiceImpl implements IAdminService{

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Resource(name = "orgnizationService")
    private IOrgnizationService orgnizationService;

    @Override
    public Admin getBySchoolNumber(String schoolNumber) throws DMException {
        return adminMapper.getByNumber(schoolNumber);
    }

    @Override
    public List<Person> getCandidatePerson(List<Orgnization> orgnizationList,String number,Page page) throws DMException {
        PersonCondition personCondition=new PersonCondition();
        if(orgnizationList!=null){
            List<Integer> orgIdsList=new ArrayList<Integer>();
            for(Orgnization orgnization:orgnizationList){
                orgIdsList.add(orgnization.getId());
            }
            personCondition.setOrgList(orgIdsList);
        }
        personCondition.setNumber(number);
        List<Person> personList = personInfoMapper.getAdminCandidateList(personCondition, page);
        if(personList==null||personList.size()==0){
            personCondition.setName(number);
            personCondition.setNumber(null);
            personList=personInfoMapper.getAdminCandidateList(personCondition,page);
        }
        return personList;
    }

    @Override
    public void updatePassword(Admin admin) throws DMException {
        admin.setPasswordUpdateTime(new Date());
        adminMapper.updatePass(admin);
    }

    @Override
    public Admin getById(int adminId) throws DMException {
        return adminMapper.getById(adminId);
    }

    @Override
    public void createAdmin(Admin admin) throws DMException {
        admin.setCreateTime(new Date());
        adminMapper.insert(admin);
    }

    @Override
    public void deleteAdmin(Admin admin) throws DMException {
        adminMapper.delete(admin);
    }

    @Override
    public List<Admin> getByCondition(AdminCondition condition, Page page) throws DMException {
        return adminMapper.getByCondition(condition,page);
    }

    @Override
    public int countByCondition(AdminCondition condition) throws DMException {
        return adminMapper.countByCondition(condition);
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
