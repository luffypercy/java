package xyxc.wx.service;

import cn.springmvc.dto.OperParamDto;
import cn.springmvc.service.IBaseService;
import xyxc.wx.model.AdminLoginModel;

/**
 * <b>description</b>：登陆记录业务接口<br>
 * <b>time</b>：2014-11-07 09:33:55 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public interface IAdminLoginService extends IBaseService {
	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminLoginModel insert(AdminLoginModel model) throws Exception;

	/**
	 * 更新,大于等于1表示成功，其他失败
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminLoginModel model) throws Exception;

	/**
	 * 根据id删除,大于等于1表示成功，其他失败
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(Long id) throws Exception;

	/**
	 * 删除一个月之前的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public int deleteAmonthData(OperParamDto operParamDto) throws Exception;
}
