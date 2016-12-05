package xyxc.wx.service;

import cn.springmvc.service.IBaseService;
import xyxc.wx.model.XcWxHbaskModel;

/**
 * <b>description</b>：微信用户信息表业务接口<br>
 * <b>time</b>：2016-12-05 22:17:13 <br>
 * <b>author</b>：  percy-chen@hotmail.com
 */
public interface IXcWxHbaskService extends IBaseService {
	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public XcWxHbaskModel insert(XcWxHbaskModel model) throws Exception;

	/**
	 * 更新,大于等于1表示成功，其他失败
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(XcWxHbaskModel model) throws Exception;

	/**
	 * 根据id删除,大于等于1表示成功，其他失败
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(long id) throws Exception;

}
