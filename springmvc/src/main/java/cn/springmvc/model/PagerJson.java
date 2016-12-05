package cn.springmvc.model;

import java.util.List;

/**
 * 对jquery EasyUi数据源的封装
 * 
 * @author Ready 2012-10-21
 */
public class PagerJson {

	/**
	 * 
	 */
	private long page;
	private long total;
	@SuppressWarnings("rawtypes")
	private List rows;

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}

	@SuppressWarnings("rawtypes")
	public void setRows(List rows) {
		this.rows = rows;
	}
}
