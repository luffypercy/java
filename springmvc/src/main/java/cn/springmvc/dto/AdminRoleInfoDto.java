package cn.springmvc.dto;

import cn.springmvc.model.Model;

public class AdminRoleInfoDto extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long admin_id;
	private Long role_id;
	private String role_name;
	
	
	
	public Long getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(Long admin_id) {
		this.admin_id = admin_id;
	}
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	
	
	

}
