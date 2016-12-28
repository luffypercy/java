package cn.springmvc.dto;

import java.io.Serializable;

/**
 * <b>description</b>：操作相关信息(此类在back中使用) <br>
 * <b>time</b>：2014-11-7上午9:01:53 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class OperParamDto implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 操作人id
	 */
	private Long admin_id;
	/**
	 * 操作人名称
	 */
	private String username;
	/**
	 * ip
	 */
	private String ip;
	
	public Long getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(Long admin_id) {
		this.admin_id = admin_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
