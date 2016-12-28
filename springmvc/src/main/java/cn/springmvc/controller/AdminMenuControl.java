package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.springmvc.dto.AdminMenusDto;
import cn.springmvc.enums.DbWREnums;
import cn.springmvc.util.FrameUtil;
import xyxc.wx.model.AdminMenuModel;
import xyxc.wx.service.IAdminMenuService;

/**
 * <b>description</b>：管理菜单 <br>
 * <b>time</b>：2014-11-5下午5:01:04 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Controller
@Scope("prototype")
@RequestMapping("/adminmenu/")
public class AdminMenuControl extends BaseControl {

	/**
	 * 管理菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public String list() throws Exception {
		List<AdminMenuModel> list = this.adminMenuService
				.getModelList(FrameUtil.newHashMap("order",
						"order by lid asc,pid asc"),DbWREnums.READ);
		Map<AdminMenuModel, List<AdminMenuModel>> menuMap = new LinkedHashMap<AdminMenuModel, List<AdminMenuModel>>();
		for (AdminMenuModel adminMenuModel : list) {
			if (adminMenuModel.getLid() == 0) {
				List<AdminMenuModel> tempList = new ArrayList<AdminMenuModel>();
				for (AdminMenuModel temp : list) {
					if (adminMenuModel.getId().equals(temp.getLid())) {
						tempList.add(temp);
					}
				}
				menuMap.put(adminMenuModel, tempList);
			}
		}
		request.setAttribute("menuMap", menuMap);
		return "adminmenu.list";
	}

	/**
	 * 更新管理菜单
	 * 
	 * @param adminMenusDto
	 * @throws Exception
	 */
	@RequestMapping("update")
	public void update(AdminMenusDto adminMenusDto) throws Exception {
		List<AdminMenuModel> menus = adminMenusDto.getMenus();
		List<AdminMenuModel> upMenus = new ArrayList<AdminMenuModel>();
		for (AdminMenuModel adminMenuModel : menus) {
			if (adminMenuModel.getId() != null) {
				upMenus.add(adminMenuModel);
			}
		}
		adminMenusDto.setMenus(upMenus);
		this.adminMenuService.updateMenu(adminMenusDto, this.getOperParamDto());
		this.successMsg(null, "更新数据成功!");
	}

	/**
	 * 根据管理菜单id删除管理菜单
	 * 
	 * @param lid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del/{id}")
	public void del(@PathVariable("id") Long id) throws Exception {
		if (id == null) {
			FrameUtil.throwBaseException1("参数有误!");
		}
		this.adminMenuService.delete(id, this.getOperParamDto());
		this.successMsg(null, "删除数据成功!");
	}

	/**
	 * 新增管理菜单
	 * 
	 * @param lid
	 *            父节点id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("add/{lid}")
	public String add(@PathVariable("lid") Long lid) throws Exception {
		AdminMenuModel menu = this.adminMenuService.getModelById(lid,DbWREnums.READ);
		if (menu == null) {
			return this.toErrorPage(null, "数据不存在!");
		}
		request.setAttribute("menu", menu);
		return "adminmenu.add";
	}

	/**
	 * 新增管理菜单
	 * 
	 * @param lid
	 *            父节点id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(AdminMenuModel menuModel) throws Exception {
		this.adminMenuService.insert(menuModel, this.getOperParamDto());
		this.successMsg(null, "新增数据成功!");
	}

	@Resource
	private IAdminMenuService adminMenuService;
}
