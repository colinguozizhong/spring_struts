package com.ustcsoft.framework.dao;

import java.util.List;

import com.ustcsoft.framework.exception.DaoException;

public abstract interface IBaseDaoIbatis<T> {
	public abstract Object selectObject(String paramString, Object paramObject)
			throws DaoException;

	public abstract List<T> selectObjects(String paramString, Object paramObject)
			throws DaoException;

	public abstract void insertObject(String paramString, Object paramObject)
			throws DaoException;

	public abstract void batchInsertObject(String paramString, List<T> paramList)
			throws DaoException;

	public abstract int updateObject(String paramString, Object paramObject)
			throws DaoException;

	public abstract void batchUpdateObject(String paramString, List<T> paramList)
			throws DaoException;

	public abstract int deleteObject(String paramString, Object paramObject)
			throws DaoException;

	public abstract void batchDeleteObject(String paramString, List<T> paramList)
			throws DaoException;

	public abstract long countObject(String paramString, Object paramObject)
			throws DaoException;

	public abstract List<T> selectObjects(String paramString,
			Object paramObject, int paramInt1, int paramInt2)
			throws DaoException;

	public abstract String getIbatisSql(String paramString, Object paramObject);

	public abstract Object executeProcedureForObject(String paramString,
			Object paramObject) throws DaoException;

	public abstract List<T> executeProcedureForList(String paramString,
			Object paramObject) throws DaoException;
	
	public abstract long countObjects(String sqlId, Object o) throws DaoException;
}