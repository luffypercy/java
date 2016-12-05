package cn.springmvc.model;

import java.io.Serializable;

/**
 * <b>description</b>：分页按钮列表 <br>
 * <b>time</b>：2014-8-22 下午7:14:54 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class PagerIndexModel implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 是否是数字
	 */
	private Boolean isNum;
	/**
	 * 页码
	 */
	private String pageIndex;
	/**
	 * 页url
	 */
	private String pageUrl;

	public PagerIndexModel() {
	}

	public PagerIndexModel(Boolean isNum, String pageIndex, String pageUrl) {
		this.isNum = isNum;
		this.pageIndex = pageIndex;
		this.pageUrl = pageUrl;
	}

	public Boolean getIsNum() {
		return isNum;
	}

	public void setIsNum(Boolean isNum) {
		this.isNum = isNum;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}
