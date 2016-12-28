package xyxc.wx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.IBaseDao;
import cn.springmvc.dto.AdminDataWrap;
import cn.springmvc.dto.AdminDto;
import cn.springmvc.dto.AdminInfoDto;
import cn.springmvc.dto.AdminLoginInDto;
import cn.springmvc.dto.OperParamDto;
import cn.springmvc.enums.DbWREnums;
import cn.springmvc.model.PagerModel;
import cn.springmvc.service.IPager;
import cn.springmvc.util.AdminDataUtil;
import cn.springmvc.util.AdminModelConvert;
import cn.springmvc.util.FrameUtil;
import cn.springmvc.util.PagerUtil;
import cn.springmvc.util.SecurityCode;
import cn.springmvc.util.StringUtil;
import xyxc.wx.dao.IAdminDao;
import xyxc.wx.model.AdminLoginModel;
import xyxc.wx.model.AdminMenuModel;
import xyxc.wx.model.AdminModel;
import xyxc.wx.model.AdminRoleModel;
import xyxc.wx.model.AdminRoleUserModel;
import xyxc.wx.service.IAdminLoginService;
import xyxc.wx.service.IAdminMenuService;
import xyxc.wx.service.IAdminRoleService;
import xyxc.wx.service.IAdminService;

/**
 * <b>description</b>：系统用户业务实现<br>
 * <b>time</b>：2014-11-05 14:06:27 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Service("adminService")
public class AdminServiceImpl extends BaseServiceImpl implements IAdminService {

	/**
	 * 插入
	 * 
	 * @param model
	 * @param operParamDto
	 * @return
	 * @throws Exception
	 */
	public AdminModel insert(AdminModel model, OperParamDto operParamDto)
			throws Exception {
		model.setEncrypt(SecurityCode.getRandomNumber(6));
		model.setPassword(SecurityCode.passwordMD5_2(model.getPassword(),
				model.getEncrypt()));
		this.adminLog(operParamDto, "添加管理员[" + model.getUsername() + "]",
				FrameUtil.getTime(null));
		return this.adminDao.insertModel(model, true);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @param operParamDto
	 * @return
	 * @throws Exception
	 */
	public int update(AdminModel model, OperParamDto operParamDto)
			throws Exception {
		if(StringUtil.isNotEmpty(model.getPassword())){
			model.setEncrypt(SecurityCode.getRandomNumber(6));
			model.setPassword(SecurityCode.passwordMD5_2(model.getPassword(),
					model.getEncrypt()));
		}
		if (operParamDto != null) {
			this.adminLog(operParamDto, "更新管理员[" + model.getUsername() + "]",
					FrameUtil.getTime(null));
		}
		return this.adminDao.updateModel(model, true);
	}

	/**
	 * 根据id删除
	 * 
	 * @param admin_id
	 * @return
	 * @throws Exception
	 */
	public int delete(Long admin_id, OperParamDto operParamDto)
			throws Exception {
		if (admin_id == null) {
			FrameUtil.throwBaseException1("paramEx.00001");
		}
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("admin_id", admin_id);
		AdminModel model = this.getModelById(admin_id,DbWREnums.WRITE);
		// 不允许删除管理员
		if (AdminDataUtil.isAdmin(model)) {
			FrameUtil.throwBaseException1("该账户是管理员，不允许删除!");
		}
		if (model != null) {
			this.adminLog(operParamDto, "删除管理员[" + model.getUsername() + "]",
					FrameUtil.getTime(null));
		} else {
			FrameUtil.throwBaseException1("normal.dataNotExists");
		}
		return this.adminDao.deleteModel(paramMap, true);
	}

	/**
	 * 根据用户名和密码获取用户信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public AdminModel getAdmin(String username, String password)
			throws Exception {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("username", username);
		AdminModel model = this.getModelOne(paramMap,DbWREnums.WRITE);
		if (model != null) {
			String md5PassWord = SecurityCode.passwordMD5_2(password,
					model.getEncrypt());
			if (md5PassWord.equals(model.getPassword())) {
				return model;
			}
		}
		return null;
	}

	/**
	 * 管理员登陆
	 * 
	 * @param adminLoginInDto
	 * @return
	 * @throws Exception
	 */
	public AdminDataWrap login(AdminLoginInDto adminLoginInDto)
			throws Exception {
		AdminDataWrap result = this.getAdminDataWrap(
				adminLoginInDto.getUsername(), adminLoginInDto.getPassword());
		if (result != null) {
			this.adminLoginService.insert(new AdminLoginModel(adminLoginInDto
					.getUsername(), adminLoginInDto.getIp(), adminLoginInDto
					.getIpdata(), FrameUtil.getTime(null)));
			AdminModel model = new AdminModel();
			BeanUtils.copyProperties(result.getAdminModel(), model);
			model.setIp(adminLoginInDto.getIp());
			model.setIpdata(adminLoginInDto.getIpdata());
			model.setLastlogin(FrameUtil.getTime(null));
			this.adminDao.updateModel(model, true);
		}
		return result;
	}

	/**
	 * 根据用户名和密码获取管理员相关数据信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public AdminDataWrap getAdminDataWrap(String username, String password)
			throws Exception {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("username", username);
		AdminModel adminModel = this.getModelOne(paramMap,DbWREnums.WRITE);
		if (adminModel != null) {
			AdminDataWrap adminDataWrap = new AdminDataWrap();
			String md5PassWord = SecurityCode.passwordMD5_2(password,
					adminModel.getEncrypt());
			if (md5PassWord.equals(adminModel.getPassword())) {
				if (adminModel.getRole() != null) {
					List<AdminMenuModel> adminMenuModels = this.adminMenuService
							.getModelList(FrameUtil.newHashMap("order",
									" order by lid asc,pid asc"),DbWREnums.WRITE);
					adminDataWrap.setAdminMenuModels(adminMenuModels);
					adminDataWrap.setAdminModel(adminModel);
					adminDataWrap
							.setAdminRoleModel((AdminRoleModel) this.adminRoleService
									.getModelById(adminModel.getRole(),DbWREnums.WRITE));
					return adminDataWrap;
				}
			}
		}
		return null;
	}

	/**
	 * 获取管理员数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AdminDto> getAdminDtos(Map paramMap) throws Exception {
		List<AdminModel> list = this.adminDao.getModelList(paramMap, true);
		List<Long> roleIds = new ArrayList<Long>();
		for (AdminModel adminModel : list) {
			roleIds.add(adminModel.getRole());
		}
		Map<Long, AdminRoleModel> rolesMap = this.adminRoleService
				.getArsMapByIds(roleIds);
		List<AdminDto> result = new ArrayList<AdminDto>();
		for (AdminModel adminModel : list) {
			result.add(new AdminDto(AdminModelConvert.convert(adminModel),
					rolesMap.get(adminModel.getRole())));
		}
		return result;
	}

	/**
	 * 获取管理员数据对象
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PagerModel getAdminDtoPage(Map paramMap) throws Exception {
		return PagerUtil.getPagerModel(new IPager() {

			@Override
			public List getPageList(Map paramMap) throws Exception {
				return getAdminDtos(paramMap);
			}

			@Override
			public long getPageCount(Map paramMap) throws Exception {
				return adminDao.getModelListCount(paramMap, true);
			}
		}, paramMap, adminDao.getDbtype());
	}

	@Resource
	private IAdminDao adminDao;

	@Resource
	private IAdminRoleService adminRoleService;

	@Resource
	private IAdminMenuService adminMenuService;

	@Resource
	private IAdminLoginService adminLoginService;

	@Override
	public IBaseDao getBaseDao() {
		return this.adminDao;
	}

	public static void main(String[] args) {
		System.out.println(SecurityCode.passwordMD5_2("123456", "064860"));
	}

	@Override
	public AdminModel insert(AdminModel model, OperParamDto operParamDto,
			List<AdminRoleUserModel> roleList) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(AdminModel model, OperParamDto operParamDto,
			List<AdminRoleUserModel> roleList) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AdminInfoDto> getAdminInfoDtos(Map paramMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
