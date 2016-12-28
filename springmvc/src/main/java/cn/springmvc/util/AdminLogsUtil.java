package cn.springmvc.util;

import cn.springmvc.dto.OperParamDto;
import xyxc.wx.model.AdminLogsModel;

/**
 * <b>description</b>：操作记录工具类 <br>
 * <b>time</b>：2014-11-7上午9:45:03 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class AdminLogsUtil {

	/**
	 * 构建操作记录信息
	 * 
	 * @param operParamDto
	 *            操作参数
	 * @param msg
	 *            信息
	 * @param time
	 *            操作时间
	 * @return
	 */
	public static AdminLogsModel build(OperParamDto operParamDto, String msg,
			Long time) {
		AdminLogsModel adminLogsModel = new AdminLogsModel();
		adminLogsModel.setUsername(operParamDto.getUsername());
		adminLogsModel.setIp(operParamDto.getIp());
		adminLogsModel.setMsg(msg);
		adminLogsModel.setTime(time);
		return adminLogsModel;
	}
}
