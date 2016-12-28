package xyxc.wx.model;

import cn.springmvc.model.Model;
import cn.springmvc.util.StringUtil;

/**
 * <b>description</b>：登陆记录模型<br>
 * <b>time</b>：2014-11-07 09:33:55 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminLoginModel extends Model {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String ip;
	private String ipdata;
	private Long lastlogin;

	public AdminLoginModel() {
	}

	public AdminLoginModel(String username, String ip, String ipdata,
			Long lastlogin) {
		this.username = StringUtil.stringNullToEmpty(username);
		this.ip = StringUtil.stringNullToEmpty(ip);
		this.ipdata = StringUtil.stringNullToEmpty(ipdata);
		this.lastlogin = lastlogin;
	}

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

	public String getIpdata() {
		return ipdata;
	}

	public void setIpdata(String ipdata) {
		this.ipdata = ipdata;
	}

	public Long getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Long lastlogin) {
		this.lastlogin = lastlogin;
	}

}
