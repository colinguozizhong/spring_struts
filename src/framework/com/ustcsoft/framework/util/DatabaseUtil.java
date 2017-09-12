package com.ustcsoft.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.ustcsoft.framework.exception.BaseRunTimeException;
import com.ustcsoft.framework.exception.BusinessException;
import com.ustcsoft.framework.service.IBaseServiceIbatis;
import com.ustcsoft.framework.spring.SpringContextUtil;

public class DatabaseUtil {
	public static final String DATABASE_TYPE_DB2 = "db2";
	public static final String DATABASE_TYPE_ORACLE = "oracle";
	public static final String DATABASE_TYPE_MsSql = "sqlserver";
	public static final String DATABASE_TYPE_Dm = "Dm";
	private static final String DATABASE_DRIVER_DB2 = "com.ibm.db2.jcc.DB2Driver";
	private static final String DATABASE_DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";
	private static final String DATABASE_DRIVER_MsSql = "net.sourceforge.jtds.jdbc.Driver";
	private static final String DATABASE_DRIVER_Dm = "dm.jdbc.driver.DmDriver";
	private static String currentDriver = "";

	public static String getDatabaseType() {
		if ("".equals(currentDriver)) {
			String drivers = "";
			Properties jdbcrop = new Properties();
			try {
				jdbcrop.load(DatabaseUtil.class
						.getResourceAsStream("/jdbc.properties"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (jdbcrop != null) {
				drivers = jdbcrop.getProperty("jdbc.driverClassName").trim();
			}
			if ("".equals(drivers)) {
				throw new BaseRunTimeException("读取数据库配置文件出错！");
			}

			if ("com.ibm.db2.jcc.DB2Driver".equals(drivers))
				return "db2";
			if ("oracle.jdbc.driver.OracleDriver".equals(drivers))
				return "oracle";
			if ("net.sourceforge.jtds.jdbc.Driver".equals(drivers))
				return "sqlserver";
			if ("dm.jdbc.driver.DmDriver".equals(drivers)) {
				return "Dm";
			}
			throw new BaseRunTimeException("未知的数据库类型配置！");
		}
		return currentDriver;
	}

	@SuppressWarnings("unchecked")
	public static Date getSysDate() {
		IBaseServiceIbatis baseService = (IBaseServiceIbatis) SpringContextUtil
				.getService("baseService");
		Date sysDate = new Date();
		try {
			sysDate = (Date) baseService.findObject(
					"sysUtil.selectSysDate", null);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return sysDate;
	}

	@SuppressWarnings("unchecked")
	public static Long nextSequenceValue(String sequence) {
		IBaseServiceIbatis baseService = (IBaseServiceIbatis) SpringContextUtil
				.getService("baseService");
		Long nextSeq = null;
		try {
			nextSeq = (Long) baseService.findObject("sysUtil.seqNextVal",
					sequence);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return nextSeq;
	}

	@SuppressWarnings("unchecked")
	public static boolean validateUniqueness(String table, String condition) throws BusinessException {
		IBaseServiceIbatis baseService = (IBaseServiceIbatis) SpringContextUtil
			.getService("baseService");
		Map map =new HashMap();
		map.put("table", table);
		map.put("condition", condition);
		return (Integer)baseService.findObject(
				"sysUtil.validateUniqueness", map)>0;
	}
}