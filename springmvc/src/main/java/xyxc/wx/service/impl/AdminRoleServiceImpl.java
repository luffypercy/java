package xyxc.wx.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.springmvc.dao.IBaseDao;
import cn.springmvc.dto.OperParamDto;
import cn.springmvc.enums.DbWREnums;
import cn.springmvc.util.FrameUtil;
import xyxc.wx.dao.IAdminRoleDao;
import xyxc.wx.model.AdminRoleModel;
import xyxc.wx.service.IAdminRoleService;

/**
 * <b>description</b>：系统角色业务实现<br>
 * <b>time</b>：2014-11-05 14:06:35 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Service("adminRoleService")
public class AdminRoleServiceImpl extends BaseServiceImpl implements
		IAdminRoleService {

	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminRoleModel insert(AdminRoleModel model, OperParamDto operParamDto)
			throws Exception {
		this.adminLog(operParamDto, "添加角色[" + model.getName() + "]",
				FrameUtil.getTime(null));
		return this.adminRoleDao.insertModel(model, true);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminRoleModel model, OperParamDto operParamDto)
			throws Exception {
		this.adminLog(operParamDto, "更新角色[" + model.getName() + "]",
				FrameUtil.getTime(null));
		return this.adminRoleDao.updateModel(model, true);
	}

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(Long id, OperParamDto operParamDto) throws Exception {
		if (id == null) {
			FrameUtil.throwBaseException1("paramEx.00001");
		}
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("id", id);
		AdminRoleModel model = this.getModelById(id,DbWREnums.WRITE);
		if (model != null) {
			this.adminLog(operParamDto, "删除角色[" + model.getName() + "]",
					FrameUtil.getTime(null));
		} else {
			FrameUtil.throwBaseException1("normal.dataNotExists");
		}
		return this.adminRoleDao.deleteModel(paramMap, true);
	}

	/**
	 * 获取角色列表
	 * 
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<AdminRoleModel> getArsByIds(List<Long> roleIds)
			throws Exception {
		if (roleIds == null || roleIds.size() == 0) {
			return FrameUtil.newArrayList();
		}
		return this.adminRoleDao.getModelList(
				FrameUtil.newHashMap("roleIds", roleIds), true);
	}

	/**
	 * 获取角色列表
	 * 
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	public Map<Long, AdminRoleModel> getArsMapByIds(List<Long> roleIds)
			throws Exception {
		Map<Long, AdminRoleModel> map = new LinkedHashMap<Long, AdminRoleModel>();
		List<AdminRoleModel> list = this.getArsByIds(roleIds);
		for (AdminRoleModel adminRoleModel : list) {
			map.put(adminRoleModel.getId(), adminRoleModel);
		}
		return map;
	}

	@Resource
	private IAdminRoleDao adminRoleDao;

	@Override
	public IBaseDao getBaseDao() {
		return this.adminRoleDao;
	}

}
