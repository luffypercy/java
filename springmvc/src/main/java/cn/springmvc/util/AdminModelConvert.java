package cn.springmvc.util;

import java.util.List;

import xyxc.wx.model.AdminModel;

/**
 * <b>description</b>：管理员数据转换器 <br>
 * <b>time</b>：2014-11-7下午9:42:52 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminModelConvert {

	public static AdminModel convert(AdminModel adminModel) {
		if (adminModel != null) {
			adminModel.getStrMap().put(
					"lastlogin",
					DateUtil.timestampToDateString(adminModel.getLastlogin(),
							DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
		}
		return adminModel;
	}

	public static List<AdminModel> convert(List<AdminModel> list) {
		for (AdminModel adminModel : list) {
			convert(adminModel);
		}
		return list;
	}
}
