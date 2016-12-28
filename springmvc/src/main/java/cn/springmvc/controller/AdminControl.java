package cn.springmvc.controller;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.springmvc.dto.AdminDataWrap;
import cn.springmvc.dto.AdminLoginInDto;
import cn.springmvc.enums.DbWREnums;
import cn.springmvc.util.AdminDataUtil;
import cn.springmvc.util.FrameUtil;
import cn.springmvc.util.ServletUtil;
import cn.springmvc.util.StringUtil;
import xyxc.wx.model.AdminModel;
import xyxc.wx.service.IAdminRoleService;
import xyxc.wx.service.IAdminService;


/**
 * <b>description</b>：系统管理员 <br>
 * <b>time</b>：2014-11-5下午5:01:04 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/")
public class AdminControl extends BaseControl {

	/**
	 * 管理员列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list() throws Exception {
		request.setAttribute("list", this.adminService.getAdminDtos(FrameUtil
				.newHashMap("sort", "role asc")));
		return "admin.list";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toadd")
	public String toadd() throws Exception {
		this.addOrEditInit();
		return "admin.toaddoredit";
	}

	private void addOrEditInit() throws Exception {
		request.setAttribute("adminRoleList",
				this.adminRoleService.getModelList(null,DbWREnums.READ));
	}

	/**
	 * 跳转到编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toedit/{admin_id}")
	public String toedit(@PathVariable("admin_id") Long admin_id)
			throws Exception {
		if (admin_id == null) {
			FrameUtil.throwBaseException1("参数有误!");
		}
		this.addOrEditInit();
		AdminModel model = this.adminService.getModelById(admin_id,DbWREnums.READ);
		if (model == null) {
			return this.toErrorPage(null, "数据被删除!");
		}
		request.setAttribute("model", model);
		return "admin.toaddoredit";
	}

	/**
	 * 新增和保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(AdminModel model) throws Exception {
		if (StringUtil.isEmpty(model.getUsername())) {
			FrameUtil.throwBaseException1("用户名不能为空!");
		} else if (StringUtil.isEmpty(model.getPassword())) {
			FrameUtil.throwBaseException1("密码不能为空!");
		}
		if (model.getAdmin_id() == null) {
			this.adminService.insert(model, this.getOperParamDto());
		} else {
			this.adminService.update(model, this.getOperParamDto());
		}
		this.successMsg(null, "保存成功!");
	}

	/**
	 * 根据管理员id删除管理员
	 * 
	 * @param lid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del/{admin_id}")
	public void del(@PathVariable("admin_id") Long admin_id) throws Exception {
		if (admin_id == null) {
			FrameUtil.throwBaseException1("参数有误!");
		}
		this.adminService.delete(admin_id, this.getOperParamDto());
		this.successMsg(null, "删除数据成功!");
	}

	/**
	 * 登陆
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login")
	public String login() throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtil.isNotEmpty(username) && StringUtil.isNotEmpty(password)) {
			AdminDataWrap adminDataWrap = this.adminService
					.login(new AdminLoginInDto(username, password, ServletUtil
							.getIpAddr(request),""));
			if (adminDataWrap == null || adminDataWrap.getAdminModel() == null) {
				return this
						.toErrorPage(ServletUtil.getRootPath(), "用户名或者密码有误!");
			} else {
				AdminDataUtil.putSession(request, response, AdminDataUtil
						.buildAdminData(adminDataWrap.getAdminModel(),
								adminDataWrap.getAdminRoleModel(),
								adminDataWrap.getAdminMenuModels()));
				return this.toSuccessPage(ServletUtil.getUrl("/admin/main"),
						"登陆成功");
			}
		} else {
			return this.toErrorPage(ServletUtil.getRootPath(), "用户名和密码不能为空!");
		}
	}

	/**
	 * 注销
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exit")
	public String exit() throws Exception {
		ServletUtil.cleanAllSession(request, response, true);
		return this.toSuccessPage(ServletUtil.getRootPath(), "退出成功");
	}

	/**
	 * 登陆成功
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("main")
	public String main() throws Exception {
		return "/system/admin/admin.main";
	}

	@Resource
	private IAdminService adminService;

	@Resource
	private IAdminRoleService adminRoleService;
}
