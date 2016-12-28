package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.springmvc.enums.DbWREnums;
import cn.springmvc.model.PagerHtmlModel;
import cn.springmvc.util.DateUtil;
import cn.springmvc.util.PagerUtil;
import cn.springmvc.util.ServletUtil;
import xyxc.wx.model.AdminLogsModel;
import xyxc.wx.service.IAdminLogsService;

/**
 * <b>description</b>：操作记录 <br>
 * <b>time</b>：2014-11-7上午10:30:55 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Controller
@Scope("prototype")
@RequestMapping("/adminlogs/")
public class AdminLogsControl extends BaseControl {

	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public String list() throws Exception {
		Map<String, Object> paramMap = ServletUtil.getPageParameterMap(request);
		paramMap.put("sort", "id desc");
		PagerHtmlModel pager = PagerUtil.getPagerHtmlModel(
				this.adminLogsService.getPageModel(paramMap,DbWREnums.READ),
				ServletUtil.getPageParameterMap(request),
				ServletUtil.getUrl("/adminlogs/list"));
		List<AdminLogsModel> data = pager.getPagerModel().getDataList();
		for (AdminLogsModel model : data) {
			model.getStrMap().put(
					"time",
					DateUtil.timestampToDateString(model.getTime(),
							DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
		}
		request.setAttribute("pager", pager);
		return "adminlogs.list";
	}

	/**
	 * 根据管理菜单id删除管理菜单
	 * 
	 * @param lid
	 * @return
	 * @throws Exception
	 */
	public void del() throws Exception {
		this.adminLogsService.deleteAmonthData(this.getOperParamDto());
		this.successMsg(null, "删除数据成功!");
	}

	@Resource
	private IAdminLogsService adminLogsService;
}
