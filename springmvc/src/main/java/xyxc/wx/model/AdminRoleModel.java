package xyxc.wx.model;

import cn.springmvc.model.Model;

/**
 * <b>description</b>：系统角色模型<br>
 * <b>time</b>：2014-11-05 14:06:35 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminRoleModel extends Model {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色对应的权限
	 */
	private String right;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

}
