package com.ustcsoft.framework.util;

import java.io.Serializable;
import java.util.List;
/**
 * 分页查询返回结果
 */
@SuppressWarnings("unchecked")
public class PageObject implements Serializable {
	
	private static final long serialVersionUID = 1214335599970597085L;
	
	/** 查询结果 */
	private List items;
	/** 总件数*/
	private Long totalCount;
	
	
	public PageObject(List items, Long totalCount) {
		setTotalCount(totalCount);
		setItems(items);
	}
	
	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
	
	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
			this.totalCount = totalCount;
	}

}