package cn.springmvc.dto;

import xyxc.wx.model.AdminModel;
import xyxc.wx.model.AdminRoleModel;

/**
 * <b>description</b>：管理员数据 <br>
 * <b>time</b>：2014-11-7下午5:25:36 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminDto extends AdminModel {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 管理员信息
	 */
	private AdminModel adminModel;
	/**
	 * 角色信息
	 */
	private AdminRoleModel adminRoleModel;

	public AdminDto() {
	}

	public AdminDto(AdminModel adminModel, AdminRoleModel adminRoleModel) {
		this.adminModel = adminModel;
		this.adminRoleModel = adminRoleModel;
	}

	public AdminModel getAdminModel() {
		return adminModel;
	}

	public void setAdminModel(AdminModel adminModel) {
		this.adminModel = adminModel;
	}

	public AdminRoleModel getAdminRoleModel() {
		return adminRoleModel;
	}

	public void setAdminRoleModel(AdminRoleModel adminRoleModel) {
		this.adminRoleModel = adminRoleModel;
	}

}
