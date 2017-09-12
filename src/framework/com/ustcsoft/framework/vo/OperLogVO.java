package com.ustcsoft.framework.vo;

import java.io.Serializable;
import java.util.Date;

public class OperLogVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String operatorId;
	private String operatorName;
	private String operatType;
	private String operatContent;
	private Date createTime;
	private String browserName;
	private String browserVersion;
	private String ip;
	
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatType() {
		return operatType;
	}
	public void setOperatType(String operatType) {
		this.operatType = operatType;
	}
	public String getOperatContent() {
		return operatContent;
	}
	public void setOperatContent(String operatContent) {
		this.operatContent = operatContent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	public String getBrowserVersion() {
		return browserVersion;
	}
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
