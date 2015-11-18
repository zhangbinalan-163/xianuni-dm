package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.IOrgnizationDao;
import com.alan.dm.dao.mapper.OrgnizationMapper;
import com.alan.dm.entity.Orgnization;
import com.alan.dm.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 党组织DAO实现
 * Created by zhangbinalan on 15/11/11.
 */
@Repository(value = "orgnizationDao")
public class OrgnizationDaoimpl implements IOrgnizationDao{

    @Autowired
    private OrgnizationMapper orgnizationMapper;

    @Override
    public void insert(Orgnization orgnization) throws DMException {

    }

    @Override
    public void delete(Orgnization orgnization) throws DMException {

    }
    @Override
    public void update(Orgnization orgnization) throws DMException {
        //TODO
    }

    @Override
    public List<Orgnization> getByParentOrg(int parentId,Page page) throws DMException {
        return orgnizationMapper.getByParentOrg(parentId,page);
    }

    @Override
    public Orgnization getById(int id) throws DMException {
        return orgnizationMapper.getById(id);
    }

    @Override
    public int countSubOrg(int parentId) throws DMException {
        return orgnizationMapper.countSubOrg(parentId);
    }
}
