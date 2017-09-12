package com.ustcsoft.framework.vo;


import java.util.Date;


public class OnlineUserVO extends BaseVO{

	private static final long serialVersionUID = -205074496010322966L;
	
	// 用户Id
	private Long userId;
	
	// 登录名
	private String userName;
	
	// 姓名
	private String name;
	
	// 登录IP
	private String loginIp;
	
	// 登录时间
	private Date loginTime;
	
	// SessionId
	private String sessionId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
