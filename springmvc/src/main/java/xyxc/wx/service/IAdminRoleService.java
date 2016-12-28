package xyxc.wx.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.dto.OperParamDto;
import cn.springmvc.service.IBaseService;
import xyxc.wx.model.AdminRoleModel;


/**
 * <b>description</b>：系统角色业务接口<br>
 * <b>time</b>：2014-11-05 14:06:35 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public interface IAdminRoleService extends IBaseService {
	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminRoleModel insert(AdminRoleModel model, OperParamDto operParamDto)
			throws Exception;

	/**
	 * 更新,大于等于1表示成功，其他失败
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminRoleModel model, OperParamDto operParamDto)
			throws Exception;

	/**
	 * 根据id删除,大于等于1表示成功，其他失败
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(Long id, OperParamDto operParamDto) throws Exception;

	/**
	 * 获取角色列表
	 * 
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	public List<AdminRoleModel> getArsByIds(List<Long> roleIds)
			throws Exception;

	/**
	 * 获取角色列表
	 * 
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	public Map<Long, AdminRoleModel> getArsMapByIds(List<Long> roleIds)
			throws Exception;

}
