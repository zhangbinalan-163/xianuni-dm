package com.alan.dm.dao.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

public class StringHandler implements ResultSetHandler<String> {

	@Override
	public String handle(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return rs.getString(1);
		}
		return null;
	}

}
