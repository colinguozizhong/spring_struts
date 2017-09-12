package com.ustcsoft.framework.util.log;

import java.lang.reflect.Method;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import com.ustcsoft.framework.application.SystemConstants;

public class LogMethodAfterAdvice implements AfterReturningAdvice {
	private static final Logger log = Logger
			.getLogger(LogMethodAfterAdvice.class);

	public void afterReturning(Object obj, Method method, Object[] aobj,
			Object obj1) throws Throwable {
		String s = SystemConstants.getAutolog();
		if ((s == null) || ("".equalsIgnoreCase(s))
				|| ("none".equalsIgnoreCase(s)))
			return;
		if ((!"all".equalsIgnoreCase(s)) && (!s.matches(".*after.*")))
			return;
		String s1 = "===>执行方法后：" + obj1.getClass().getName() + "."
				+ method.getName() + "(";
		if (aobj != null) {
			for (int i = 0; i < aobj.length; i++) {
				try {
					JSONArray jsonarray1 = JSONArray.fromObject(aobj[i]);
					s1 = s1 + jsonarray1;
				} catch (Exception exception1) {
					s1 = s1 + aobj[i];
				}

				if (i != aobj.length - 1) {
					s1 = s1 + ",";
				}
			}

			s1 = s1 + ") ";
		}
		try {
			JSONArray jsonarray = JSONArray.fromObject(obj);
			String s3 = " ";
			if (obj != null)
				s3 = s3 + obj.getClass().getName() + " ";
			s1 = s1 + "\nDEBUG:===>方法返回值：" + s3 + jsonarray;
		} catch (Exception exception) {
			s1 = s1 + "\nDEBUG:===>方法返回值：" + obj;
		}
		String s2 = obj1.getClass().getName();
		if ((s2 != null) && (s2.length() > 1))
			s2 = s2.substring(0, 1);
		if (!"$".equalsIgnoreCase(s2))
			log.info(s1);
	}
}