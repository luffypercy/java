package cn.springmvc.dto;

import java.io.Serializable;
import java.util.List;

import xyxc.wx.model.AdminMenuModel;
import xyxc.wx.model.AdminModel;
import xyxc.wx.model.AdminRoleModel;

/**
 * @ClassName: SysUserDataWrap
 * @Description: 用户数据的封装，包含用户基本信息、权限菜单以及按钮
 * @author ready likun_557@163.com
 * @date 2014-8-19 上午11:21:07
 */
public class AdminDataWrap implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户基本信息
	 */
	private AdminModel adminModel;

	private AdminRoleModel adminRoleModel;
	/**
	 * 所有权限菜单列表
	 */
	private List<AdminMenuModel> adminMenuModels;

	/**
	 * 用户权限菜单
	 */
	private List<AdminMenuModel> userMenuModels;
	
	
	private List<AdminRoleInfoDto> roleList ;//角色组列表

	/**
	 * 用户权限json格式
	 */
	private String userMenuJson;

	public AdminModel getAdminModel() {
		return adminModel;
	}

	public void setAdminModel(AdminModel adminModel) {
		this.adminModel = adminModel;
	}

	public List<AdminMenuModel> getAdminMenuModels() {
		return adminMenuModels;
	}

	public void setAdminMenuModels(List<AdminMenuModel> adminMenuModels) {
		this.adminMenuModels = adminMenuModels;
	}

	public AdminRoleModel getAdminRoleModel() {
		return adminRoleModel;
	}

	public void setAdminRoleModel(AdminRoleModel adminRoleModel) {
		this.adminRoleModel = adminRoleModel;
	}

	public List<AdminMenuModel> getUserMenuModels() {
		return userMenuModels;
	}

	public void setUserMenuModels(List<AdminMenuModel> userMenuModels) {
		this.userMenuModels = userMenuModels;
	}

	public String getUserMenuJson() {
		return userMenuJson;
	}

	public void setUserMenuJson(String userMenuJson) {
		this.userMenuJson = userMenuJson;
	}

	public List<AdminRoleInfoDto> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<AdminRoleInfoDto> roleList) {
		this.roleList = roleList;
	}
	
	

}
