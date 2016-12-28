package cn.springmvc.dto;

import java.io.Serializable;
import java.util.List;

import xyxc.wx.model.AdminMenuModel;


/**
 * <b>description</b>：管理菜单数组 <br>
 * <b>time</b>：2014-11-6下午3:23:16 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminMenusDto implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 管理菜单列表
	 */
	private List<AdminMenuModel> menus;

	public List<AdminMenuModel> getMenus() {
		return menus;
	}

	public void setMenus(List<AdminMenuModel> menus) {
		this.menus = menus;
	}

}
