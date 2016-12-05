package com.yjd.pub.debt.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.yjd.comm.base.dao.IBaseDao;
import com.yjd.comm.base.service.impl.BaseServiceImpl;
import com.yjd.comm.debt.model.DebtRecordModel;
import com.yjd.comm.debt.service.IDebtRecordService;
import com.yjd.pub.debt.dao.IDebtRecordDao;

/**
 * <b>description</b>：逾期债权购买记录业务实现<br>
 * <b>time</b>：2016-12-05 18:28:10 <br>
 * <b>author</b>：  ready percy-chen@hotmail.com
 */
@Service("debtRecordService")
public class DebtRecordServiceImpl extends BaseServiceImpl implements IDebtRecordService {

	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public DebtRecordModel insert(DebtRecordModel model) throws Exception {
		return this.debtRecordDao.insertModel(model, true);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(DebtRecordModel model) throws Exception {
		return this.debtRecordDao.updateModel(model, true);
	}

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(long id) throws Exception {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("id", id);
		return this.debtRecordDao.deleteModel(paramMap, true);
	}

	@Resource
	private IDebtRecordDao debtRecordDao;

	@Override
	public IBaseDao getBaseDao() {
		return this.debtRecordDao;
	}

}
