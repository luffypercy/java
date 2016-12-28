package cn.springmvc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.springmvc.dto.AdminDataWrap;
import cn.springmvc.dto.OperParamDto;
import xyxc.wx.model.AdminMenuModel;
import xyxc.wx.model.AdminModel;
import xyxc.wx.model.AdminRoleModel;

/**
 * @ClassName: AdminDataUtil
 * @Description: 系统用户工具类
 * @author ready likun_557@163.com
 * @date 2014-8-19 上午11:34:08
 */
public class AdminDataUtil {
	public static final String SESSION_SYS_USER = "SESSION_SYS_USER";

	/**
	 * @Title: putSession
	 * @Description: 将系统用户的相关数据放入到session中
	 * @param @param request
	 * @param @param response
	 * @param @param sysUserDataWrap 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void putSession(HttpServletRequest request,
			HttpServletResponse response, AdminDataWrap adminDataWrap) {
		ServletUtil.putSession(request, response, SESSION_SYS_USER,
				adminDataWrap);
	}

	/**
	 * @Title: getSysUserDataWrap
	 * @Description: 获取系统用户相关数据
	 * @param @param request
	 * @param @param response
	 * @param @return 设定文件
	 * @return SysUserDataWrap 返回类型
	 * @throws
	 */
	public static AdminDataWrap getAdminDataWrap(HttpServletRequest request,
			HttpServletResponse response) {
		return (AdminDataWrap) ServletUtil.getSessionAttribute(request,
				response, SESSION_SYS_USER);
	}

	/**
	 * 构建管理员数据
	 * 
	 * @param adminModel
	 * @param adminRoleModel
	 * @param adminMenuModels
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static AdminDataWrap buildAdminData(AdminModel adminModel,
			AdminRoleModel adminRoleModel, List<AdminMenuModel> adminMenuModels) {
		AdminDataWrap adminDataWrap = new AdminDataWrap();
		adminDataWrap.setAdminModel(adminModel);
		adminDataWrap.setAdminRoleModel(adminRoleModel);
		adminDataWrap.setAdminMenuModels(adminMenuModels);
		String menuIds[] = null;
		// 管理员拥有最高权限
		if (isAdmin(adminModel)) {
			menuIds = new String[adminMenuModels.size()];
			int i = 0;
			for (AdminMenuModel adminMenuModel : adminMenuModels) {
				menuIds[i++] = adminMenuModel.getId().toString();
			}
		} else {
			menuIds = adminRoleModel.getRight().split(",");
		}
		List<AdminMenuModel> userMenuModels = new ArrayList<AdminMenuModel>();

		Map<Long, String> menuIdKeyMap = new HashMap<Long, String>();
		Map<String, Long> menuKeyIdmap = new HashMap<String, Long>();
		Map<String, Map> rootMenuMap = new LinkedHashMap<String, Map>();
		for (AdminMenuModel adminMenuModel : adminMenuModels) {
			for (String string : menuIds) {
				if (adminMenuModel.getId().toString().equals(string)) {
					userMenuModels.add(adminMenuModel);
					String menuKey = (StringUtil.isNotEmpty(adminMenuModel
							.getC()) ? adminMenuModel.getC() : "nav")
							+ ("_" + adminMenuModel.getId());
					menuIdKeyMap.put(adminMenuModel.getId(), menuKey);
					menuKeyIdmap.put(menuKey, adminMenuModel.getId());
					if (adminMenuModel.getLid() == 0) {
						rootMenuMap.put(menuKey,
								FrameUtil.newLinkedHashMap("id", menuKey,
										"name", adminMenuModel.getName()));
					}
				}
			}
		}

		for (Entry<String, Map> entry : rootMenuMap.entrySet()) {
			Map<String, Map> items = new LinkedHashMap<String, Map>();
			for (AdminMenuModel adminMenuModel : userMenuModels) {
				Long menuId = menuKeyIdmap.get(entry.getKey());
				if (menuId.equals(adminMenuModel.getLid())) {
					String key = menuIdKeyMap.get(adminMenuModel.getId());
					items.put(key, FrameUtil.newLinkedHashMap("id", key,
							"name", adminMenuModel.getName(), "parent",
							entry.getKey(), "url",
							ServletUtil.getUrl(adminMenuModel.getUrl())));
				}
			}
			if (items.size() >= 1) {
				entry.getValue().put("items", items);
			}
		}

		adminDataWrap.setUserMenuModels(userMenuModels);
		adminDataWrap.setUserMenuJson(FrameUtil.json(rootMenuMap));
		return adminDataWrap;
	}

	/**
	 * 判断某个用户是否是管理员
	 * 
	 * @param adminModel
	 * @return
	 */
	public static boolean isAdmin(AdminModel adminModel) {
		if (adminModel != null
				&& (adminModel.getRole() == null || adminModel.getRole() == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取操作参数信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static OperParamDto getOperParamDto(HttpServletRequest request,
			HttpServletResponse response) {
		AdminDataWrap adminDataWrap = getAdminDataWrap(request, response);
		OperParamDto operParamDto = new OperParamDto();
		if (adminDataWrap != null) {
			operParamDto.setAdmin_id(adminDataWrap.getAdminModel()
					.getAdmin_id());
			operParamDto.setUsername(adminDataWrap.getAdminModel()
					.getUsername());
		}
		operParamDto.setIp(ServletUtil.getIpAddr(request));
		return operParamDto;
	}
}
