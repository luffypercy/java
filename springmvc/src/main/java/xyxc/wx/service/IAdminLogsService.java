package xyxc.wx.service;

import cn.springmvc.dto.OperParamDto;
import cn.springmvc.service.IBaseService;
import xyxc.wx.model.AdminLogsModel;

/**
 * <b>description</b>：系统日志业务接口<br>
 * <b>time</b>：2014-11-05 14:06:16 <br>
 * <b>author</b>：  ready likun_557@163.com
 */
public interface IAdminLogsService extends IBaseService {
	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminLogsModel insert(AdminLogsModel model) throws Exception;

	/**
	 * 更新,大于等于1表示成功，其他失败
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminLogsModel model) throws Exception;

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
