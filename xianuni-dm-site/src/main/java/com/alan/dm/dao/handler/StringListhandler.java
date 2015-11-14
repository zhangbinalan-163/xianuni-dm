package com.alan.dm.dao.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.AbstractListHandler;

public class StringListhandler extends AbstractListHandler<String> {
	protected String handleRow(ResultSet rs) throws SQLException {
		return rs.getString(1);
	}
}
