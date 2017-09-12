/**
 * Copyright ustcsoft.com
 * All right reserved.
 */
package com.ustcsoft.framework.util;

/**
 * 共通常量类
 * 
 */
public class Constants {
	/** 成功信息 */
	public static final String MSG_SUCCESS = "{success:true,msg:'success'}";
	/** STR_00 */
	public static final String STR_00 = "00";
	/** STR_01 */
	public static final String STR_01 = "01";
	/** STR_ADMIN */
	public static final String STR_ADMIN = "admin";
	
	/** 接口返回值：发送成功 */
	public static final String SEND_RESULT_SUCCESS = "0";
	/** 接口返回值：发送失败 */
	public static final String SEND_RESULT_FAIL = "1";
	/** 接口返回值：存在无效值*/
	public static final String SEND_RESULT_INVALID = "2";

	/** 删除状态 */
	/** 未删除 */
	public static final String DELETE_STATUS_NO = STR_00;
	/** 删除 */
	public static final String DELETE_STATUS_YES = STR_01;

	/** 信息发送状态 */
	/** 信息发送状态：发送成功 */
	public static final String SEND_STATUS_SUCCESS = STR_00;
	/** 信息发送状态：发送失败 */
	public static final String SEND_STATUS_FAIL = STR_01;
	/** 信息发送状态：未发送 */
	public static final String SEND_STATUS_UNSEND = "10";

	/** 短信彩信状态 */
	/** 短信标志 */
	public static final String SMS_SIGN = STR_00;
	/** 彩信标志 */
	public static final String MMS_SIGN = STR_01;

	/** 创建者ID */
	public static final String CREATE_ID = STR_ADMIN;
	/** 更新者ID */
	public static final String UPDATE_ID = STR_ADMIN;
	/** 更新回数 */
	public static final int ROW_VERSION = 1;
	/** 更新回数 */
	public static final long ROW_VERSION_LONG = 1L;
	/** 合并字符串用的分号 */
	public static final String BRANCH = ";";

	/** 空白 */
	public static final String BLANK = "";
	
	
	/** 区分来自哪个调用：查询调用 */
	public static final String CX_CALL = "00";
	/** 区分来自哪个调用：订阅调用 */
	public static final String DY_CALL = "01";
	/** 区分来自哪个调用：接口调用 */
	public static final String JK_CALL = "02";
	
	/** 发送反馈：发送成功SEND_REMARK_SUCCESS */
	public static final String SEND_REMARK_SUCCESS = STR_00;
	/** 发送反馈：发送失败SEND_REMARK_FAIL */
	public static final String SEND_REMARK_FAIL = STR_01;
	
	/** 订阅状态：已订阅 */
	public static final String SUBSCRIBE_STATUS_YES = STR_00;
	/** 订阅状态：取消 */
	public static final String SUBSCRIBE_STATUS_NO = STR_01;
	
	/** 运营商区分：移动 */
	public static final String OPERATOR_CM = "00";
	/** 运营商区分：联通 */
	public static final String OPERATOR_CU = "01";
	/** 运营商区分：电信 */
	public static final String OPERATOR_CT = "02";

	/** pageAuth */
	public static final String PAGEAUTH = "pageAuth";
	/** menuId */
	public static final String MENUID = "menuId";
	/** success */
	public static final String SUCCESS = "success";
	/** STR_SEPARE */
	public static final String STR_SEPARE = "'";
	/** STR_COMMA */
	public static final String STR_COMMA = ",";
	/** USERINFO */
	public static final String USERINFO = "userInfo";
	/** baseService */
	public static final String BASESERVICE = "baseService";
	
}
