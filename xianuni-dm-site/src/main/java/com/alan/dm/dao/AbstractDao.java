package com.alan.dm.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.common.util.StringUtils;
import com.alan.dm.dao.handler.ExistHandler;
import com.alan.dm.dao.handler.StringHandler;
import com.alan.dm.dao.handler.StringListhandler;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO基类
 */
public abstract class AbstractDao {
	private static Logger LOG = LoggerFactory.getLogger(AbstractDao.class);

	private static final int SQL_TIMEOUT = 5 * 60;

	private Connection conn;

	private int commitCount = 0;

	private boolean autoCommit = true;

	private long connTime = System.currentTimeMillis();

	protected int page = -1;

	protected int count = -1;

	private String returnColumn;

	private long returnId = -1;

	private QueryRunner run = new QueryRunner();

	public void setRun(QueryRunner run) {
		this.run = run;
	}

	/**
	 * 分页参数
	 * 
	 * @param page
	 * @param count
	 */
	protected void setLimit(int page, int count) {
		this.page = page;
		this.count = count;
	}

	protected void setReturnColumn(String returnColumn) {
		this.returnColumn = returnColumn;
	}


	protected long getReturnId() {
		return returnId;
	}


	private void prepareConn() throws DMException {
		if (conn == null) {
			try {
				long t1 = System.currentTimeMillis();
				conn = getConnection();
				conn.setAutoCommit(autoCommit);
				if(LOG.isDebugEnabled()){
					LOG.info("prepareConn connTime:" + connTime + ",use time:" + (System.currentTimeMillis() - t1));
				}
			} catch (Exception e) {
				LOG.error("prepareConn error ", e);
				if (conn != null) {
					closeTransaction();
				}
				throw new DMException(e);
			}
		}
	}

	protected abstract Connection getConnection() throws SQLException;

	protected abstract String addLmit(String sql);

	/**
	 *
	 * @param sql
	 * @param handle
	 * @param param
	 * @param <T>
	 * @return
	 * @throws DMException
	 */
	protected <T> T query(String sql, ResultSetHandler<T> handle,
			Object... param) throws DMException {
		prepareConn();

		long beginTime = System.currentTimeMillis();
		try {
			if (page > -1 && count > -1) {
				sql = addLmit(sql);
			}

			fixParam(param);

			T result = run.query(conn, sql, handle, param);
			LOG.debug("query success connTime:" + connTime + ",sql:" + sql
					+ ",param:" + convertParamToString(param) + ",time:"
					+ getTime(beginTime));

			return result;
		} catch (Throwable e) {
			LOG.error("query error connTime:" + connTime + ",sql:" + sql
					+ ",param:" + convertParamToString(param) + ",time:"
					+ getTime(beginTime), e);
			throw new DMException(e);
		} finally {
			page = -1;
			count = -1;
			close();
		}
	}

	private long getTime(long beginTime) {
		return System.currentTimeMillis() - beginTime;
	}


