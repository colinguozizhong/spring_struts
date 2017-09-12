package com.ustcsoft.framework.vo;

import java.io.Serializable;

public class OperConfigVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String actionPath;

	private String operatType;

	private String message;
	
	private String parameter;
	
	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}

	public String getOperatType() {
		return operatType;
	}

	public void setOperatType(String operatType) {
		this.operatType = operatType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
