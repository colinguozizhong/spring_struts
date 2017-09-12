package com.ustcsoft.framework.spring;


import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		SpringContextUtil.setApplicationContext(context);
		System.out.println("spring加载完成！！！");
	}
}