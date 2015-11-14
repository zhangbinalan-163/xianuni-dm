package com.alan.dm.dao;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 基本DAO实现
 * Created by zhangbinalan on 15/11/11.
 */
public class BaseDao extends AbstractDao{

    @Resource(name = "dataSource")
    private DataSource ds;

    /**
     * 获得当前dao需要的链接
     *
     * @return
     */
    protected Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 增加分页
     *
     * @param sql
     * @return
     */
    protected String addLmit(String sql) {
        //现在只有MYSQL
        sql += " limit " + (page-1)*count + "," + count;
        //sql = "select * from(select t_1.*,rownum n from (" + sql+ ") t_1 where rownum<" + (begin + count + 1) + ") t_2 where n>"+ begin;
        return sql;
    }
}
