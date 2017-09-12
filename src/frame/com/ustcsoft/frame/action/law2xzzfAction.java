package com.ustcsoft.frame.action;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ustcsoft.frame.model.RyRyVO;
import com.ustcsoft.frame.service.IXzzfService;
import com.ustcsoft.framework.action.BaseActionSupport;
import com.ustcsoft.framework.spring.SpringContextUtil;

public class law2xzzfAction extends BaseActionSupport/*, QuartzJobBean*/ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 594226909703250538L;
	private IXzzfService xzzfService;
	
	
	public IXzzfService getXzzfService() {
		return xzzfService;
	}


	public void setXzzfService(IXzzfService xzzfService) {
		this.xzzfService = xzzfService;
	}

/*
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		//IXzzfService xzzfService = (IXzzfService) SpringContextUtil.getBean("xzzfService");
		RyRyVO vo = new RyRyVO();
		try {
			xzzfService.insertRyRyIntoXzzf(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
	public void testAction() throws Exception{
		RyRyVO vo = new RyRyVO();
		vo.setZfzh("348888888");
		System.out.println("333333");
		this.writeJson(vo);
	}
	
	public String testActionJsp() throws Exception{
		return SUCCESS;
	}
	
}
