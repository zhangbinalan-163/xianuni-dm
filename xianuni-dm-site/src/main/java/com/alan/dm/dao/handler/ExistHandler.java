package com.alan.dm.dao.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

public class ExistHandler implements ResultSetHandler<Boolean> {

    @Override
    public Boolean handle(ResultSet rs) throws SQLException {
        return Boolean.valueOf(rs!=null && rs.next());
    }

}
