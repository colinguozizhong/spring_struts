package com.ustcsoft.framework.util.log;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import com.ustcsoft.framework.application.SystemConstants;

public class LogExceptionAdvice implements ThrowsAdvice {
	private static final Logger log = Logger
			.getLogger(LogExceptionAdvice.class);

	public void afterThrowing(Throwable throwable) {
		String autolog = SystemConstants.getAutolog();
		if ((autolog == null) || ("".equalsIgnoreCase(autolog))
				|| ("none".equalsIgnoreCase(autolog)))
			return;
		if ((!"all".equalsIgnoreCase(autolog))
				&& (!autolog.matches(".*exception.*")))
			return;
		String logMsg = "===>执行方法抛出异常：" + throwable;
		log.error(logMsg);

		throwable.printStackTrace();
	}
}