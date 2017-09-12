
package com.ustcsoft.framework.application;
/**
 * 系统初始化回调的接口，实现了改接口的类，可以在启动时，被初始化回调
 *
 * @author gaoLi
 *
 */
public interface InitBeanInterface {
	/**
	 * 初始化方法
	 */
	void init();
}
