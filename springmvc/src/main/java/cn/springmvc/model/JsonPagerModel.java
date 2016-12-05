package cn.springmvc.model;

import java.io.Serializable;
import java.util.List;

public class JsonPagerModel implements Serializable {
	/**
	* @Fields serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	// 当前页
	private long currentPage;
	// 记录数
	private long count;
	// 总页数
	private long pageCount;
	// 数据集合
	@SuppressWarnings("rawtypes")
	private List dataList;

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
}
