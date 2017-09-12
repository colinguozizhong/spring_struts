package com.ustcsoft.framework.vo;

public class ErrorLogVO {
	/**
	 * 表ID
	 */
  private String logId;
  /**
	 * 出错操作的名称
	 */
  private String operatorName;
  /**
	 * 出错记录时间
	 */
  private String createTime;
  /**
	 * 出错信息
	 */
  private String errorLog;
  /**
	 * 文件名
	 */
private String fileName;
/**
 * 出错方法名
 */
private String methodName;
  
public String getLogId() {
	return logId;
}
public void setLogId(String logId) {
	this.logId = logId;
}
public String getOperatorName() {
	return operatorName;
}
public void setOperatorName(String operatorName) {
	this.operatorName = operatorName;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
public String getErrorLog() {
	return errorLog;
}
public void setErrorLog(String errorLog) {
	this.errorLog = errorLog;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getMethodName() {
	return methodName;
}
public void setMethodName(String methodName) {
	this.methodName = methodName;
}


}
