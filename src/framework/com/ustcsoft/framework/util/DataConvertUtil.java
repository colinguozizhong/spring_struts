package com.ustcsoft.framework.util;

import java.util.HashMap;

public class DataConvertUtil {
	// 标准业户类别及汉字对应数组
	public static final String[][] yeHuLeiBieArray = {
		{"KY","班线客运业户"},
		{"LY","旅游客运业户"},
		{"KZ","客运站业户"},
		{"KZ","客运业户"},
		{"HY","普通货运业户"},
		{"WY","危险货物运输业户"},
		{"HZ","货运站（场）业户"},
		{"JC","检测站业户"},
		{"WX","机动车维修业户"},
		{"JP","机动车驾驶培训业户"},
		{"CZ","出租运输业户"},
		{"BY","搬运业户"},
		{"FW","服务类业户"},
		{"NJ","农机业户"},
		{"QT","业户"}
	};
	
	public static final HashMap<String,String> yeHuLeiBieBianMaMap = new HashMap<String,String>();
	static{
		yeHuLeiBieBianMaMap.put("KY","客");
		yeHuLeiBieBianMaMap.put("LY","旅");
		yeHuLeiBieBianMaMap.put("KZ","站");
		yeHuLeiBieBianMaMap.put("HY","货");
		yeHuLeiBieBianMaMap.put("WY","危");
		yeHuLeiBieBianMaMap.put("HZ","站");
		yeHuLeiBieBianMaMap.put("JC","检");
		yeHuLeiBieBianMaMap.put("WX","维");
		yeHuLeiBieBianMaMap.put("JP","驾");
		yeHuLeiBieBianMaMap.put("CZ","租");
		yeHuLeiBieBianMaMap.put("BY","搬");
		yeHuLeiBieBianMaMap.put("FW","服");
		yeHuLeiBieBianMaMap.put("NJ","农");
		yeHuLeiBieBianMaMap.put("QT","其他");
		
		yeHuLeiBieBianMaMap.put("1","客");
		yeHuLeiBieBianMaMap.put("2","旅");
		yeHuLeiBieBianMaMap.put("3","站");
		yeHuLeiBieBianMaMap.put("4","货");
		yeHuLeiBieBianMaMap.put("5","危");
		yeHuLeiBieBianMaMap.put("6","检");
		yeHuLeiBieBianMaMap.put("7","维");
		yeHuLeiBieBianMaMap.put("8","驾");
		yeHuLeiBieBianMaMap.put("9","租");
		yeHuLeiBieBianMaMap.put("10","服");
		yeHuLeiBieBianMaMap.put("11","赁");
		yeHuLeiBieBianMaMap.put("12","装");
		yeHuLeiBieBianMaMap.put("13","其他");
	}
	public static final String[][] yeHuLeiBieBianMaArray = {
		{"KY","客"},
		{"LY","旅"},
		{"KZ","站"},
		{"HY","货"},
		{"WY","危"},
		{"HZ","站"},
		{"JC","检"},
		{"WX","维"},
		{"JP","驾"},
		{"CZ","租"},
		{"BY","搬"},
		{"FW","服"},
		{"NJ","农"},
		{"QT","其他"},
		
		{"1","客"},
		{"2","旅"},
		{"3","站"},
		{"4","货"},
		{"5","危"},
		{"6","检"},
		{"7","维"},
		{"8","驾"},
		{"9","租"},
		{"10","服"},
		{"11","赁"},
		{"12","装"},
		{"13","其他"}
	};
}
