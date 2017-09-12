package com.ustcsoft.framework.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.ustcsoft.framework.exception.BaseException;
import com.ustcsoft.framework.exception.ExtFileUploadException;

public class ExceptionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			HttpServletRequest request = (HttpServletRequest) invocation
					.getInvocationContext()
					.get(
							"com.opensymphony.xwork2.dispatcher.HttpServletRequest");

			if (!(request.getRequestURI().indexOf("index") > -1 || request
					.getRequestURI().indexOf("login") > -1)) {

				if (request.getSession() == null
						|| request.getSession().getAttribute("userInfo") == null) {
					// session超时
					if (request.getHeader("x-requested-with") != null
							&& request.getHeader("x-requested-with")
									.equalsIgnoreCase("XMLHttpRequest")) {
						request.setAttribute("requestType", "ajax");
					}
					return "timeout";
				}
			}

			return invocation.invoke();
		} catch (ExtFileUploadException e) {
			e.printStackTrace();
			String errorMsg = e.toString();
			errorMsg = errorMsg.replace("\r\n", "").replace("'", "\\'");

			StackTraceElement[] ste = e.getStackTrace();
			StringBuffer detailmessage = new StringBuffer();
			for (int i = 0; i < ste.length; i++) {
				detailmessage.append(ste[i]).append("\t");
			}

			HttpServletRequest request = (HttpServletRequest) invocation
					.getInvocationContext()
					.get(
							"com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			request.setAttribute("json",
					"{inftype:2,showdetial:'',clientCode:'" + errorMsg
							+ "',message:'操作失败!!!',detailmessage:'"
							+ detailmessage + "',callback:''}");

			return "exceptionFileUpload";
		} catch (BaseException e) {
			e.printStackTrace();
			String errorMsg = e.getMessage();
			errorMsg = errorMsg.replace("\r\n", "");

			StackTraceElement[] ste = e.getStackTrace();
			StringBuffer detailmessage = new StringBuffer();
			for (int i = 0; i < ste.length; i++) {
				detailmessage.append(ste[i]).append("\t");
			}

			HttpServletRequest request = (HttpServletRequest) invocation
					.getInvocationContext()
					.get(
							"com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			request.setAttribute("json",
					"{inftype:2,showdetial:'',clientCode:'" + errorMsg
							+ "',message:'操作失败!!!',detailmessage:'"
							+ detailmessage + "',callback:''}");
			return "exception";
		} catch (RuntimeException e) {
			e.printStackTrace();
			String errorMsg = e.getMessage();
			errorMsg = errorMsg.replace("\r\n", "");

			StackTraceElement[] ste = e.getStackTrace();
			StringBuffer detailmessage = new StringBuffer();
			for (int i = 0; i < ste.length; i++) {
				detailmessage.append(ste[i]).append("\t");
			}

			HttpServletRequest request = (HttpServletRequest) invocation
					.getInvocationContext()
					.get(
							"com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			request.setAttribute("json",
					"{inftype:2,showdetial:'',clientCode:'" + errorMsg
							+ "',message:'操作失败!!!',detailmessage:'"
							+ detailmessage + "',callback:''}");
			return "exception";
		} catch (Exception e) {
			return "exception";
		}
	}
}