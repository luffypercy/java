package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.springmvc.enums.DbWREnums;
import cn.springmvc.util.FrameUtil;
import cn.springmvc.util.StringUtil;
import xyxc.wx.model.AdminMenuModel;
import xyxc.wx.model.AdminRoleModel;
import xyxc.wx.service.IAdminMenuService;
import xyxc.wx.service.IAdminRoleService;

/**
 * <b>description</b>：系统角色 <br>
 * <b>time</b>：2014-11-8上午10:38:03 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Controller
@Scope("prototype")
@RequestMapping("/adminrole/")
public class AdminRoleControl extends BaseControl {

	/**
	 * 管理员列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list() throws Exception {
		request.setAttribute("list", this.adminRoleService
				.getModelList(FrameUtil.newHashMap("sort", "id asc"),DbWREnums.READ));
		return "adminrole.list";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toadd")
	public String toadd() throws Exception {
		return "adminrole.toadd";
	}

	/**
	 * 新增
	 * 
	 * @param model
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("add")
	public void add(AdminRoleModel model) throws Exception {
		if (StringUtil.isEmpty(model.getName())) {
			this.errorMsg(null, "角色名称不能为空!");
			return;
		}
		List<AdminRoleModel> list = this.adminRoleService
				.getModelList(FrameUtil.newHashMap("name", model.getName()),DbWREnums.READ);
		if (list.size() >= 1) {
			this.errorMsg(null, "角色名称已经存在!");
			return;
		}
		this.adminRoleService.insert(model, this.getOperParamDto());
		this.successMsg(null, "保存成功!");
	}

	/**
	 * 跳转到编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("toedit/{id}")
	public String toedit(@PathVariable("id") Long id) throws Exception {
		if (id == null) {
			FrameUtil.throwBaseException1("参数有误!");
		}
		AdminRoleModel model = this.adminRoleService.getModelById(id,DbWREnums.READ);
		if (model == null) {
			return this.toErrorPage(null, "数据被删除!");
		}
		List<AdminMenuModel> rootMenus = new ArrayList<AdminMenuModel>();
		List<AdminMenuModel> adminMenuModels = this.adminMenuService
				.getModelList(FrameUtil.newHashMap("order",
						"order by lid asc,pid asc"),DbWREnums.READ);
		String right = model.getRight();
		String ms[] = null;
		if (StringUtil.isNotEmpty(right)) {
			ms = right.split(",");
		}
		for (AdminMenuModel adminMenuModel : adminMenuModels) {
			Boolean checked = Boolean.FALSE;
			if (ms != null) {
				for (String string : ms) {
					if (adminMenuModel.getId().toString().equals(string)) {
						checked = Boolean.TRUE;
						break;
					}
				}
			}
			// 角色中该权限是否选中
			adminMenuModel.getStrMap().put("checked", checked);
			for (AdminMenuModel ch : adminMenuModels) {
				if (adminMenuModel.getId().equals(ch.getLid())) {
					List<AdminMenuModel> childs = (List<AdminMenuModel>) adminMenuModel
							.getStrMap().get("childs");
					if (childs == null) {
						childs = new ArrayList<AdminMenuModel>();
					}
					childs.add(ch);
					adminMenuModel.getStrMap().put("childs", childs);
				}
			}
			if (adminMenuModel.getLid() == 0) {
				rootMenus.add(adminMenuModel);
			}
		}
		request.setAttribute("rootMenus", rootMenus);
		request.setAttribute("model", model);
		return "adminrole.toedit";
	}

	/**
	 * 新增
	 * 
	 * @param model
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("edit")
	public void edit(AdminRoleModel model) throws Exception {
		if (model.getId() == null) {
			FrameUtil.throwBaseException1("参数有误!");
		}
		if (StringUtil.isEmpty(model.getName())) {
			this.errorMsg(null, "角色名称不能为空!");
			return;
		}
		List<AdminRoleModel> list = this.adminRoleService
				.getModelList(FrameUtil.newHashMap("name", model.getName()),DbWREnums.READ);
		if (list.size() == 1 && !list.get(0).getId().equals(model.getId())) {
			this.errorMsg(null, "角色名称已经存在!");
			return;
		}
		String[] menus = this.request.getParameterValues("ids[]");
		if (menus != null) {
			List<String> menuList = new ArrayList<String>();
			for (String string : menus) {
				if (!FrameUtil.isNumeric(string)) {
					FrameUtil.throwBaseException1("参数有误!");
				}
				menuList.add(string);
			}
			model.setRight(FrameUtil.arrayConcat(menuList, ",", false));
		}
		this.adminRoleService.update(model, this.getOperParamDto());
		this.successMsg(null, "保存成功!");
	}

	/**
	 * 根据角色id删除角色
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
		this.adminRoleService.delete(id, this.getOperParamDto());
		this.successMsg(null, "删除数据成功!");
	}

	@Resource
	private IAdminRoleService adminRoleService;

	@Resource
	private IAdminMenuService adminMenuService;
}
