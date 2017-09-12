package com.ustcsoft.framework.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ustcsoft.framework.dao.IBaseDaoIbatis;
import com.ustcsoft.framework.exception.BusinessException;
import com.ustcsoft.framework.exception.DaoException;
import com.ustcsoft.framework.service.IBaseServiceIbatis;
import com.ustcsoft.framework.util.DatabaseUtil;
import com.ustcsoft.framework.util.PageObject;

public class BaseServiceIbatisImpl<T> implements IBaseServiceIbatis<T> {
	private IBaseDaoIbatis<T> baseDaoIbatis;

	public void addObject(String sqlId, Object o) throws BusinessException {
		try {
			this.baseDaoIbatis.insertObject(sqlId, o);
		} catch (DaoException de) {
			de.printStackTrace();
			throw new BusinessException("程序执行方法： insertObject(" + sqlId + ","
					+ o + ")出错! \t" + de.getMessage());
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public void batchAddObject(String sqlId, List<T> list)
			throws BusinessException {
		try {
			this.baseDaoIbatis.batchInsertObject(sqlId, list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("batchAddObject(" + sqlId + "," + list
					+ ")方法出错！\t" + e.getMessage());
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public void batchModifyObject(String sqlId, List<T> list)
			throws BusinessException {
		try {
			this.baseDaoIbatis.batchUpdateObject(sqlId, list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("batchModifyObject(" + sqlId + ","
					+ list + ")方法出错！\t" + e.getMessage());
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public void batchRemoveObject(String sqlId, List<T> list)
			throws BusinessException {
		try {
			this.baseDaoIbatis.batchDeleteObject(sqlId, list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("batchRemoveObject(" + sqlId + ","
					+ list + ")方法出错！\t" + e.getMessage());
		}
	}

	public long countObject(String sqlId, Object o) throws BusinessException {
		long temp = 0L;
		try {
			temp = this.baseDaoIbatis.countObject(sqlId, o);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("countObject(" + sqlId + "," + o
					+ ")方法出错！\t" + e.getMessage());
		}

		return temp;
	}

	/**
	 * 检索记录条数，自动加select count(*) from (?)
	 * @param sqlId sqlId
	 * @param o o
	 * @return 记录条数
	 * @throws BusinessException
	 */
	public long countObjects(String sqlId, Object o) throws BusinessException {
		long temp = 0L;
		try {
			temp = this.baseDaoIbatis.countObjects(sqlId, o);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("countObjects(" + sqlId + "," + o
					+ ")方法出错！\t" + e.getMessage());
		}

		return temp;
	}

	public Object findObject(String sqlId, Object o) throws BusinessException {
		Object temp = null;
		try {
			temp = this.baseDaoIbatis.selectObject(sqlId, o);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("findObject(" + sqlId + "," + o
					+ ")方法出错！\t" + e.getMessage());
		}

		return temp;
	}

	public List<T> findObjects(String sqlId, Object o) throws BusinessException {
		List temp = null;
		try {
			temp = this.baseDaoIbatis.selectObjects(sqlId, o);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("findObjects(" + sqlId + "," + o
					+ ")方法出错！\t" + e.getMessage());
		}
		return temp;
	}

	public int modifyObject(String sqlId, Object o) throws BusinessException {
		try {
			int count =this.baseDaoIbatis.updateObject(sqlId, o);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("modifyObject(" + sqlId + "," + o
					+ ")方法出错！\t" + e.getMessage());
		}
	}

	public int removeObject(String sqlId, Object o) throws BusinessException {
		try {
			return this.baseDaoIbatis.deleteObject(sqlId, o);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("removeObject(" + sqlId + "," + o
					+ ")方法出错！\t" + e.getMessage());
		}
	}

	public List<T> findObjects(String sqlId, Object parameter, int skipResults,
			int maxResults) throws BusinessException {
		List<T> list = null;
		try {
			list = this.baseDaoIbatis.selectObjects(sqlId, parameter,
					skipResults, maxResults);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("findObjects(" + sqlId + ","
					+ parameter + "," + skipResults + "," + maxResults
					+ ")方法出错！\t" + e.getMessage());
		}
		return list;
	}
	

	public IBaseDaoIbatis<T> getBaseDaoIbatis() {
		return this.baseDaoIbatis;
	}

	public void setBaseDaoIbatis(IBaseDaoIbatis<T> baseDaoIbatis) {
		this.baseDaoIbatis = baseDaoIbatis;
	}

	public String getIbatisSql(String id, Object parameterObject) {
		return this.baseDaoIbatis.getIbatisSql(id, parameterObject);
	}

	public Object executeProcedureForObject(String procedureId, Object o)
			throws BusinessException {
		try {
			return this.baseDaoIbatis.executeProcedureForObject(procedureId, o);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new BusinessException("executeProcedureForObject(" + procedureId
					+ "," + o + ")方法出错！\t" + e.getMessage());
		}
	}

	public List<T> executeProcedureForList(String procedureId, Object o)
			throws BusinessException {
		try {
			return this.baseDaoIbatis.executeProcedureForList(procedureId, o);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new BusinessException("executeProcedureForList(" + procedureId
					+ "," + o + ")方法出错！\t" + e.getMessage());
		}
	}
	
	public PageObject findObjectForPage(String sqlId, Object o,int start,int limit) throws BusinessException {
		PageObject temp = null;
		try {
			long totalCount =this.baseDaoIbatis.countObjects(sqlId, o);
			List<?> items = this.baseDaoIbatis.selectObjects(sqlId, o,start,limit);
//			int totalCount =((List<?>)this.baseDaoIbatis.selectObjects(sqlId, o)).size();
			temp = new PageObject(items, totalCount);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("findObjectForPage(" + sqlId + "," + o
					+ ")方法出错！\t" + e.getMessage());
		}
	}
}