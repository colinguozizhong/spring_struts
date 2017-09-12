package com.ustcsoft.framework.util.log;

import net.sf.json.JSONArray;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.ustcsoft.framework.application.SystemConstants;

public class LogMethodInterceptor implements MethodInterceptor {
	private static final Logger log = Logger
			.getLogger(LogMethodInterceptor.class);

	public Object invoke(MethodInvocation methodinvocation) throws Throwable {
		String s = SystemConstants.getAutolog();
		if ((s == null) || ("".equalsIgnoreCase(s))
				|| ("none".equalsIgnoreCase(s)))
			return methodinvocation.proceed();
		if ((!"all".equalsIgnoreCase(s)) && (!s.matches(".*run.*")))
			return methodinvocation.proceed();
		String s1 = methodinvocation.getMethod().getName();
		Class[] aclass = methodinvocation.getMethod().getParameterTypes();
		Object[] aobj = methodinvocation.getArguments();
		Class class1 = methodinvocation.getMethod().getReturnType();
		Class class2 = methodinvocation.getThis().getClass();
		String s2 = "===>执行方法：" + class2.getName() + "." + s1 + "(";
		if (aclass != null) {
			for (int i = 0; i < aclass.length; i++) {
				String s4 = aclass[i].getName();
				int j = s4.lastIndexOf(".");
				if (j > 0)
					s4 = s4.substring(j + 1);
				s2 = s2 + s4 + " ";
				try {
					JSONArray jsonarray = JSONArray.fromObject(aobj[i]);
					s2 = s2 + jsonarray;
				} catch (Exception exception) {
					s2 = s2 + aobj[i];
				}
				if (i != aclass.length - 1) {
					s2 = s2 + ",";
				}
			}
		}
		s2 = s2 + ") ";
		String s3 = class2.getName();
		if ((s3 != null) && (s3.length() > 1))
			s3 = s3.substring(0, 1);
		if (!"$".equalsIgnoreCase(s3))
			log.info(s2);
		return methodinvocation.proceed();
	}
}