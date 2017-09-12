package com.ustcsoft.frame.util;

public class StringUtil {

	/**
	 * 返回一个字符串的字节长度，一个中文或中文符号占2个字节，英文数字或英文符号占1个字节
	 * 对应oracle默认字符集ZHS16GBK或其他数据库的GBK
	 * @param str
	 * @return
	 */
	public static int getBytesSize(String str){
		int len = 0;
		String chineseRegex = "[\u4e00-\u9fa5]";
		for (int i = 0; i < str.length(); i++) {
			// 获取一个字符
			String temp = str.substring(i, i + 1);
			// 补充temp.getBytes().length判断。java：UTF-8、GBK中文或中文字符占3、2个字节
			if (temp.matches(chineseRegex)||temp.getBytes().length > 1) { 
				// 中文字符长度为2
				len += 2;
			} else {
				// 其他字符长度为1
				len += 1;
			}
		}
		return len;
	}
	
	/**
	 * 返回一个限制字节数的字符串，一个中文或中文符号占2个字节，英文数字或英文符号占1个字节
	 * 对应oracle默认字符集ZHS16GBK或其他数据库的GBK
	 * @param str
	 * @param size
	 * @return
	 */
	public static String getStrByByteSize(String str, int size){
		int num = 0;
		int len = 0;
		String chineseRegex = "[\u4e00-\u9fa5]";
		for (int i = 0; i < str.length(); i++) {
			// 获取一个字符
			String temp = str.substring(i, i + 1);
			if (temp.matches(chineseRegex)||temp.getBytes().length > 1) {
				// 中文字符长度为2
				len += 2;
			} else {
				// 其他字符长度为1
				len += 1;
			}
			// 长度超出指定的长度时
			if(len > size){
				num = i;
				break;
			}
		}
		return str.substring(0, num);
	}
	
	/**
	 * 返回一个字符串的字节长度，一个中文或中文符号占3个字节，英文数字或英文符号占1个字节
	 * 对应oracle常用字符集AL32UTF8或其他数据库的GBK
	 * @param str
	 * @return
	 */
	public static int getBytesSizeByUTF8(String str){
		int len = 0;
		String chineseRegex = "[\u4e00-\u9fa5]";
		for (int i = 0; i < str.length(); i++) {
			// 获取一个字符
			String temp = str.substring(i, i + 1);
			if (temp.matches(chineseRegex)||temp.getBytes().length > 1) {
				// 中文字符长度为3
				len += 3;
			} else {
				// 其他字符长度为1
				len += 1;
			}
		}
		return len;
	}
	
	/**
	 * 返回一个限制字节数的字符串，一个中文或中文符号占3个字节，英文数字或英文符号占1个字节
	 * 对应oracle常用字符集AL32UTF8或其他数据库的GBK
	 * @param str
	 * @param size
	 * @return
	 */
	public static String getStrByByteSizeByUTF8(String str, int size){
		int num = 0;
		int len = 0;
		String chineseRegex = "[\u4e00-\u9fa5]";
		for (int i = 0; i < str.length(); i++) {
			// 获取一个字符
			String temp = str.substring(i, i + 1);
			if (temp.matches(chineseRegex)||temp.getBytes().length > 1) {
				// 中文字符长度为3
				len += 3;
			} else {
				// 其他字符长度为1
				len += 1;
			}
			// 长度超出指定的长度时
			if(len > size){
				num = i;
				break;
			}else{
				num = i;
			}
		}
		return str.substring(0, num);
	}
	
}
