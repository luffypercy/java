package cn.springmvc.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类 设置属性过程为:setPageSize,setCurrentPage,setCount,setDataList
 * 
 * @author Ready 2012-10-17
 */
public class PagerModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 每页显示数量
	private long pageSize = 16;

	// 当前页行的开始行的索引，如1,2,3....
	private long startIndex;

	// 当前页行的结束索引
	private long endIndex;

	// 当前页
	private long currentPage;

	// 上一页索引
	private long priviousPage;

	// 下一页索引
	private long nextPage;

	// 记录数
	private long count;

	// 是否有上一页
	private boolean isPriviousPage;

	// 是否有下一页
	private boolean isNextPage;

	// 总页数
	private long pageCount;

	// 数据集合
	@SuppressWarnings("rawtypes")
	private List dataList;

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
		this.startIndex = (this.currentPage - 1) * this.pageSize + 1;
		this.endIndex = this.startIndex + this.pageSize - 1;
	}

	public void setCount(long count) {
		this.count = count;
		this.pageCount = (this.count % this.pageSize == 0) ? this.count
				/ this.pageSize : this.count / this.pageSize + 1;
		if (this.currentPage >= this.pageCount) {
			this.setCurrentPage(this.pageCount);
			this.nextPage = this.pageCount;
			this.isNextPage = false;
		} else {
			this.nextPage = this.currentPage + 1;
			this.isNextPage = true;
		}
		if (this.currentPage <= 1) {
			this.priviousPage = 1;
			this.isPriviousPage = false;
		} else {
			this.priviousPage = this.currentPage - 1;
			this.isPriviousPage = true;
		}
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}

	public long getPageSize() {
		return pageSize;
	}

	public long getStartIndex() {
		return startIndex;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public long getPriviousPage() {
		return priviousPage;
	}

	public void setPriviousPage(long priviousPage) {
		this.priviousPage = priviousPage;
	}

	public long getNextPage() {
		return nextPage;
	}

	public void setNextPage(long nextPage) {
		this.nextPage = nextPage;
	}

	public long getCount() {
		return count;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	@SuppressWarnings("rawtypes")
	public List getDataList() {
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public boolean getIsPriviousPage() {
		return isPriviousPage;
	}

	public boolean getIsNextPage() {
		return isNextPage;
	}

	public long getEndIndex() {
		if (endIndex >= this.getCount()) {
			this.endIndex = this.getCount();
		}
		return endIndex;
	}

	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}


}
