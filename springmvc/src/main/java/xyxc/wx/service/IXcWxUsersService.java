package xyxc.wx.service;

import cn.springmvc.service.IBaseService;
import xyxc.wx.model.XcWxUsersModel;

/**
 * <b>description</b>：微信用户信息表业务接口<br>
 * <b>time</b>：2016-12-05 21:40:52 <br>
 * <b>author</b>：  percy-chen@hotmail.com
 */
public interface IXcWxUsersService extends IBaseService {
	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public XcWxUsersModel insert(XcWxUsersModel model) throws Exception;

	/**
	 * 更新,大于等于1表示成功，其他失败
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(XcWxUsersModel model) throws Exception;

	/**
	 * 根据id删除,大于等于1表示成功，其他失败
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(long id) throws Exception;

}
