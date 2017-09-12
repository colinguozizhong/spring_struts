package com.ustcsoft.framework.spring;

import org.springframework.context.ApplicationContext;

public class SpringContextUtil {
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getService(String serviceName) {
		Object service = null;

		service = applicationContext.getBean(serviceName);
		return service;
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}