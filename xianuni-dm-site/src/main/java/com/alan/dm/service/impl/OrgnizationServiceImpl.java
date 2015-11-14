package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IOrgnizationDao;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import com.alan.dm.service.IOrgnizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<Orgnization> getOrgByParent(Orgnization parentOrg,Page page) throws DMException {
        int pageNumber=-1;
        int size=-1;
        if(page!=null){
            pageNumber=page.getCurrent();
            size=page.getSize();
        }
        return orgnizationDao.getByParentOrg(parentOrg.getId(),pageNumber,size);
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
    public void deleteOrg(Orgnization orgnization, boolean withSubOrgs) throws DMException {
        if(withSubOrgs){
            //先删除子节点
            List<Orgnization> subOrgs = getOrgByParent(orgnization, null);
            if(subOrgs!=null){
                for(Orgnization subOrg:subOrgs){
                    if(countSubOrg(subOrg)>0){
                        //删除子节点
                        deleteOrg(subOrg,true);
                    }
                }
            }
        }
        //删除党组织
        orgnizationDao.delete(orgnization);
        LOG.info("delete orgnization success,orgId={},orgName={}", orgnization.getId(), orgnization.getName());
        //设置父节点是否还为空
        Orgnization parentOrg=new Orgnization();
        parentOrg.setId(orgnization.getParent());
        if(countSubOrg(parentOrg)==0){
            //修改 TODO
        }
    }
}
