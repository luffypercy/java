package xyxc.wx.model;
import cn.springmvc.model.Model;

/**
 * <b>description</b>：系统用户模型<br>
 * <b>time</b>：2014-11-05 14:06:27 <br>
 * <b>author</b>：  ready likun_557@163.com
 */
public class AdminModel extends Model {

	/**
	* @Fields serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	
	private Long admin_id;
	private String username;
	private String password;
	private String encrypt;
	private Long role;
	private Integer verifynum;
	private String ip;
	private String ipdata;
	private Long lastlogin;

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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	public Long getRole() {
		return role;
	}
	public void setRole(Long role) {
		this.role = role;
	}
	public Integer getVerifynum() {
		return verifynum;
	}
	public void setVerifynum(Integer verifynum) {
		this.verifynum = verifynum;
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
