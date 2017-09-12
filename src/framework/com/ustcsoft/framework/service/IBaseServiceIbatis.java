package com.ustcsoft.framework.service;

import java.util.List;

import com.ustcsoft.framework.exception.BusinessException;
import com.ustcsoft.framework.util.PageObject;

public abstract interface IBaseServiceIbatis<T>
{
  public abstract Object findObject(String paramString, Object paramObject)
    throws BusinessException;

  public abstract List<T> findObjects(String paramString, Object paramObject)
    throws BusinessException;

  public abstract void addObject(String paramString, Object paramObject)
    throws BusinessException;

  public abstract void batchAddObject(String paramString, List<T> paramList)
    throws BusinessException;

  public abstract int modifyObject(String paramString, Object paramObject)
    throws BusinessException;

  public abstract void batchModifyObject(String paramString, List<T> paramList)
    throws BusinessException;

  public abstract int removeObject(String paramString, Object paramObject)
    throws BusinessException;

  public abstract void batchRemoveObject(String paramString, List<T> paramList)
    throws BusinessException;

  public abstract long countObject(String paramString, Object paramObject)
    throws BusinessException;

  public abstract List<T> findObjects(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws BusinessException;

  public abstract String getIbatisSql(String paramString, Object paramObject);

  public abstract Object executeProcedureForObject(String paramString, Object paramObject)
    throws BusinessException;

  public abstract List<T> executeProcedureForList(String paramString, Object paramObject)
    throws BusinessException;
  
  public abstract PageObject findObjectForPage(String sqlId, Object o,int Start,int limit ) throws BusinessException;
}