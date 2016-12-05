package xyxc.wx.dao.impl;

import org.springframework.stereotype.Component;

import cn.springmvc.dao.impl.BaseDaoImpl;
import xyxc.wx.dao.IXcWxUsersDao;
import xyxc.wx.model.XcWxUsersModel;

/**
 * <b>description</b>：微信用户信息表数据访问层 <br>
 * <b>time</b>：2016-12-05 21:40:52 <br>
 * <b>author</b>： percy-chen@hotmail.com
 */
@Component("xcWxUsersDao")
public class XcWxUsersDaoImpl extends BaseDaoImpl implements IXcWxUsersDao {

	private static final String SQLMAPNAMESPACE = XcWxUsersModel.class.getName();
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
