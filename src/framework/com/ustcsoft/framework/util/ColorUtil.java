package com.ustcsoft.framework.util;

import java.util.HashMap;

public class ColorUtil {
	public static HashMap<String,String> colorMap = new HashMap<String,String>();
	static {  
		colorMap.put("0000FF", "蓝");  
		colorMap.put("FFFF99", "农");// 农用车，原本颜色是粉黄
		colorMap.put("FFFFFF", "白");
		colorMap.put("FFFF00", "黄");
		colorMap.put("008000", "绿");
		colorMap.put("FEFEFF", "value2");// 颜色是浅白
		colorMap.put("000000", "黑");
	} 
	
}
