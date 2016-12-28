package xyxc.wx.dao.impl;

import org.springframework.stereotype.Component;

import cn.springmvc.dao.impl.BaseDaoImpl;
import xyxc.wx.dao.IAdminLogsDao;
import xyxc.wx.model.AdminLogsModel;

/**
 * <b>description</b>：系统日志数据访问层 <br>
 * <b>time</b>：2014-11-05 14:06:16 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Component("adminLogsDao")
public class AdminLogsDaoImpl extends BaseDaoImpl implements IAdminLogsDao {

	private static final String SQLMAPNAMESPACE = AdminLogsModel.class.getName();
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
