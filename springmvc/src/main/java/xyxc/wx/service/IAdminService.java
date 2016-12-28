package xyxc.wx.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.dto.AdminDataWrap;
import cn.springmvc.dto.AdminDto;
import cn.springmvc.dto.AdminInfoDto;
import cn.springmvc.dto.AdminLoginInDto;
import cn.springmvc.dto.OperParamDto;
import cn.springmvc.model.PagerModel;
import cn.springmvc.service.IBaseService;
import xyxc.wx.model.AdminModel;
import xyxc.wx.model.AdminRoleUserModel;


/**
 * <b>description</b>：系统用户业务接口<br>
 * <b>time</b>：2014-11-05 14:06:27 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public interface IAdminService extends IBaseService {
	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminModel insert(AdminModel model, OperParamDto operParamDto,List<AdminRoleUserModel> roleList)
			throws Exception;

	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminModel insert(AdminModel model, OperParamDto operParamDto)
			throws Exception;
	/**
	 * 更新,大于等于1表示成功，其他失败
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminModel model, OperParamDto operParamDto,List<AdminRoleUserModel> roleList)
			throws Exception;
	/**
	 * 更新,大于等于1表示成功，其他失败
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminModel model, OperParamDto operParamDto)
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
	 * 根据用户名和密码获取用户信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public AdminModel getAdmin(String username, String password)
			throws Exception;

	/**
	 * 管理员登陆
	 * 
	 * @param adminLoginInDto
	 * @return
	 * @throws Exception
	 */
	public AdminDataWrap login(AdminLoginInDto adminLoginInDto)
			throws Exception;

	/**
	 * 根据用户名和密码获取管理员相关数据信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public AdminDataWrap getAdminDataWrap(String username, String password)
			throws Exception;

	/**
	 * 获取管理员数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<AdminDto> getAdminDtos(Map paramMap) throws Exception;
	

	/**
	 * 获取管理员数据对象
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public PagerModel getAdminDtoPage(Map paramMap) throws Exception;

	/**
	 * 获取管理员数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<AdminInfoDto> getAdminInfoDtos(Map paramMap) throws Exception;
}
