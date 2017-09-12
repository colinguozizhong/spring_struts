package com.ustcsoft.framework.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import com.ustcsoft.framework.exception.BusinessException;

public class SystemConstants {
	private static String autolog;
	
	public static String SYSTEM_NAME;
	
	public static String UPLOAD_PATH;
	
	public static String arcgisUrl;
	
	public static String arcgisPort;
	
	public static String arcgisParamBySheng;
	
	public static String arcgisParamByOther;
	
	public static String MapServer;
	
	public static String serviceUrl;
	
	public static String servicePort;
	
	private static int onlineUserCount = 0;
	//自动任务管理
	public static Scheduler sched;
	
	static {
		Properties sysprop = new Properties();
		try {
			sysprop.load(SystemConstants.class
					.getResourceAsStream("/sysconfig.properties"));
			autolog = sysprop.getProperty("sys.control.autolog");
			SYSTEM_NAME = sysprop.getProperty("SYSTEM_NAME");
			UPLOAD_PATH= sysprop.getProperty("UPLOAD_PATH");
			arcgisUrl= sysprop.getProperty("arcgisUrl");
			arcgisPort=	sysprop.getProperty("arcgisPort");
			arcgisParamBySheng= sysprop.getProperty("arcgisParamBySheng");
			arcgisParamByOther= sysprop.getProperty("arcgisParamByOther");
			MapServer = sysprop.getProperty("MapServer");
			serviceUrl = sysprop.getProperty("serviceUrl");
			servicePort = sysprop.getProperty("servicePort");
			SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
			sched = schedFact.getScheduler();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int getOnlineUserCount() {
		return onlineUserCount;
	}

	public static void setOnlineUserCount(int _onlineUserCount) {
		onlineUserCount = _onlineUserCount;
	}

	public static String getAutolog() {
		return autolog;
	}

	public static void setAutolog(String autolog) {
		SystemConstants.autolog = autolog;
	}

	public static String getUPLOAD_PATH() {
		return UPLOAD_PATH;
	}

	public static void setUPLOAD_PATH(String UPLOAD_PATH) {
		SystemConstants.UPLOAD_PATH = UPLOAD_PATH;
	}
}