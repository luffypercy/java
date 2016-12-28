package xyxc.wx.dao.impl;

import org.springframework.stereotype.Component;

import cn.springmvc.dao.impl.BaseDaoImpl;
import xyxc.wx.dao.IAdminLoginDao;
import xyxc.wx.model.AdminLoginModel;

/**
 * <b>description</b>：登陆记录数据访问层 <br>
 * <b>time</b>：2014-11-07 09:33:55 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Component("adminLoginDao")
public class AdminLoginDaoImpl extends BaseDaoImpl implements IAdminLoginDao {

	private static final String SQLMAPNAMESPACE = AdminLoginModel.class.getName();
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
