package xyxc.wx.dao.impl;

import org.springframework.stereotype.Component;

import cn.springmvc.dao.impl.BaseDaoImpl;
import xyxc.wx.dao.IAdminRoleDao;
import xyxc.wx.model.AdminRoleModel;

/**
 * <b>description</b>：系统角色数据访问层 <br>
 * <b>time</b>：2014-11-05 14:06:35 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Component("adminRoleDao")
public class AdminRoleDaoImpl extends BaseDaoImpl implements IAdminRoleDao {

	private static final String SQLMAPNAMESPACE = AdminRoleModel.class.getName();
	private static final String PKNAME = "id";

	@Override
	public String getPrimaryKeyName() {
		return PKNAME;
	}

	@Override
	public String getSqlmapNamespace() {
		return SQLMAPNAMESPACE;
	}
	
}
