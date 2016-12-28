package xyxc.wx.service;

import cn.springmvc.dto.AdminMenusDto;
import cn.springmvc.dto.OperParamDto;
import cn.springmvc.service.IBaseService;
import xyxc.wx.model.AdminMenuModel;

/**
 * <b>description</b>：系统菜单业务接口<br>
 * <b>time</b>：2014-11-05 14:09:43 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public interface IAdminMenuService extends IBaseService {
	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminMenuModel insert(AdminMenuModel model, OperParamDto operParamDto)
			throws Exception;

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminMenuModel model, OperParamDto operParamDto)
			throws Exception;

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(Long id, OperParamDto operParamDto) throws Exception;

	/**
	 * 更新管理菜单
	 * 
	 * @param adminMenusDto
	 * @return
	 * @throws Exception
	 */
	public int updateMenu(AdminMenusDto adminMenusDto, OperParamDto operParamDto)
			throws Exception;

}
