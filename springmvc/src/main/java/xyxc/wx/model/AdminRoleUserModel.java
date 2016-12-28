package xyxc.wx.model;

import cn.springmvc.model.Model;

/**
 * <b>description</b>：管理员角色组关系服务模型<br>
 * <b>time</b>：2016-11-03 17:41:48 <br>
 * <b>author</b>：  carl
 */
public class AdminRoleUserModel extends Model{

	/**
	* @Fields serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	
	 /*
	  *唯一id 
	  */
	private java.lang.Long id;
	 /*
	  *角色组id 
	  */
	private java.lang.Long role_id;
	 /*
	  *管理员id 
	  */
	private java.lang.Long admin_id;
	 /*
	  *创建时间 
	  */
	private java.lang.Long create_time;
	 /*
	  *修改时间 
	  */
	private java.lang.Long update_time;

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.Long getRole_id() {
		return role_id;
	}

	public void setRole_id(java.lang.Long role_id) {
		this.role_id = role_id;
	}
	public java.lang.Long getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(java.lang.Long admin_id) {
		this.admin_id = admin_id;
	}

	public java.lang.Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(java.lang.Long create_time) {
		this.create_time = create_time;
	}

	public java.lang.Long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(java.lang.Long update_time) {
		this.update_time = update_time;
	}
	
	

}
