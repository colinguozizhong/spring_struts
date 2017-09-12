package com.ustcsoft.framework.util.log;

import java.lang.reflect.Method;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

import com.ustcsoft.framework.application.SystemConstants;

public class LogMethodBeforeAdvice implements MethodBeforeAdvice {
	private static final Logger log = Logger
			.getLogger(LogMethodBeforeAdvice.class);

	public void before(Method method, Object[] aobj, Object obj)
			throws Throwable {
		String s = SystemConstants.getAutolog();
		if ((s == null) || ("".equalsIgnoreCase(s))
				|| ("none".equalsIgnoreCase(s)))
			return;
		if ((!"all".equalsIgnoreCase(s)) && (!s.matches(".*before.*")))
			return;
		String s1 = "===>调用方法前：" + obj.getClass().getName() + "."
				+ method.getName() + "  参数为:(";
		if (aobj != null) {
			for (int i = 0; i < aobj.length; i++) {
				try {
					JSONArray jsonarray = JSONArray.fromObject(aobj[i]);
					s1 = s1 + jsonarray;
				} catch (Exception exception) {
					s1 = s1 + aobj[i];
				}
				if (i != aobj.length - 1) {
					s1 = s1 + ",";
				}
			}
			s1 = s1 + ")";
		}
		String s2 = obj.getClass().getName();
		if ((s2 != null) && (s2.length() > 1))
			s2 = s2.substring(0, 1);
		if (!"$".equalsIgnoreCase(s2))
			log.info(s1);
	}
}