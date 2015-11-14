package com.alan.dm.dao.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

public class CountHandler implements ResultSetHandler<Integer> {

    @Override
    public Integer handle(ResultSet rs) throws SQLException {
        if(rs == null){
            return 0;
        }
        if(rs.next()){
            return Integer.valueOf(rs.getInt(1));
        }else{
            return Integer.valueOf(0);
        }
    }
}