	protected int getInt(String sql, Object... param) throws DMException {
		ScalarHandler handle = new ScalarHandler();
		Object obj = query(sql, handle, param);
		if (obj == null) {
			return 0;
		}

		if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).intValue();
		}
		return Integer.parseInt(String.valueOf(obj));
	}


	protected long getLong(String sql, Object... param) throws DMException {
		ScalarHandler handle = new ScalarHandler();
		Object obj = query(sql, handle, param);
		if (obj == null) {
			return 0;
		}

		if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).longValue();
		}
		return Long.parseLong(String.valueOf(obj));
	}


	protected boolean isExist(String sql, Object... param) throws DMException {
		ExistHandler handle = new ExistHandler();
		setLimit(0, 1);
		return query(sql, handle, param);
	}


	protected <T> T getBean(String sql, Class<T> type, Object... param)
			throws DMException {
		BeanHandler<T> handle = new BeanHandler<T>(type);
		return query(sql, handle, param);
	}


	protected <T> List<T> getBeanList(String sql, Class<T> type,
			Object... param) throws DMException {
		BeanListHandler<T> handle = new BeanListHandler<T>(type);
		return query(sql, handle, param);
	}

	protected List<String> getStringList(String sql, Object... param)
			throws DMException {
		StringListhandler handle = new StringListhandler();
		List<String> result = query(sql, handle, param);
		return result;
	}


	protected String getString(String sql, Object... param) throws DMException {
		StringHandler handle = new StringHandler();
		String str = query(sql, handle, param);
		return str;
	}


	private String convertParamToString(Object[] param) {
		int len = param == null ? 0 : param.length;
		StringBuilder sb = new StringBuilder(len * 20);
		sb.append("[");
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				sb.append(param[i]).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		return sb.toString();
	}

	protected int update(String sql, Object... param) throws DMException {
		prepareConn();

		long begin = System.currentTimeMillis();
		PreparedStatement ps = null;
		try {
			if (StringUtils.isEmpty(returnColumn)) {
				ps = conn.prepareStatement(sql);
			} else {
				ps = conn.prepareStatement(sql, new String[] { returnColumn });
			}
			
			ps.setQueryTimeout(SQL_TIMEOUT);

			fixParam(param);

			run.fillStatement(ps, param);

			int result = ps.executeUpdate();

			if (!StringUtils.isEmpty(returnColumn)) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					returnId = rs.getLong(1);
				}
			}
			long time = System.currentTimeMillis() - begin;
			String params = convertParamToString(param);
			LOG.info("update success connTime:"
					+ connTime
					+ ",sql:"
					+ sql
					+ ",param:"
					+ params
					+ (StringUtils.isEmpty(returnColumn) ? "" : ",returnId:"
							+ returnId) + ",time:" + time);

			return result;
		} catch (Throwable e) {
			long time = System.currentTimeMillis() - begin;
			String params = convertParamToString(param);
			LOG.error("update error connTime:" + connTime + ",sql:" + sql
					+ ",param:" + params + ",time:" + time, e);
			throw new DMException(e);
		} finally {
			returnColumn = null;
			try {
				DbUtils.closeQuietly(ps);
			} finally {
				close();
			}
		}
	}


	private void fixParam(Object[] param) {
		if (param == null)
			return;
		for (int i = 0; i < param.length; i++) {
			Object obj = param[i];
			if (obj instanceof Date) {
				param[i] = new Timestamp(((Date) obj).getTime());
			}
		}
	}


	private void close() throws DMException {
		if (conn != null && autoCommit) {
			closeTransaction();
		}
	}


	public void closeTransaction() throws DMException {
		if (conn == null) {
			LOG.warn("closeTransaction error conn is null");
			return;
		}
		if (commitCount <= 0) {
			try {
				DbUtils.close(conn);
				conn = null;
				commitCount = 0;
				autoCommit = true;
				if(LOG.isDebugEnabled()){
					LOG.debug("closeTransaction success connTime:" + connTime);
				}
			} catch (SQLException e) {
				LOG.error("closeTransaction error connTime:" + connTime, e);
				throw new DMException(e);
			}
		} else {
			LOG.info("closeTransaction conn:" + connTime + ",commitCount:"
					+ commitCount);
		}
	}

	public void rollbackTransaction() throws DMException {
		commitCount--;
		if (conn == null) {
			LOG.error("rollbackTransaction error conn is null connTime:" + connTime);
			return;
		}
		if (commitCount == 0) {
			try {
				DbUtils.rollback(conn);
				LOG.debug("rollbackTransaction success connTime:" + connTime);
			} catch (SQLException e) {
				LOG.error("rollbackTransaction error connTime:" + connTime, e);
				throw new DMException(e);
			}
		} else {
			LOG.info("rollbackTransaction conn:" + connTime + ",commitCount:"
					+ commitCount);
		}
	}

	public void commit() throws DMException {
		commitCount--;
		if (conn == null) {
			LOG.error("commit error conn is null connTime:" + connTime);
			return;
		}
		if (commitCount == 0) {
			try {
				conn.commit();
				if(LOG.isDebugEnabled()){
					LOG.debug("commit success connTime:" + connTime);
				}
			} catch (SQLException e) {
				LOG.error("commit error connTime:" + connTime, e);
				throw new DMException(e);
			}
		} else {
			LOG.info("commit conn:" + connTime + ",commitCount:" + commitCount);
		}
	}

	public void startTransaction() throws DMException {
		autoCommit = false;
		commitCount++;
		if (conn != null) {
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				LOG.error("startTransaction error connTime:" + connTime, e);
				throw new DMException(e);
			}
		}
		if(LOG.isDebugEnabled()){
			LOG.debug("startTransaction conn:" + connTime);
		}
	}
}
