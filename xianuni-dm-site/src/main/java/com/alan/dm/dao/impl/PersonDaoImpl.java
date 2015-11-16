package com.alan.dm.dao.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.BaseDao;
import com.alan.dm.dao.IPersonDao;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.condition.PersonCondition;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbinalan on 15/11/16.
 */
@Repository(value = "personDao")
public class PersonDaoImpl extends BaseDao implements IPersonDao{

    private static final String tableName="tb_person";

    @Override
    public List<Person> getByCondition(PersonCondition condition, Page page) throws DMException {
        StringBuffer sb=new StringBuffer();
        sb.append("select * from ").append(tableName);
        List<Object> params=new ArrayList<Object>();
        if(condition!=null){
            StringBuffer whereSb=new StringBuffer();
            if(condition.getStatus()!=null){
                whereSb.append(" and ").append("status=?");
                params.add(condition.getStatus());
            }
            if(condition.getOrgId()!=null){
                whereSb.append(" and ").append("orgId=?");
                params.add(condition.getOrgId());
            }
            if(condition.getName()!=null){
                whereSb.append(" and ").append("name like ?");
                params.add(condition.getName()+"%");
            }
            if(condition.getNumber()!=null){
                whereSb.append(" and ").append("number like ?");
                params.add(condition.getNumber()+"%");
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
        return getBeanList(sb.toString(),Person.class,params.toArray());
    }

    @Override
    public int countByCondition(PersonCondition condition) throws DMException {
        StringBuffer sb=new StringBuffer();
        sb.append("select count(*) from ").append(tableName);
        List<Object> params=new ArrayList<Object>();
        if(condition!=null){
            StringBuffer whereSb=new StringBuffer();
            if(condition.getStatus()!=null){
                whereSb.append(" and ").append("status=?");
                params.add(condition.getStatus());
            }
            if(condition.getOrgId()!=null){
                whereSb.append(" and ").append("orgId=?");
                params.add(condition.getOrgId());
            }
            if(condition.getName()!=null){
                whereSb.append(" and ").append("name like ?");
                params.add(condition.getName()+"%");
            }
            if(condition.getNumber()!=null){
                whereSb.append(" and ").append("number like ?");
                params.add(condition.getNumber()+"%");
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
