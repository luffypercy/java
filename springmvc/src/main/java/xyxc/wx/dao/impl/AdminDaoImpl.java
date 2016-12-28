package xyxc.wx.dao.impl;

import org.springframework.stereotype.Component;

import cn.springmvc.dao.impl.BaseDaoImpl;
import xyxc.wx.dao.IAdminDao;
import xyxc.wx.model.AdminModel;

/**
 * <b>description</b>：系统用户数据访问层 <br>
 * <b>time</b>：2014-11-05 14:06:27 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Component("adminDao")
public class AdminDaoImpl extends BaseDaoImpl implements IAdminDao {

	private static final String SQLMAPNAMESPACE = AdminModel.class.getName();
	private static final String PKNAME = "admin_id";

	@Override
	public String getPrimaryKeyName() {
		return PKNAME;
	}

	@Override
	public String getSqlmapNamespace() {
		return SQLMAPNAMESPACE;
	}
	
}
