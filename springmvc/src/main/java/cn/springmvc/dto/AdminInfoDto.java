package cn.springmvc.dto;

import java.util.List;

import xyxc.wx.model.AdminModel;

/**
 * <b>description</b>：管理员数据 <br>
 * <b>time</b>：2014-11-7下午5:25:36 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminInfoDto extends AdminModel {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 管理员信息
	 */
	private AdminModel adminModel;

	public List<AdminRoleInfoDto> roleList ;//角色组列表


	public AdminInfoDto() {
	}

	public AdminInfoDto(AdminModel adminModel,List<AdminRoleInfoDto> roleList) {
		this.adminModel = adminModel;
		this.roleList = roleList;
	}

	public AdminModel getAdminModel() {
		return adminModel;
	}

	public void setAdminModel(AdminModel adminModel) {
		this.adminModel = adminModel;
	}

	public List<AdminRoleInfoDto> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<AdminRoleInfoDto> roleList) {
		this.roleList = roleList;
	}

	

}
