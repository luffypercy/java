package xyxc.wx.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.springmvc.dao.IBaseDao;
import cn.springmvc.dto.AdminMenusDto;
import cn.springmvc.dto.OperParamDto;
import cn.springmvc.enums.DbWREnums;
import cn.springmvc.util.FrameUtil;
import xyxc.wx.dao.IAdminMenuDao;
import xyxc.wx.model.AdminMenuModel;
import xyxc.wx.service.IAdminMenuService;

/**
 * <b>description</b>：系统菜单业务实现<br>
 * <b>time</b>：2014-11-05 14:09:43 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Service("adminMenuService")
public class AdminMenuServiceImpl extends BaseServiceImpl implements
		IAdminMenuService {

	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminMenuModel insert(AdminMenuModel model, OperParamDto operParamDto)
			throws Exception {
		this.adminLog(operParamDto, "添加管理菜单[" + model.getName() + "]",
				FrameUtil.getTime(null));
		return this.adminMenuDao.insertModel(model, true);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminMenuModel model, OperParamDto operParamDto)
			throws Exception {
		this.adminLog(operParamDto, "更新管理菜单[" + model.getName() + "]",
				FrameUtil.getTime(null));
		return this.adminMenuDao.updateModel(model, true);
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
		AdminMenuModel adminMenuModel = this.getModelById(id,DbWREnums.WRITE);
		if (adminMenuModel != null) {
			this.adminLog(operParamDto, "删除管理菜单[" + adminMenuModel.getName()
					+ "]", FrameUtil.getTime(null));
		} else {
			FrameUtil.throwBaseException1("normal.dataNotExists");
		}
		return this.adminMenuDao.deleteModel(paramMap, true);
	}

	/**
	 * 更新管理菜单
	 * 
	 * @param adminMenusDto
	 * @return
	 * @throws Exception
	 */
	public int updateMenu(AdminMenusDto adminMenusDto, OperParamDto operParamDto)
			throws Exception {
		if (adminMenusDto != null && adminMenusDto.getMenus() != null
				&& adminMenusDto.getMenus().size() >= 1) {
			this.adminLog(operParamDto, "更新管理菜单", FrameUtil.getTime(null));
			return this.adminMenuDao
					.updateBatch(adminMenusDto.getMenus(), true);
		}
		return 0;
	}

	@Resource
	private IAdminMenuDao adminMenuDao;

	@Override
	public IBaseDao getBaseDao() {
		return this.adminMenuDao;
	}

}
