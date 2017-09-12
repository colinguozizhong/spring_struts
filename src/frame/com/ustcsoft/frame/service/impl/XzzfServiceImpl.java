package com.ustcsoft.frame.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.ustcsoft.frame.model.RyRyVO;
import com.ustcsoft.frame.service.IXzzfService;
import com.ustcsoft.framework.service.IBaseServiceIbatis;
import com.ustcsoft.framework.service.impl.BaseServiceIbatisImpl;
import com.ustcsoft.framework.spring.SpringContextUtil;

public class XzzfServiceImpl extends BaseServiceIbatisImpl implements IXzzfService{

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void insertRyRyIntoXzzf(RyRyVO ryRyVo) throws Exception {
		// 将数据插入到 xzzf数据库
		Long count = (Long) this.findObject("xzzf.countRyRyByZfrybh", ryRyVo);
		if(count>0L){
			this.modifyObject("xzzf.updateRyRy", ryRyVo);
		}else{
			this.addObject("xzzf.insertIntoRyRy", ryRyVo);
		}
	}
}
