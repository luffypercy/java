package xyxc.wx.dao.impl;

import org.springframework.stereotype.Component;

import cn.springmvc.dao.impl.BaseDaoImpl;
import xyxc.wx.dao.IXcWxHbaskDao;
import xyxc.wx.model.XcWxHbaskModel;

/**
 * <b>description</b>：微信用户信息表数据访问层 <br>
 * <b>time</b>：2016-12-05 22:17:13 <br>
 * <b>author</b>： percy-chen@hotmail.com
 */
@Component("xcWxHbaskDao")
public class XcWxHbaskDaoImpl extends BaseDaoImpl implements IXcWxHbaskDao {

	private static final String SQLMAPNAMESPACE = XcWxHbaskModel.class.getName();
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
