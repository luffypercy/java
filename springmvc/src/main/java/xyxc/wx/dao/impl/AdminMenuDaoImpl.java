package xyxc.wx.dao.impl;

import org.springframework.stereotype.Component;

import cn.springmvc.dao.impl.BaseDaoImpl;
import xyxc.wx.dao.IAdminMenuDao;
import xyxc.wx.model.AdminMenuModel;

/**
 * <b>description</b>：系统菜单数据访问层 <br>
 * <b>time</b>：2014-11-05 14:09:43 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Component("adminMenuDao")
public class AdminMenuDaoImpl extends BaseDaoImpl implements IAdminMenuDao {

	private static final String SQLMAPNAMESPACE = AdminMenuModel.class.getName();
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
