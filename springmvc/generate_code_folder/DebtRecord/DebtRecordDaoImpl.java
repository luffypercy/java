package com.yjd.pub.debt.dao.impl;

import org.springframework.stereotype.Component;
import com.yjd.comm.base.dao.impl.BaseDaoImpl;
import com.yjd.comm.debt.model.DebtRecordModel;
import com.yjd.pub.debt.dao.IDebtRecordDao;

/**
 * <b>description</b>：逾期债权购买记录数据访问层 <br>
 * <b>time</b>：2016-12-05 18:28:10 <br>
 * <b>author</b>： ready percy-chen@hotmail.com
 */
@Component("debtRecordDao")
public class DebtRecordDaoImpl extends BaseDaoImpl implements IDebtRecordDao {

	private static final String SQLMAPNAMESPACE = DebtRecordModel.class.getName();
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
