package com.yjd.comm.debt.service;

import com.yjd.comm.base.service.IBaseService;
import com.yjd.comm.debt.model.DebtRecordModel;

/**
 * <b>description</b>：逾期债权购买记录业务接口<br>
 * <b>time</b>：2016-12-05 18:28:10 <br>
 * <b>author</b>：  ready percy-chen@hotmail.com
 */
public interface IDebtRecordService extends IBaseService {
	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public DebtRecordModel insert(DebtRecordModel model) throws Exception;

	/**
	 * 更新,大于等于1表示成功，其他失败
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(DebtRecordModel model) throws Exception;

	/**
	 * 根据id删除,大于等于1表示成功，其他失败
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(long id) throws Exception;

}
