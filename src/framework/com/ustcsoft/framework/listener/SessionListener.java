package com.ustcsoft.framework.listener;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ustcsoft.framework.application.SystemConstants;

public class SessionListener implements HttpSessionListener {
	// 保存系统所有session
	static ConcurrentHashMap<String, HttpSession> ht = new ConcurrentHashMap();
	// 保存登录人Id以及此Id当前登录次数（在多个IE中同事登录某个账号导致）
	static ConcurrentHashMap<String, Integer> ut = new ConcurrentHashMap();

	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		System.out.println("create session :" + session.getId());
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		System.out.println("destory session :" + session.getId());
		ht.remove(session.getId());

		/*if (session.getAttribute("userInfo") != null) {
			SysUserVO userInfo = (SysUserVO) session.getAttribute("userInfo");
			if (ut.get(userInfo.getUserId().toString()) > 1) {
				ut.put(userInfo.getUserId().toString(), ut.get(userInfo.getUserId().toString()) - 1);
			} else {
				ut.remove(userInfo.getUserId().toString());
				SystemConstants.setOnlineUserCount(SystemConstants
						.getOnlineUserCount() - 1);
				
			}
		}*/
		System.out.println((new StringBuilder("当前在线人数为：")).append(
				SystemConstants.getOnlineUserCount()).toString());
	}

	public static void setUt(HttpSession session) {
		/*if (!ht.containsKey(session.getId())) {
			SysUserVO userInfo = (SysUserVO) (session.getAttribute("userInfo"));
			if (ut.containsKey(userInfo.getUserId().toString())) {
				ut.put(userInfo.getUserId().toString(), ut.get(userInfo.getUserId().toString()) + 1);
			} else {
				ut.put(userInfo.getUserId().toString(), 1);
				SystemConstants.setOnlineUserCount(SystemConstants
						.getOnlineUserCount() + 1);
			}
			ht.put(session.getId(), session);
		}*/
	}
	
	public static Iterator getSet() {
		return ht.values().iterator();
	}

	public static void removeSession(String sessionId) {
		ht.remove(sessionId);
	}

	public static HttpSession getSession(String sessionId) {
		return (HttpSession) ht.get(sessionId);
	}
}