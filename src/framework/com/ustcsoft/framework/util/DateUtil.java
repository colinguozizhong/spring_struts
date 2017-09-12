package com.ustcsoft.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 日期所在月的天数
	 * 如：“2011-02-02”所在2月份的天数为28天
	 * @param date
	 * @return
	 */
	public static int getDayCountInMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 得到日期所在月的最后一天
	 * 如：“2011-02-02”所在2月份的最后一天为“2011-02-28”
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, getDayCountInMonth(date));

		return calendar.getTime();
	}
	
	/**
	 * 得到日期所在月的第一天
	 * 如：“2011-02-02”所在2月份的第一天为“2011-02-01”
	 * @param date
	 * @return
	 */
	public static Date getMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}
	
	/**
	 * 为日期添加指定的月数
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date addMonth(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		return cal.getTime();
	}
	
	/**
	 * 为日期增加指定天数
	 * @param date 日期
	 * @param num 数量
	 * @return 增加后的日期
	 * 
	 */
	public static Date addDay(Date date, int num) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, num);
		return cal.getTime();
	}
	
	/**
	 * 格式化日期
	 * @param formatStr 日期格式。如“yyyy-MM-dd”
	 * @param date
	 * @return
	 */
	public static String format(String formatStr, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}
	
	/**
	 * 将格式化的日期字符串转换成日期。
	 * @param formatStr 日期格式。如“yyyy-MM-dd”
	 * @param dateStr 格式化的日期字符串。如“2011-02-16”
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String formatStr, String dateStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.parse(dateStr);
	}
	
	/**
	 * 得到日期在月份中的天数。
	 * 如“2011-02-08”在二月份中的天数为8
	 * @param date
	 * @return
	 */
	public static String getDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * 将日期的时、分、秒和毫秒设置为零，只留下年月日。
	 * 如“2011-02-03 23:12:34.345” 转换为“2011-02-03 00:00:00.000”
	 * @param date
	 * @return
	 */
	public static Date getYMDDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String formatStr = "yyyy-MM-dd HH:mm:ss.SSS";
		String dateStr = "2011-02-08 20:34:28.128";
//		
//		Date date = parse(formatStr, dateStr);
//		System.out.println(format(formatStr, addDay(date, 1)));
//		System.out.println(format(formatStr, addDay(date, -1)));
//		System.out.println(format(formatStr, addMonth(date, 1)));
//		System.out.println(format(formatStr, addMonth(date, -1)));
//		System.out.println(format(formatStr, getMonthFirstDay(date)));
//		System.out.println(format(formatStr, getMonthLastDay(date)));
//		System.out.println(format(formatStr, getMonthLastDay(addMonth(date, -1))));
//		System.out.println(getDayOfMonth(date));
//		System.out.println(format(formatStr, getYMDDate(date)));
		
	}
	
	
	public static String asString(java.util.Date date) {
		if (date == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}


}
