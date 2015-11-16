package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IOrgnizationDao;
import com.alan.dm.entity.Orgnization;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * 党组织DAO实现
 * Created by zhangbinalan on 15/11/11.
 */
@Repository(value = "orgnizationDao")
public class OrgnizationDaoimpl extends BaseDao implements IOrgnizationDao{

    private static final String tableName="party_orgnization";

    @Override
    public void insert(Orgnization orgnization) throws DMException {
        String sql="insert into "+tableName+"(name,parent,isParent,status,updateTime,createTime,electionTime,desc) values(?,?,?,?,?,?,?,?)";
        update(sql,orgnization.getName(),orgnization.getParent(),orgnization.isParent(),orgnization.getStatus(),orgnization.getUpdateTime(),orgnization.getCreateTime(),orgnization.getElectionTime(),orgnization.getDesc());
    }

    @Override
    public void delete(Orgnization orgnization) throws DMException {
        String sql="delete from "+tableName+" where id=?";
        update(sql,orgnization.getId());
    }
    @Override
    public void update(Orgnization orgnization) throws DMException {
        //TODO
    }

    @Override
    public List<Orgnization> getByParentOrg(int parentId,int page,int size) throws DMException {
        String sql = "select * from "+tableName+" where parent=?";
        setLimit(page,size);
        return getBeanList(sql,Orgnization.class,parentId);
    }

    @Override
    public Orgnization getById(int id) throws DMException {
        String sql = "select * from "+tableName+" where id=?";
        return getBean(sql,Orgnization.class,id);
    }

    @Override
    public int countSubOrg(int parentId) throws DMException {
        String sql = "select count(*) from " +tableName+ " where parent=?";
        return getInt(sql,parentId);
    }
}
