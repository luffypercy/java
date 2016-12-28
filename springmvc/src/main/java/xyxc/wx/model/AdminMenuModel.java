package xyxc.wx.model;

import cn.springmvc.model.Model;

/**
 * <b>description</b>：系统菜单模型<br>
 * <b>time</b>：2014-11-05 14:09:43 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminMenuModel extends Model {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单编号
	 */
	private Long id;
	/**
	 * 父菜单id
	 */
	private Long lid;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 控制器
	 */
	private String c;
	/**
	 * 动作组
	 */
	private String a;
	/**
	 * 排序
	 */
	private Integer pid;
	
	/**
	 * 跳转的url
	 */
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLid() {
		return lid;
	}

	public void setLid(Long lid) {
		this.lid = lid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
