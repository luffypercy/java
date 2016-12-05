package xyxc.wx.model;

import cn.springmvc.model.Model;

/**
 * <b>description</b>：微信用户信息表模型<br>
 * <b>time</b>：2016-12-05 22:17:12 <br>
 * <b>author</b>：  percy-chen@hotmail.com
 */
public class XcWxHbaskModel extends Model{

	/**
	* @Fields serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	
	 /*
	  * 
	  */
	private java.lang.Integer id;
	 /*
	  *xc_wx_users表id 
	  */
	private java.lang.Integer wx_user_id;
	 /*
	  *内容 
	  */
	private java.lang.String content;
	 /*
	  * 
	  */
	private java.lang.Integer addtime;
	 /*
	  *发放状态：0-否 1-是 
	  */
	private java.lang.Integer status;
	 /*
	  *发放金额 
	  */
	private java.math.BigDecimal price;
	 /*
	  *最后更新时间 
	  */
	private java.lang.Integer uptime;
	 /*
	  *发送时间 
	  */
	private java.lang.Integer sendtime;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getWx_user_id() {
		return wx_user_id;
	}

	public void setWx_user_id(java.lang.Integer wx_user_id) {
		this.wx_user_id = wx_user_id;
	}
	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}
	public java.lang.Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(java.lang.Integer addtime) {
		this.addtime = addtime;
	}
	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}
	public java.lang.Integer getUptime() {
		return uptime;
	}

	public void setUptime(java.lang.Integer uptime) {
		this.uptime = uptime;
	}
	public java.lang.Integer getSendtime() {
		return sendtime;
	}

	public void setSendtime(java.lang.Integer sendtime) {
		this.sendtime = sendtime;
	}

}
