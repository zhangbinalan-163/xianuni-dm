package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IOrgnizationDao;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.service.IOrgnizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by zhangbinalan on 15/11/11.
 */
@Service(value = "orgnizationService")
public class OrgnizationServiceImpl implements IOrgnizationService {
    private static Logger LOG= LoggerFactory.getLogger(OrgnizationServiceImpl.class);

    @Resource(name = "orgnizationDao")
    private IOrgnizationDao orgnizationDao;

    @Override
    public List<Orgnization> getOrgByParent(Orgnization parentOrg,boolean withAllSub) throws DMException {
        //获取直属的组织
        List<Orgnization> directSubOrgList = orgnizationDao.getByParentOrg(parentOrg.getId());
        if(!withAllSub){
            return directSubOrgList;
        }
        //级联获取所有的组织，确保千万不要会无限循环啊
        List<Orgnization> subOrgList=new ArrayList<Orgnization>();
        if(directSubOrgList!=null){
            subOrgList.addAll(directSubOrgList);
            for(Orgnization subOrg:directSubOrgList){
                if(subOrg.isHasSon()){
                    subOrgList.addAll(getOrgByParent(subOrg, true));
                }
            }
        }
        return subOrgList;
    }

    @Override
    public Orgnization getOrgById(int id) throws DMException {
        return orgnizationDao.getById(id);
    }

    @Override
    public int countSubOrg(Orgnization parentOrg) throws DMException {
        return orgnizationDao.countSubOrg(parentOrg.getId());
    }

    @Override
    public void createOrg(Orgnization orgnization) throws DMException {
        orgnization.setCreateTime(new Date());
        orgnizationDao.insert(orgnization);
    }

    @Override
    public void updateOrg(Orgnization orgnization) throws DMException {
        orgnization.setUpdateTime(new Date());
        orgnizationDao.update(orgnization);
    }

    @Override
    public void deleteOrg(Orgnization orgnization, boolean withSubOrgs) throws DMException {
        if(withSubOrgs){
            //先删除子节点
            List<Orgnization> subOrgs = getOrgByParent(orgnization,false);
            if(subOrgs!=null){
                for(Orgnization subOrg:subOrgs){
                    deleteOrg(subOrg,true);
                }
            }
        }
        //删除党组织
        orgnizationDao.delete(orgnization);
        //todo 其他信息是否需要删除
        LOG.info("delete orgnization success,orgId={},orgName={}", orgnization.getId(), orgnization.getName());
    }

    @Override
    public List<Orgnization> getParentOrg(Orgnization orgnization) throws DMException {
        List<Orgnization> orgnizationList=new ArrayList<>();
        int parent=orgnization.getParent();
        while(parent!=-1){
            Orgnization parentOrg = orgnizationDao.getById(parent);
            orgnizationList.add(parentOrg);
        }
        return orgnizationList;
    }
}
