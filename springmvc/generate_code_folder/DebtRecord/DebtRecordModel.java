package com.yjd.comm.debt.model;

import com.yjd.comm.base.model.Model;

/**
 * <b>description</b>：逾期债权购买记录模型<br>
 * <b>time</b>：2016-12-05 18:28:10 <br>
 * <b>author</b>：  ready percy-chen@hotmail.com
 */
public class DebtRecordModel extends Model{

	/**
	* @Fields serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	
	 /*
	  * 
	  */
	private java.lang.Integer id;
	 /*
	  * 
	  */
	private java.lang.Integer borrow_id;
	 /*
	  * 
	  */
	private java.lang.String borrow_title;
	 /*
	  *负责人admin_id 
	  */
	private java.lang.Integer admin_id;
	 /*
	  *购买价格 
	  */
	private java.math.BigDecimal price;
	 /*
	  *剩余期数 
	  */
	private java.lang.Integer limit;
	 /*
	  *状态0已还，1未还 
	  */
	private java.lang.Integer status;
	 /*
	  *添加时间 
	  */
	private java.lang.Integer addtime;
	 /*
	  *最后更新时间 
	  */
	private java.lang.Integer uptime;
	 /*
	  *版本 
	  */
	private java.lang.Integer version;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getBorrow_id() {
		return borrow_id;
	}

	public void setBorrow_id(java.lang.Integer borrow_id) {
		this.borrow_id = borrow_id;
	}
	public java.lang.String getBorrow_title() {
		return borrow_title;
	}

	public void setBorrow_title(java.lang.String borrow_title) {
		this.borrow_title = borrow_title;
	}
	public java.lang.Integer getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(java.lang.Integer admin_id) {
		this.admin_id = admin_id;
	}
	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}
	public java.lang.Integer getLimit() {
		return limit;
	}

	public void setLimit(java.lang.Integer limit) {
		this.limit = limit;
	}
	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	public java.lang.Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(java.lang.Integer addtime) {
		this.addtime = addtime;
	}
	public java.lang.Integer getUptime() {
		return uptime;
	}

	public void setUptime(java.lang.Integer uptime) {
		this.uptime = uptime;
	}
	public java.lang.Integer getVersion() {
		return version;
	}

	public void setVersion(java.lang.Integer version) {
		this.version = version;
	}

}
