
package com.ustcsoft.framework.application;

import java.util.List;

import org.apache.log4j.Logger;
/**
 * 系统初始化回调方法类
 *
 * @author gaoLi
 *
 */
public class SystemInitBean {
	/**
	 * 日志
	 */
	private static final Logger log=Logger.getLogger(SystemInitBean.class);	
	/**
	 * 初始化Bean列表
	 */
	private List<InitBeanInterface> initBeanList;
	/**
	 * 系统初始化回调方法，通过该方法可以初始化数据或缓存数据
	 *
	 */
	public void init(){
		if(initBeanList!=null&&initBeanList.size()>0){
			for(int i=0;i<initBeanList.size();i++){
				InitBeanInterface initBean=initBeanList.get(i);
				initBean.init();
			}
		}
		log.info(".........  SystemInitBean init  ...........");
	}
	
	/**
	 * 销毁方法
	 */
	public void destroy(){
		
		log.info("......... SystemInitBean destroy ..........");
	}

	/**
	 * 返回初始化Bean列表
	 * @return List<InitBeanInterface> 初始化Bean列表
	 */
	public List<InitBeanInterface> getInitBeanList() {
		return initBeanList;
	}

	/**
	 * 设置初始化Bean列表
	 * @param initBeanList 初始化Bean列表
	 */
	public void setInitBeanList(List<InitBeanInterface> initBeanList) {
		this.initBeanList = initBeanList;
	}


}
