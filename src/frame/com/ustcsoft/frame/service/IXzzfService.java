package com.ustcsoft.frame.service;

import com.ustcsoft.frame.model.RyRyVO;
import com.ustcsoft.framework.service.IBaseServiceIbatis;

public interface IXzzfService extends IBaseServiceIbatis{
	
	void insertRyRyIntoXzzf(RyRyVO ryRyVo) throws Exception;
}
