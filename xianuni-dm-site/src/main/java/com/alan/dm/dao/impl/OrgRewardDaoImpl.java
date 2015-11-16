package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IOrgRewardDao;
import com.alan.dm.entity.OrgReward;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.condition.OrgRewardCondition;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "orgRewardDao")
public class OrgRewardDaoImpl extends BaseDao implements IOrgRewardDao{

    private static final String tableName="tb_org_reward";

    @Override
    public void insert(OrgReward orgReward) throws DMException {
        String sql="insert into "+tableName+"(orgId,name,rewardTime,type,level,desc) values(?,?,?,?,?,?)";
        update(sql,orgReward.getOrgId(),orgReward.getName(),orgReward.getRewardTime(),orgReward.getType(),orgReward.getLevel(),orgReward.getDesc());
    }

    @Override
    public void update(OrgReward orgReward) throws DMException {
        //TODO
    }

    @Override
    public void delete(OrgReward orgReward) throws DMException {
        String sql="delete from "+tableName+" where id=?";
        update(sql,orgReward.getId());
    }

    @Override
    public List<OrgReward> getByCondition(OrgRewardCondition condition, Page page) throws DMException {
        StringBuffer sb=new StringBuffer();
        sb.append("select * from ").append(tableName);
        List<Object> params=new ArrayList<Object>();
        if(condition!=null){
            StringBuffer whereSb=new StringBuffer();
            if(condition.getOrgId()!=null){
                whereSb.append(" and ").append("orgId=?");
                params.add(condition.getOrgId());
            }
            if(condition.getLevel()!=null){
                whereSb.append(" and ").append("level=?");
                params.add(condition.getLevel());
            }
            if(condition.getType()!=null){
                whereSb.append(" and ").append("type=?");
                params.add(condition.getType());
            }
            if(!StringUtils.isEmpty(condition.getName())){
                whereSb.append(" and ").append("name like ?");
                params.add(condition.getName() + "%");
            }
            String whereSQL=whereSb.toString();
            if(whereSQL.startsWith(" and")){
                whereSQL=whereSQL.substring(4);
                sb.append(" where ");
                sb.append(whereSQL);
            }
        }
        if(page!=null){
            setLimit(page.getCurrent(),page.getSize());
        }
        return getBeanList(sb.toString(),OrgReward.class,params.toArray());
    }

    @Override
    public OrgReward getById(int id) throws DMException {
        String sql = "select * from "+tableName+" where id=?";
        return getBean(sql,OrgReward.class,id);
    }

    @Override
    public int countByCondition(OrgRewardCondition condition) throws DMException {
        StringBuffer sb=new StringBuffer();
        sb.append("select count(*) from ").append(tableName);
        List<Object> params=new ArrayList<Object>();
        if(condition!=null){
            StringBuffer whereSb=new StringBuffer();
            if(condition.getOrgId()!=null){
                whereSb.append(" and ").append("orgId=?");
                params.add(condition.getOrgId());
            }
            if(condition.getLevel()!=null){
                whereSb.append(" and ").append("level=?");
                params.add(condition.getLevel());
            }
            if(condition.getType()!=null){
                whereSb.append(" and ").append("type=?");
                params.add(condition.getType());
            }
            if(!StringUtils.isEmpty(condition.getName())){
                whereSb.append(" and ").append("name like ?");
                params.add(condition.getName()+"%");
            }
            String whereSQL=whereSb.toString();
            if(whereSQL.startsWith(" and")){
                whereSQL=whereSQL.substring(4);
                sb.append(" where ");
                sb.append(whereSQL);
            }
        }
        return getInt(sb.toString(),params.toArray());
    }
}
