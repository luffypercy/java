package cn.springmvc.dto;

import java.io.Serializable;

/**
 * <b>description</b>：管理员登陆参数 <br>
 * <b>time</b>：2014-11-7上午10:10:38 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminLoginInDto implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * 地址
	 */
	private String ipdata;

	public AdminLoginInDto() {
	}

	public AdminLoginInDto(String username, String password, String ip,
			String ipdata) {
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.ipdata = ipdata;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
}
