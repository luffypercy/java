package xyxc.wx.model;

import cn.springmvc.model.Model;

/**
 * <b>description</b>：系统日志模型<br>
 * <b>time</b>：2014-11-05 14:06:16 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminLogsModel extends Model {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 系统日志编号
	 */
	private Long id;
	/**
	 * 操作人名
	 */
	private String username;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * 时间
	 */
	private Long time;
	/**
	 * 执行操作
	 */
	private String msg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
