package com.ustcsoft.framework.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.CannotSerializeTransactionException;
import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.LobRetrievalFailureException;
import org.springframework.jdbc.SQLWarningException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMapping;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.sql.dynamic.DynamicSql;
import com.ibatis.sqlmap.engine.mapping.sql.raw.RawSql;
import com.ibatis.sqlmap.engine.mapping.sql.simple.SimpleDynamicSql;
import com.ibatis.sqlmap.engine.mapping.sql.stat.StaticSql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import com.ibatis.sqlmap.engine.type.TypeHandler;
import com.ustcsoft.framework.dao.IBaseDaoIbatis;
import com.ustcsoft.framework.exception.DaoException;
import com.ustcsoft.framework.util.DateUtil;

public class BaseDaoIbatisImpl<T> extends SqlMapClientDaoSupport implements
		IBaseDaoIbatis<T> {
	protected final Log log = LogFactory.getLog(getClass());
	
	public String getIbatisSql(String id, Object parameterObject) {
		String sql = null;
		SqlMapClientImpl sqlmap = (SqlMapClientImpl) getSqlMapClient();
		SqlMapExecutorDelegate delegate = sqlmap.getDelegate();
		MappedStatement stmt = sqlmap.getMappedStatement(id);
		Sql staticSql = stmt.getSql();
		SqlMapSession SqlMapSessionImpl = sqlmap.openSession();

		SessionScope sessionScope = new SessionScope();
		sessionScope.setSqlMapClient(sqlmap);
		sessionScope.setSqlMapExecutor(sqlmap);

		sessionScope.setSqlMapTxMgr(SqlMapSessionImpl);

		MappedStatement ms = delegate.getMappedStatement(id);

		StatementScope statementScope = new StatementScope(sessionScope);
		sessionScope.incrementRequestStackDepth();
		ms.initRequest(statementScope);

		sql = staticSql.getSql(statementScope, parameterObject);
		SqlMapSessionImpl.close();
		return sql;
	}

	public Object selectObject(String sqlId, Object o) throws DaoException {
		Object object;
		try {
			object = getSqlMapClientTemplate().queryForObject(sqlId, o);
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("程序执行方法： selectObject(" + sqlId + "," + o
					+ ")出错!!! 其他访问出错!");
		}
		return object;
	}

	public List<T> selectObjects(String sqlId, Object o) throws DaoException {
		List queryResult = null;
		try {
			queryResult = getSqlMapClientTemplate().queryForList(sqlId, o);
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 其他访问出错!");
		}
		return queryResult;
	}

	public List<T> selectObjects(String sqlId, Object o, int skipResults,
			int maxResults) throws DaoException {
		List list = null;
		try {
			list = getSqlMapClientTemplate().queryForList(sqlId, o,
					skipResults, maxResults);
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("程序执行方法： selectObjects(" + sqlId + "," + o
					+ ")出错!!! 其他访问出错!");
		}
		return list;
	}

	public void insertObject(String sqlId, Object o) throws DaoException {
		try {
			getSqlMapClientTemplate().insert(sqlId, o);
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			String errorMessage = dive.getMessage();
			if(errorMessage!=null){
				errorMessage = com.ustcsoft.frame.util.StringUtil.getStrByByteSizeByUTF8(errorMessage, 3900);
			}else{
				errorMessage = "未知";
			}
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 插入或更新数据库；违反数据完整性约束!"
					+ "原因："+errorMessage);
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("程序执行方法： insertObject(" + sqlId + "," + o
					+ ")出错!!! 其他访问出错!");
		}
	}

	public void batchInsertObject(String _sqlId,  List<T> _o) throws DaoException {
		final String sqlId = _sqlId;
		final List<T> o= _o;
		try {
			getSqlMapClientTemplate().execute(
					new SqlMapClientCallback() {
						public Object doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							executor.startBatch();
							int batch = 0;
							for (int i = 0; i < o.size(); i++) {
								executor.insert(sqlId,o
										.get(i));
								batch++;
								if (batch != 1000)
									continue;
								executor.executeBatch();
								batch = 0;
							}

							executor.executeBatch();

							return null;
						}
					});
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("程序执行方法： batchInsertObject(" + sqlId + ","
					+ o + ")出错!!! 其他访问出错!");
		}
	}

	public int updateObject(String sqlId, Object o) throws DaoException {
		try {
			return getSqlMapClientTemplate().update(sqlId, o);
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
					+ ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new DaoException("程序执行方法： updateObject(" + sqlId + "," + o
				+ ")出错!!! 其他访问出错!");
	}

	public void batchUpdateObject(String _sqlId, List<T> _o) throws DaoException {
		final String sqlId = _sqlId;
		final List<T> o= _o;
		try {
			getSqlMapClientTemplate().execute(
					new SqlMapClientCallback() {
						public Object doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							executor.startBatch();
							int batch = 0;
							for (int i = 0; i <o.size(); i++) {
								executor.update(sqlId, o
										.get(i));
								batch++;
								if (batch == 300) {
									executor.executeBatch();
									batch = 0;
								}
							}
							executor.executeBatch();

							return null;
						}
					});
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("程序执行方法： batchUpdateObject(" + sqlId + ","
					+ o + ")出错!!! 其他访问出错!");
		}
	}

	public int deleteObject(String sqlId, Object o) throws DaoException {
		try {
			return getSqlMapClientTemplate().delete(sqlId, o);
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
					+ ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new DaoException("程序执行方法： deleteObject(" + sqlId + "," + o
				+ ")出错!!! 其他访问出错!");
	}

	public void batchDeleteObject(String _sqlId, List<T> _o) throws DaoException {
		final String sqlId = _sqlId;
		final List<T> o= _o;
		try {
			getSqlMapClientTemplate().execute(
					new SqlMapClientCallback() {
						public Object doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							executor.startBatch();
							int batch = 0;
							for (int i = 0; i < o.size(); i++) {
								executor.delete(sqlId, o
										.get(i));
								batch++;
								if (batch == 300) {
									executor.executeBatch();
									batch = 0;
								}
							}
							executor.executeBatch();

							return null;
						}
					});
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("程序执行方法： batchDeleteObject(" + sqlId + ","
					+ o + ")出错!!! 其他访问出错!");
		}
	}

	public long countObject(String sqlId, Object o) throws DaoException {
		try {
			return ((Long) getSqlMapClientTemplate().queryForObject(sqlId, o))
					.longValue();
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
					+ ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new DaoException("程序执行方法： countObject(" + sqlId + "," + o
				+ ")出错!!! 其他访问出错!");
	}
	

	public Object executeProcedureForObject(String procedureId, Object o)
			throws DaoException {
		return selectObject(procedureId, o);
	}

	public List<T> executeProcedureForList(String procedureId, Object o)
			throws DaoException {
		return selectObjects(procedureId, o);
	}

	/**
	 * WangHao 2012-7-24废止，原因：SQL注入漏洞
	 */
	@Deprecated
	protected String getExecuteSQL(String sqlId, Object object) {
		// SqlMapExecutorDelegate是一个相当核心的类,他存放了配置文件所有信息和java连接对象,有一个会话池和一个请求池,还有sql解析其,以及一个具体sql语句的执行者,大家可以看看
		// SqlMapExecutorDelegate
		// delegate=((ExtendedSqlMapClient)(getSqlMapClientTemplate().getSqlMapClient())).getDelegate();
		// //这个类用来存放某个id名的Statement信息,如这个当中的getProduct就是一条语句的配置id名
		// MappedStatement ms = delegate.getMappedStatement(sqlId);
		// //sql类就是某一类型 Statement的对应sql拼装解析类
		// Sql sql=ms.getSql();
		// //然后调用getSql方法,把参数值数组传进去,如下面只有一个参数productId,便可以生成 形如 select * from
		// xxx where
		// xx=?的sql语句了,代理类的sql执行者便可以根据这个sql和参数值数组进行参数查询了,前面那个参数的类型是com.ibatis.sqlmap.engine.scope.RequestScope,这个要从上面提到的代理类里面获取,但是因为是保护成员,而且发现拼凑sql的时候并没有多大作用,所以传了个null进去,结果居然通过了,不过这部分我还要确认一下.个人感觉RequestScope还是很重要的,可以会影响其他类型的Statement
		// System.out.println(sql.getSql(null,object));
		// return null;

		SqlMapClientImpl sqlmap = (SqlMapClientImpl) getSqlMapClientTemplate()
				.getSqlMapClient();

		MappedStatement stmt = sqlmap.getMappedStatement(sqlId);
		Sql sql = stmt.getSql();

		String sqlStr = null;
		SessionScope sessionScope = new SessionScope();
		sessionScope.incrementRequestStackDepth();
		StatementScope statementScope = new StatementScope(sessionScope);
		stmt.initRequest(statementScope);
		ParameterMap parameterMap = sql.getParameterMap(statementScope, object);
		// 获取sql语句(含?号)
		if (sql instanceof DynamicSql) {
			DynamicSql dynamicSql = (DynamicSql) sql;
			sqlStr = dynamicSql.getSql(statementScope, object);
//			System.out
//					.println("DynamicSql####################################################"
//							+ sqlStr);
		} else if (sql instanceof StaticSql) {
			StaticSql staticSql = (StaticSql) sql;
			sqlStr = staticSql.getSql(statementScope, object);
//			System.out
//					.println("StaticSql####################################################"
//							+ sqlStr);
		}else if (sql instanceof SimpleDynamicSql) {
			SimpleDynamicSql simpleDynamicSql = (SimpleDynamicSql) sql;
			sqlStr = simpleDynamicSql.getSql(null, object);
//			System.out
//					.println("SimpleDynamicSql####################################################"
//							+ sqlStr);
		}else if (sql instanceof RawSql) {
			RawSql rawSql = (RawSql) sql;
			sqlStr = rawSql.getSql(statementScope, object);
//			System.out
//					.println("RawSql####################################################"
//							+ sqlStr);
		}
		// 有动态参数
		if (parameterMap != null) {
			Object[] parameterMappings = parameterMap
					.getParameterObjectValues(statementScope, object);
			// 这个对象就是送到DB端取数据的参数数组
			// 我们要做的就是拼凑?和参数数据对应,此处使用的是ibatis的解析后的结果,
			// 不存在?和参数个数不匹配的问题,有的话在前面执行sql出已经以异常形式抛出
			for (int j = 0; j < parameterMappings.length; j++) {
				if (parameterMappings[j] instanceof String) {
					sqlStr = sqlStr.replaceFirst("\\?", " '"
							+ ((String) parameterMappings[j]) + "' ");
				} else if (parameterMappings[j] instanceof Integer) {
					sqlStr = sqlStr.replaceFirst("\\?", " '"
							+ ((Integer) parameterMappings[j]) + "' ");
				} else if (parameterMappings[j] instanceof BigDecimal) {
					sqlStr = sqlStr.replaceFirst("\\?", " '"
							+ ((BigDecimal) parameterMappings[j])
									.toPlainString() + "' ");
				} else  if (parameterMappings[j] instanceof Date){
					sqlStr = sqlStr.replaceFirst("\\?", " to_date('"
							+ DateUtil.format("yyyy-MM-dd HH:mm:ss", (Date) parameterMappings[j])
									+ "','yyyy-mm-dd HH24:MI:SS') ");
				}
			}
//			System.out.println("#####################end of replace sql :"
//					+ sqlStr);
		}
		sql.cleanup(statementScope);
		sessionScope.cleanup();
		stmt = null;
		sql = null;
		sessionScope = null;
		statementScope = null;
		return sqlStr;
		//return "select count(*) from ("+ sqlStr+")" ;
	}
	public String  getcountsql(String sqlStr){
		return "select count(*) from ("+ sqlStr+")" ;
	}
	/**
	 * 新版本的总记录数检索方法
	 * @param sqlId sqlId
	 * @param object object
	 * @return count
	 * @throws DaoException
	 * @throws SQLException
	 */
	private long executeCount(String sqlId, Object object) throws SQLException {
		SqlMapClientImpl sqlmap = (SqlMapClientImpl) getSqlMapClientTemplate()
				.getSqlMapClient();

		MappedStatement stmt = sqlmap.getMappedStatement(sqlId);
		Sql sql = stmt.getSql();

		String sqlStr = null;
		SessionScope sessionScope = new SessionScope();
		sessionScope.incrementRequestStackDepth();
		StatementScope statementScope = new StatementScope(sessionScope);
		stmt.initRequest(statementScope);
		ParameterMap parameterMap = sql.getParameterMap(statementScope, object);
		// 获取sql语句(含?号)
		if (sql instanceof DynamicSql) {
			DynamicSql dynamicSql = (DynamicSql) sql;
			sqlStr = dynamicSql.getSql(statementScope, object);
		} else if (sql instanceof StaticSql) {
			StaticSql staticSql = (StaticSql) sql;
			sqlStr = staticSql.getSql(statementScope, object);
		}else if (sql instanceof SimpleDynamicSql) {
			SimpleDynamicSql simpleDynamicSql = (SimpleDynamicSql) sql;
			sqlStr = simpleDynamicSql.getSql(null, object);
		}else if (sql instanceof RawSql) {
			RawSql rawSql = (RawSql) sql;
			sqlStr = rawSql.getSql(statementScope, object);
		}
		long count = 0;
		Connection conn = sqlmap.getDataSource().getConnection();
		PreparedStatement pstmt = conn.prepareStatement(getcountsql(sqlStr));
		// 有动态参数
		if (parameterMap != null) {
			Object[] parameterValues = parameterMap
					.getParameterObjectValues(statementScope, object);
			setParameters(pstmt, parameterMap, parameterValues);
		}
		ResultSet rs = pstmt.executeQuery(); 
		if (rs.next()){ 
			count = rs.getLong(1);
		}
		this.log.debug("#Record Count:[" + count + "]");
		sql.cleanup(statementScope);
		sessionScope.cleanup();
		conn.close();
		stmt = null;
		sql = null;
		sessionScope = null;
		statementScope = null;
		//return sqlStr;
		
		return count;
	}
	private void setParameters(PreparedStatement ps, ParameterMap parameterMap, Object[] values) throws SQLException {
		SqlMapClientImpl sqlmap = (SqlMapClientImpl) getSqlMapClientTemplate().getSqlMapClient();
		ParameterMapping[] parameterMappings = parameterMap.getParameterMappings();
		for (int i = 0; i < parameterMappings.length; i++) {
			TypeHandler handler = sqlmap.delegate.getTypeHandlerFactory().getTypeHandler(values[i].getClass());
			handler.setParameter(ps, i + 1, values[i], parameterMappings[i].getJdbcTypeName());
		}
	}

	public long countObjects(String sqlId, Object o) throws DaoException {
		long totalCount =0;
		try {
			totalCount = executeCount(sqlId, o);
			//totalCount = (Long)this.getSqlMapClientTemplate().queryForObject("sysUtil.getTotalCount",getcountsql(getExecuteSQL(sqlId,o)));
		
		} catch (EmptyResultDataAccessException erde) {
			erde.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 返回结果为空!");
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException juainoe) {
			juainoe.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 错误更新数据行数!");
		} catch (CannotGetJdbcConnectionException cgjce) {
			cgjce.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 不能获得连接!");
		} catch (CannotAcquireLockException cale) {
			cale.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 不能获得锁!");
		} catch (CannotSerializeTransactionException cste) {
			cste.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 事务连接失败!");
		} catch (DeadlockLoserDataAccessException dlde) {
			dlde.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 数据死锁!");
		} catch (IncorrectResultSizeDataAccessException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 结果大小超出范围!");
		} catch (IncorrectResultSetColumnCountException irscce) {
			irscce.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集字段统计!");
		} catch (LobRetrievalFailureException irsde) {
			irsde.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! lob恢复失败!");
		} catch (IncorrectUpdateSemanticsDataAccessException iusde) {
			iusde.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 错误的数据更新语句!");
		} catch (TypeMismatchDataAccessException tmde) {
			tmde.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 数据类型不匹配!");
		} catch (BadSqlGrammarException bge) {
			bge.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 不正确的sql语句!");
		} catch (InvalidResultSetAccessException irsae) {
			irsae.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 不正确的结果集!");
		} catch (DataAccessResourceFailureException darfe) {
			darfe.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 资源访问失败!");
		} catch (SQLWarningException sqe) {
			sqe.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! sql警告!");
		} catch (UncategorizedSQLException usqe) {
			usqe.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 非类别sql问题!");
		} catch (OptimisticLockingFailureException dive) {
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 乐观锁失败，没有检测到数据库!");
		} catch (PessimisticLockingFailureException plfe) {
			plfe.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 悲观锁失败!");
		} catch (CleanupFailureDataAccessException cfdae) {
			cfdae.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 清除失败，可能连接已关闭!");
		} catch (NonTransientDataAccessResourceException ntdare) {
			ntdare.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 不是短暂的资源访问失败!");
		} catch (DataIntegrityViolationException dive) {
			dive.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 插入或更新数据库；违反数据完整性约束!");
		} catch (DataRetrievalFailureException drfe) {
			drfe.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 违反数据完整性!");
		} catch (InvalidDataAccessApiUsageException idaae) {
			idaae.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 无效的使用api!");
		} catch (InvalidDataAccessResourceUsageException idarue) {
			idarue.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 无效的资源使用!");
		} catch (PermissionDeniedDataAccessException pddae) {
			pddae.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 许可访问拒绝!");
		} catch (UncategorizedDataAccessException ude) {
			ude.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 非类别数据访问问题!");
		} catch (ConcurrencyFailureException cfe) {
			cfe.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 同步失败!");
		} catch (TransientDataAccessResourceException tdare) {
			tdare.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问资源失败!");
		} catch (NonTransientDataAccessException ntdae) {
			ntdae.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 非短暂的数据访问!");
		} catch (RecoverableDataAccessException rde) {
			rde.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 重新获得数据访问失败!");
		} catch (TransientDataAccessException tdae) {
			tdae.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 短暂数据访问!");
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 数据访问失败!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("程序执行方法： countObjects(" + sqlId + "," + o
					+ ")出错!!! 其他访问出错!");
		}
		return totalCount;
	}
}